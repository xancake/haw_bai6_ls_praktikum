package org.haw.ls.xancake.viergewinnt.game;

import java.util.List;
import java.util.Objects;
import org.haw.ls.xancake.viergewinnt.game.VierGewinntBoard.Color;
import org.haw.ls.xancake.viergewinnt.player.VierGewinntPlayer;
import de.xancake.pattern.listener.EventDispatcher;

public class VierGewinntGame {
	private EventDispatcher<VierGewinntGameListener> _dispatcher;
	
	private VierGewinntBoard _board;
	private VierGewinntPlayer _player1;
	private VierGewinntPlayer _player2;
	
	private boolean _play;
	
	public VierGewinntGame(VierGewinntBoard board, VierGewinntPlayer player1, VierGewinntPlayer player2) {
		_dispatcher = new EventDispatcher<>();
		_board = Objects.requireNonNull(board);
		_player1 = Objects.requireNonNull(player1);
		_player2 = Objects.requireNonNull(player2);
		_player1.setColor(Color.X);
		_player2.setColor(Color.O);
	}
	
	public void addListener(VierGewinntGameListener listener) {
		_dispatcher.addListener(listener);
	}
	
	public void removeListener(VierGewinntGameListener listener) {
		_dispatcher.removeListener(listener);
	}
	
	public void start() {
		_play = true;
		gameLoop();
	}
	
	private void gameLoop() {
		_dispatcher.fireEvent(l -> l.onGameStart());
		while(_play && !_board.isGameOver()) {
			_dispatcher.fireEvent(l -> l.onRoundStart());
			
			doPlayerTurn(_player1);
			if(!_board.isGameOver()) {
				// Wenn das Spiel nach dem Zug von Spieler 1 vorbei ist, darf Spieler 2 keinen Zug mehr machen
				doPlayerTurn(_player2);
			}
			
			_dispatcher.fireEvent(l -> l.onRoundEnd());
		}
		// TODO: Nicht die Color sondern den VierGewinntPlayer übergeben
		_dispatcher.fireEvent(l -> l.onGameEnd(_board.getWinner()));
	}
	
	private void doPlayerTurn(VierGewinntPlayer player) {
		List<Integer> validMoves = _board.getValidMovesFor(player.getColor());
		_dispatcher.fireEvent(l -> l.onBeforePlayersTurn(player, validMoves));
		int choice = getPlayerChoice(player, validMoves);
		_board.makeMove(player.getColor(), choice);
		_dispatcher.fireEvent(l -> l.onAfterPlayersTurn(player, validMoves, choice));
	}
	
	private int getPlayerChoice(VierGewinntPlayer player, List<Integer> validMoves) {
		int choice = -1;
		boolean validChoice;
		do {
			choice = player.chooseMove(validMoves);
			validChoice = true;
			if(!validMoves.contains(choice)) {
				int move = choice;
				_dispatcher.fireEvent(l -> l.onIllegalChoice(player, validMoves, move));
				validChoice = false;
			}
		} while(!validChoice);
		return choice;
	}
}
