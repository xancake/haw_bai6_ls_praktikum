package org.haw.ls.xancake.viergewinnt.game;

import static org.junit.Assert.*;
import java.util.Arrays;
import org.haw.ls.xancake.viergewinnt.game.VierGewinntBoard;
import org.haw.ls.xancake.viergewinnt.game.VierGewinntBoard.Color;
import org.junit.Before;
import org.junit.Test;

public class VierGewinntBoardTest {
	private VierGewinntBoard b;
	
	@Before
	public void setUp() {
		b = new VierGewinntBoard();
	}
	
	@Test
	public void testGetRows() {
		assertEquals(7, b.getRows());
	}
	
	@Test
	public void testGetCols() {
		assertEquals(6, b.getCols());
	}
	
	@Test
	public void testGetValidMovesFor_Empty() {
		assertEquals(Arrays.asList(0, 1, 2, 3, 4, 5, 6), b.getValidMovesFor(Color.X));
	}
	
	@Test
	public void testGetValidMovesFor_OneRowFull() {
		assertEquals(Arrays.asList(0, 1, 2, 3, 4, 5, 6), b.getValidMovesFor(Color.X));
		b.makeMove(Color.X, 3);
		assertEquals(Arrays.asList(0, 1, 2, 3, 4, 5, 6), b.getValidMovesFor(Color.O));
		b.makeMove(Color.O, 3);
		assertEquals(Arrays.asList(0, 1, 2, 3, 4, 5, 6), b.getValidMovesFor(Color.X));
		b.makeMove(Color.X, 3);
		assertEquals(Arrays.asList(0, 1, 2, 3, 4, 5, 6), b.getValidMovesFor(Color.O));
		b.makeMove(Color.O, 3);
		assertEquals(Arrays.asList(0, 1, 2, 3, 4, 5, 6), b.getValidMovesFor(Color.X));
		b.makeMove(Color.X, 3);
		assertEquals(Arrays.asList(0, 1, 2, 3, 4, 5, 6), b.getValidMovesFor(Color.O));
		b.makeMove(Color.O, 3);
		assertEquals(Arrays.asList(0, 1, 2, 4, 5, 6), b.getValidMovesFor(Color.X));
	}
	
	@Test
	public void testIsPlayersTurn() {
		assertTrue(b.isColorsTurn(Color.X));
		assertFalse(b.isColorsTurn(Color.O));
		
		b.makeMove(Color.X, 3);
		assertFalse(b.isColorsTurn(Color.X));
		assertTrue(b.isColorsTurn(Color.O));
		
		b.makeMove(Color.O, 3);
		assertTrue(b.isColorsTurn(Color.X));
		assertFalse(b.isColorsTurn(Color.O));
		
		b.makeMove(Color.X, 3);
		assertFalse(b.isColorsTurn(Color.X));
		assertTrue(b.isColorsTurn(Color.O));
		
		b.makeMove(Color.O, 3);
		assertTrue(b.isColorsTurn(Color.X));
		assertFalse(b.isColorsTurn(Color.O));
	}
	
	@Test
	public void testIsPlayersTurn_Null() {
		assertFalse(b.isColorsTurn(null));
		b.makeMove(Color.X, 3);
		assertFalse(b.isColorsTurn(null));
		b.makeMove(Color.O, 4);
		assertFalse(b.isColorsTurn(null));
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
		
		assertFalse(b.isGameOver());
		assertEquals(null, b.getWinner());
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
	public void testGameOverAndGetWinner_Vertically() {
		b.makeMove(Color.X, 3);
		b.makeMove(Color.O, 4);
		b.makeMove(Color.X, 3);
		b.makeMove(Color.O, 4);
		b.makeMove(Color.X, 3);
		b.makeMove(Color.O, 4);
		b.makeMove(Color.X, 3);
		
		assertTrue(b.isGameOver());
		assertEquals(Color.X, b.getWinner());
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
	public void testGameOverAndGetWinner_Horizontally() {
		b.makeMove(Color.X, 3);
		b.makeMove(Color.O, 3);
		b.makeMove(Color.X, 4);
		b.makeMove(Color.O, 4);
		b.makeMove(Color.X, 5);
		b.makeMove(Color.O, 5);
		b.makeMove(Color.X, 6);
		
		assertTrue(b.isGameOver());
		assertEquals(Color.X, b.getWinner());
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
	public void testGameOverAndGetWinner_DiagonallyUp() {
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
		
		assertTrue(b.isGameOver());
		assertEquals(Color.X, b.getWinner());
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
	public void testGameOverAndGetWinner_DiagonallyDown() {
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
		
		assertTrue(b.isGameOver());
		assertEquals(Color.X, b.getWinner());
	}
	
	@Test
	public void testToString() {
		String expected =
				"[ ][ ][ ][ ][ ][ ][ ]\n" +
				"[ ][ ][ ][ ][ ][ ][ ]\n" +
				"[ ][ ][ ][ ][ ][ ][ ]\n" +
				"[ ][ ][ ][ ][ ][ ][ ]\n" +
				"[ ][ ][ ][ ][ ][ ][ ]\n" +
				"[ ][ ][ ][ ][ ][ ][ ]\n";
		assertEquals(expected, b.toString());
	}
}
