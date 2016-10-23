package org.haw.ls.xancake.viergewinnt.startup;

import java.util.InputMismatchException;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;
import org.haw.ls.xancake.viergewinnt.game.VierGewinntBoard;
import org.haw.ls.xancake.viergewinnt.game.VierGewinntBoard.Color;
import org.haw.ls.xancake.viergewinnt.game.VierGewinntGame;
import org.haw.ls.xancake.viergewinnt.game.VierGewinntGameListener;
import org.haw.ls.xancake.viergewinnt.player.AbstractVierGewinntPlayer;
import org.haw.ls.xancake.viergewinnt.player.VierGewinntPlayer;

public class VierGewinntCLIStarter {
	private static final Scanner keyboard = new Scanner(System.in);
	
	public static void main(String[] args) {
		playGame();
	}
	
	private static void playGame() {
		VierGewinntBoard board = new VierGewinntBoard();
		VierGewinntPlayer player1 = new CLIPlayer();
		VierGewinntPlayer player2 = new CLIPlayer();
		VierGewinntGame game = new VierGewinntGame(board, player1, player2);
		game.addListener(new GameListener(board));
		game.start();
	}
	
	private static class CLIPlayer extends AbstractVierGewinntPlayer {
		@Override
		public int chooseMove(List<Integer> validMoves) {
			try {
				int choice = keyboard.nextInt();
				keyboard.nextLine();
				return choice;
			} catch(InputMismatchException e) {
				System.out.println(e.getMessage());
				return -1;
			}
		}
	}
	
	private static class GameListener implements VierGewinntGameListener {
		private VierGewinntBoard _board;
		
		public GameListener(VierGewinntBoard board) {
			_board = Objects.requireNonNull(board);
		}
		
		@Override
		public void onGameStart() {
			System.out.println(_board);
		}
		
		@Override
		public void onGameEnd(Color winner) {
			System.out.println("GAME OVER!");
			System.out.println(winner.name() + " hat das Spiel gewonnen!");
		}
		
		@Override
		public void onRoundStart() {
			
		}
		
		@Override
		public void onRoundEnd() {
			
		}
		
		@Override
		public void onBeforePlayersTurn(VierGewinntPlayer player, List<Integer> validChoices) {
			System.out.println(player.getColor().name() + " ist am Zug! Folgende Züge stehen zur Verfügung");
			System.out.print(validChoices + " Wahl: ");
		}
		
		@Override
		public void onAfterPlayersTurn(VierGewinntPlayer player, List<Integer> validChoices, Integer choice) {
			if(!(player instanceof CLIPlayer)) {
				System.out.println(choice);
			}
			System.out.println(_board);
		}
		
		@Override
		public void onIllegalChoice(VierGewinntPlayer player, List<Integer> validChoices, Integer choice) {
			System.out.print("'" + choice + "' ist keine gültige Aktion, bitte erneut Wählen: ");
		}
	}
}
