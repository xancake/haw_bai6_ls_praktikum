package org.haw.ls.xancake.viergewinnt.game;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.haw.ls.xancake.util.listener.EventDispatcher;

public class VierGewinntBoard {
	private EventDispatcher<VierGewinntBoardListener> _dispatcher = new EventDispatcher<>();
	
	private Field[][] _field;
	private Color _turn;
	private boolean _gameOver;
	private Color _winner;
	
	public VierGewinntBoard() {
		_field = new Field[7][6];
		for(int x=0; x<_field.length; x++) {
			for(int y=0; y<_field[x].length; y++) {
				_field[x][y] = new Field();
			}
		}
		_turn = Color.X;
	}
	
	public void addListener(VierGewinntBoardListener listener) {
		_dispatcher.addListener(listener);
	}
	
	public void removeListener(VierGewinntBoardListener listener) {
		_dispatcher.removeListener(listener);
	}
	
	public int getRows() {
		return _field.length;
	}
	
	public int getCols() {
		return _field[0].length;
	}
	
	public Field getField(int row, int col) {
		return _field[row][col];
	}
	
	public void makeMove(Color color, int x) {
		if(!isColorsTurn(color)) {
			throw new IllegalArgumentException(String.format("Spieler %s ist nicht am Zug!", color));
		}
		if(!canMakeMove(x)) {
			throw new IllegalArgumentException(String.format("Die Spalte %d ist bereits voll!", x));
		}
		
		int y=0;
		while(y<_field[x].length) {
			if(_field[x][y].isEmpty()) {
				_field[x][y].setOwner(color);
				int Y = y; // Muss geschehen, da in einem Lambda nur (effektiv) finale lokale Variablen verwendet werden können
				_dispatcher.fireEvent(l -> l.onMoveMade(color, x, Y));
				break;
			}
			y++;
		}
		
		_turn = Color.getOtherColor(color);
		
		if(VierGewinntBoardCheck.checkFourInARow(this, color, x, y)) {
			_gameOver = true;
			_winner = color;
			_dispatcher.fireEvent(l -> l.onGameOver(color));
		}
	}
	
	private boolean canMakeMove(int x) {
		return _field[x][_field[x].length-1].isEmpty();
	}
	
	public List<Integer> getValidMovesFor(Color color) {
		if(!isColorsTurn(color)) {
			return Collections.emptyList();
		}
		
		List<Integer> validMoves = new ArrayList<Integer>(_field.length);
		for(int x=0; x<_field.length; x++) {
			for(int y=_field[x].length-1; y>=0; y--) {
				if(_field[x][y].isEmpty()) {
					validMoves.add(x);
					break;
				}
			}
		}
		return validMoves;
	}
	
	public boolean isColorsTurn(Color color) {
		return !_gameOver && _turn == color;
	}
	
	public boolean isGameOver() {
		return _gameOver;
	}
	
	public Color getWinner() {
		return _winner;
	}
	
	public void reset() {
		for(int x=0; x<_field.length; x++) {
			for(int y=0; y<_field[x].length; y++) {
				_field[x][y].setOwner(null);
			}
		}
		_turn = Color.X;
		_gameOver = false;
		_winner = null;
	}
	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		for(int y=_field[0].length-1; y>=0; y--) {
			for(int x=0; x<_field.length; x++) {
				builder.append("[").append(_field[x][y]).append("]");
			}
			builder.append("\n");
		}
		return builder.toString();
	}
	
	public class Field {
		private Color _owner;
		
		public boolean isEmpty() {
			return _owner == null;
		}
		
		public Color getOwner() {
			return _owner;
		}
		
		void setOwner(Color color) {
			_owner = color;
		}
		
		@Override
		public String toString() {
			return _owner == null ? " " : _owner.toString();
		}
	}
	
	public enum Color {
		X, O;
		
		public static Color getOtherColor(Color color) {
			if(X == color) return O;
			else if(O == color) return X;
			else throw new IllegalArgumentException();
		}
	}
}
