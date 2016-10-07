package org.haw.ls.xancake.viergewinnt.game;

import org.haw.ls.xancake.viergewinnt.game.VierGewinntBoard.Color;
import org.haw.ls.xancake.viergewinnt.game.VierGewinntBoard.Field;

public class VierGewinntBoardCheck {
	private VierGewinntBoardCheck() {}
	
	/**
	 * Prüft, ob durch den letzten Zug vier in einer Reihe entstanden sind.
	 * <p>Bei dem Check wird nur die übergebene {@link Color Farbe} geprüft. Dadurch kann direkt gefolgert werden, dass
	 * wenn die Methode {@code true} liefert die übergebene {@link Color Farbe} damit gewonnen hat.
	 * @param board Das {@link VierGewinntBoard Board} das geprüft werden soll
	 * @param color Die {@link Color Farbe}, die den letzten Zug gemacht hat
	 * @param x Die Spalte des letzten Zugs
	 * @param y Die Zeile des letzten Zugs
	 * @return {@code true}, wenn {@code color} durch den Zug gewinnt, ansonsten {@code false}
	 */
	public static boolean checkFourInARow(VierGewinntBoard board, Color color, int x, int y) {
		int xFrom = Math.max(x-3, 0);
		int xTo   = Math.min(x+3, board.getRows());
		int yFrom = Math.max(y-3, 0);
		int yTo   = Math.min(y+3, board.getCols());
		
		FourInARowCheckResult result = checkFourVertically(board, x, x+1, yFrom, yTo); // x+1 weil der xTo-Parameter exklusiv ist
		if(result.gameOver) {
			return true;
		}
		
		result = checkFourHorizontally(board, xFrom, xTo, y, y+1); // y+1 weil der yTo-Parameter exklusiv ist
		if(result.gameOver) {
			return true;
		}
		
		result = checkFourDiagonallyUp(board, xFrom, xTo, yFrom, yTo);
		if(result.gameOver) {
			return true;
		}
		
		result = checkFourDiagonallyDown(board, xFrom, xTo, yFrom, yTo);
		if(result.gameOver) {
			return true;
		}
		
		return false;
	}
	
	/**
	 * Prüft, ob eine {@link Color Farbe} gewonnen hat und gibt sie zurück. Hierbei wird das gesamte Board geprüft.
	 * @param board Das {@link VierGewinntBoard Board} das geprüft werden soll
	 * @return Die {@link Color Farbe} des Siegers oder {@code null}, wenn es keinen gibt
	 */
	public static Color checkForWin(VierGewinntBoard board) {
		FourInARowCheckResult result = checkFourVertically(board);
		if(result.gameOver) {
			return result.winner;
		}
		
		result = checkFourHorizontally(board);
		if(result.gameOver) {
			return result.winner;
		}
		
		result = checkFourDiagonallyUp(board);
		if(result.gameOver) {
			return result.winner;
		}
		
		result = checkFourDiagonallyDown(board);
		if(result.gameOver) {
			return result.winner;
		}
		
		return null;
	}
	
	private static FourInARowCheckResult checkFourVertically(VierGewinntBoard board) {
		return checkFourVertically(board, 0, board.getRows(), 0, board.getCols());
	}
	
	private static FourInARowCheckResult checkFourVertically(VierGewinntBoard board, int xFrom, int xTo, int yFrom, int yTo) {
		checkFromToParameter(board, xFrom, xTo, yFrom, yTo);
		for(int x=xFrom; x<xTo; x++) {
			Color lastCheckedPlayer = null;
			int lastCombo = 0;
			for(int y=yFrom; y<yTo; y++) {
				Field field = board.getField(x, y);
				if(field.isEmpty()) {
					lastCheckedPlayer = null;
					lastCombo = 0;
					break;
				} else if(lastCheckedPlayer == field.getOwner() || lastCheckedPlayer == null) {
					lastCheckedPlayer = field.getOwner();
					lastCombo++;
				} else if(lastCheckedPlayer != field.getOwner()) {
					lastCheckedPlayer = field.getOwner();
					lastCombo = 0;
				}
				if(lastCombo == 4) {
					return new FourInARowCheckResult(lastCheckedPlayer);
				}
			}
		}
		return new FourInARowCheckResult();
	}
	
	private static FourInARowCheckResult checkFourHorizontally(VierGewinntBoard board) {
		return checkFourHorizontally(board, 0, board.getRows(), 0, board.getCols());
	}
	
	private static FourInARowCheckResult checkFourHorizontally(VierGewinntBoard board, int xFrom, int xTo, int yFrom, int yTo) {
		checkFromToParameter(board, xFrom, xTo, yFrom, yTo);
		for(int y=yFrom; y<yTo; y++) {
			Color lastCheckedPlayer = null;
			int lastCombo = 0;
			for(int x=xFrom; x<xTo; x++) {
				Field field = board.getField(x, y);
				if(field.isEmpty()) {
					lastCheckedPlayer = null;
					lastCombo = 0;
				} else if(lastCheckedPlayer == field.getOwner() || lastCheckedPlayer == null) {
					lastCheckedPlayer = field.getOwner();
					lastCombo++;
				} else if(lastCheckedPlayer != field.getOwner()) {
					lastCheckedPlayer = field.getOwner();
					lastCombo = 0;
				}
				if(lastCombo == 4) {
					return new FourInARowCheckResult(lastCheckedPlayer);
				}
			}
		}
		return new FourInARowCheckResult();
	}
	
	private static FourInARowCheckResult checkFourDiagonallyUp(VierGewinntBoard board) {
		return checkFourDiagonallyUp(board, 0, board.getRows(), 0, board.getCols());
	}
	
	private static FourInARowCheckResult checkFourDiagonallyUp(VierGewinntBoard board, int xFrom, int xTo, int yFrom, int yTo) {
		checkFromToParameter(board, xFrom, xTo, yFrom, yTo);
		for(int x=xFrom; x<xTo-3; x++) {
			for(int y=yFrom; y<yTo-3; y++) {
				Color lastCheckedPlayer = null;
				int lastCombo = 0;
				for(int d=0; d<4; d++) {
					Field field = board.getField(x+d, y+d);
					if(field.isEmpty()) {
						lastCheckedPlayer = null;
						lastCombo = 0;
						break;
					} else if(lastCheckedPlayer == field.getOwner() || lastCheckedPlayer == null) {
						lastCheckedPlayer = field.getOwner();
						lastCombo++;
					} else if(lastCheckedPlayer != field.getOwner()) {
						lastCheckedPlayer = field.getOwner();
						lastCombo = 0;
					}
					if(lastCombo == 4) {
						return new FourInARowCheckResult(lastCheckedPlayer);
					}
				}
			}
		}
		return new FourInARowCheckResult();
	}
	
	private static FourInARowCheckResult checkFourDiagonallyDown(VierGewinntBoard board) {
		return checkFourDiagonallyDown(board, 0, board.getRows(), 0, board.getCols());
	}
	
	private static FourInARowCheckResult checkFourDiagonallyDown(VierGewinntBoard board, int xFrom, int xTo, int yFrom, int yTo) {
		checkFromToParameter(board, xFrom, xTo, yFrom, yTo);
		for(int x=board.getRows()-1; x>=3; x--) {
			for(int y=0; y<board.getCols()-3; y++) {
				Color lastCheckedPlayer = null;
				int lastCombo = 0;
				for(int d=0; d<4; d++) {
					Field field = board.getField(x-d, y+d);
					if(field.isEmpty()) {
						lastCheckedPlayer = null;
						lastCombo = 0;
						break;
					} else if(lastCheckedPlayer == field.getOwner() || lastCheckedPlayer == null) {
						lastCheckedPlayer = field.getOwner();
						lastCombo++;
					} else if(lastCheckedPlayer != field.getOwner()) {
						lastCheckedPlayer = field.getOwner();
						lastCombo = 0;
					}
					if(lastCombo == 4) {
						return new FourInARowCheckResult(lastCheckedPlayer);
					}
				}
			}
		}
		return new FourInARowCheckResult();
	}
	
	private static void checkFromToParameter(VierGewinntBoard board, int xFrom, int xTo, int yFrom, int yTo) {
		if(xFrom < 0 || xTo > board.getRows()) {
			throw new IllegalArgumentException("Die xFrom- und xTo-Parameter müssen im Bereich von 0 bis board.rows liegen.");
		}
		if(xFrom > xTo) {
			throw new IllegalArgumentException("xFrom- darf nicht größer als xTo sein.");
		}
		if(yFrom < 0 || yTo > board.getCols()) {
			throw new IllegalArgumentException("Die Parameter yFrom- und yTo-Parameter müssen im Bereich von 0 bis board.cols liegen.");
		}
		if(yFrom > yTo) {
			throw new IllegalArgumentException("yFrom- darf nicht größer als yTo sein.");
		}
	}
	
	private static class FourInARowCheckResult {
		// TODO: Die Indizes der Felder mit aufnehmen
		private boolean gameOver;
		private Color winner;
		
		/**
		 * Konstruktor für den Fall, dass <b>keine</b> siegreiche Kombination gefunden werden konnte.
		 */
		public FourInARowCheckResult() {
			this.gameOver = false;
		}
		
		/**
		 * Konstruktor für den Fall, dass <b>eine</b> siegreiche Kombination gefunden werden konnte.
		 * @param winner Der ermittelte Gewinner
		 */
		public FourInARowCheckResult(Color winner) {
			this.gameOver = true;
			this.winner = winner;
		}
	}
}
