package org.haw.ls.xancake.viergewinnt.game;

import static org.junit.Assert.*;
import org.haw.ls.xancake.viergewinnt.game.VierGewinntBoard.Color;
import org.junit.Before;
import org.junit.Test;

public class VierGewinntBoardCheckTest {
	private VierGewinntBoard b;
	
	@Before
	public void setUp() {
		b = new VierGewinntBoard();
	}
	
	/**
	 * Board State:
	 * [ ][ ][ ][ ][ ][ ][ ]
	 * [ ][ ][ ][ ][ ][ ][ ]
	 * [ ][ ][ ][ ][ ][ ][ ]
	 * [ ][ ][ ][X][O][ ][ ]
	 * [ ][ ][ ][X][O][ ][ ]
	 * [ ][ ][ ][X][O][ ][ ]
	 */
	@Test
	public void testCheckForWin_NoWinner() {
		b.makeMove(Color.X, 3);
		b.makeMove(Color.O, 4);
		b.makeMove(Color.X, 3);
		b.makeMove(Color.O, 4);
		b.makeMove(Color.X, 3);
		b.makeMove(Color.O, 4);
		
		assertWin(null, Color.O, 4, 2);
	}
	
	/**
	 * Board State:
	 * [ ][ ][ ][ ][ ][ ][ ]
	 * [ ][ ][ ][ ][ ][ ][ ]
	 * [ ][ ][ ][X][ ][ ][ ]
	 * [ ][ ][ ][X][O][ ][ ]
	 * [ ][ ][ ][X][O][ ][ ]
	 * [ ][ ][ ][X][O][ ][ ]
	 */
	@Test
	public void testCheckForWin_Vertically() {
		b.makeMove(Color.X, 3);
		b.makeMove(Color.O, 4);
		b.makeMove(Color.X, 3);
		b.makeMove(Color.O, 4);
		b.makeMove(Color.X, 3);
		b.makeMove(Color.O, 4);
		b.makeMove(Color.X, 3);
		
		assertWin(Color.X, Color.X, 3, 3);
	}
	
	/**
	 * Board State:
	 * [ ][ ][ ][ ][ ][ ][ ]
	 * [ ][ ][ ][ ][ ][ ][ ]
	 * [ ][ ][ ][ ][ ][ ][ ]
	 * [ ][ ][ ][ ][ ][ ][ ]
	 * [ ][ ][ ][O][O][O][ ]
	 * [ ][ ][ ][X][X][X][X]
	 */
	@Test
	public void testCheckForWin_Horizontally() {
		b.makeMove(Color.X, 3);
		b.makeMove(Color.O, 3);
		b.makeMove(Color.X, 4);
		b.makeMove(Color.O, 4);
		b.makeMove(Color.X, 5);
		b.makeMove(Color.O, 5);
		b.makeMove(Color.X, 6);
		
		assertWin(Color.X, Color.X, 6, 0);
	}
	
	/**
	 * Board State:
	 * [ ][ ][ ][ ][ ][ ][ ]
	 * [ ][ ][ ][ ][ ][ ][ ]
	 * [ ][ ][ ][X][ ][ ][ ]
	 * [ ][ ][X][O][ ][ ][ ]
	 * [O][X][O][X][ ][ ][ ]
	 * [X][O][X][O][ ][ ][ ]
	 */
	@Test
	public void testCheckForWin_DiagonallyUp() {
		b.makeMove(Color.X, 0);
		b.makeMove(Color.O, 1);
		b.makeMove(Color.X, 1);
		b.makeMove(Color.O, 0);
		b.makeMove(Color.X, 2);
		b.makeMove(Color.O, 2);
		b.makeMove(Color.X, 2);
		b.makeMove(Color.O, 3);
		b.makeMove(Color.X, 3);
		b.makeMove(Color.O, 3);
		b.makeMove(Color.X, 3);
		
		assertWin(Color.X, Color.X, 3, 3);
	}
	
	/**
	 * Board State:
	 * [ ][ ][ ][ ][ ][ ][ ]
	 * [ ][ ][ ][ ][ ][ ][ ]
	 * [ ][ ][ ][X][ ][ ][ ]
	 * [ ][ ][ ][O][X][ ][ ]
	 * [ ][ ][ ][X][O][X][O]
	 * [ ][ ][ ][O][X][O][X]
	 */
	@Test
	public void testCheckForWin_DiagonallyDown() {
		b.makeMove(Color.X, 6);
		b.makeMove(Color.O, 5);
		b.makeMove(Color.X, 5);
		b.makeMove(Color.O, 6);
		b.makeMove(Color.X, 4);
		b.makeMove(Color.O, 4);
		b.makeMove(Color.X, 4);
		b.makeMove(Color.O, 3);
		b.makeMove(Color.X, 3);
		b.makeMove(Color.O, 3);
		b.makeMove(Color.X, 3);
		
		assertWin(Color.X, Color.X, 3, 3);
	}
	
	/**
	 * Prüft die beiden Methoden
	 * <ul>
	 * 	<li>
	 * 	<li>
	 * </ul>
	 * @param expected
	 * @param actual
	 * @param x
	 * @param y
	 */
	private void assertWin(Color expected, Color actual, int x, int y) {
		assertEquals(expected==actual, VierGewinntBoardCheck.checkFourInARow(b, actual, x, y));
		assertEquals(expected, VierGewinntBoardCheck.checkForWin(b));
	}
}
