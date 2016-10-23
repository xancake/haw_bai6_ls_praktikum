package org.haw.ls.xancake.gridworld.game.world.io;

import static org.junit.Assert.*;
import java.io.ByteArrayOutputStream;
import org.haw.ls.xancake.gridworld.game.world.GridWorld;
import org.haw.ls.xancake.gridworld.game.world.field.DefaultFieldType;
import org.haw.ls.xancake.gridworld.game.world.field.GridWorldFieldType;
import org.junit.Test;

public class GridWorldIOTest {
	@Test
	public void testLoadGridWorld_5x3_empty() throws Exception {
		assertLoadGridWorld(
				"gridworld/5x3_empty.gridworld",
				5, 3,
				0, 2,
				field(4, 0, DefaultFieldType.FINISH)
		);
	}
	
	@Test
	public void testLoadGridWorld_5x3_cliff() throws Exception {
		assertLoadGridWorld(
				"gridworld/5x3_cliff.gridworld",
				5, 3,
				0, 2,
				field(1, 2, DefaultFieldType.DEATH),
				field(2, 2, DefaultFieldType.DEATH),
				field(3, 2, DefaultFieldType.DEATH),
				field(4, 2, DefaultFieldType.FINISH)
		);
	}
	
	@Test
	public void testLoadGridWorld_7x6_maze() throws Exception {
		assertLoadGridWorld(
				"gridworld/7x6_maze.gridworld",
				7, 6,
				1, 5,
				field(3, 0, DefaultFieldType.WALL),
				field(6, 0, DefaultFieldType.FINISH),
				field(1, 1, DefaultFieldType.WALL),
				field(5, 1, DefaultFieldType.WALL),
				field(6, 1, DefaultFieldType.WALL),
				field(1, 2, DefaultFieldType.WALL),
				field(3, 2, DefaultFieldType.WALL),
				field(4, 2, DefaultFieldType.WALL),
				field(1, 3, DefaultFieldType.WALL),
				field(2, 4, DefaultFieldType.WALL),
				field(3, 4, DefaultFieldType.WALL),
				field(4, 4, DefaultFieldType.WALL)
		);
	}
	
	private void assertLoadGridWorld(
			String worldPath,
			int expectedWidth, int expectedHeight,
			int expectedStartX, int expectedStartY,
			FieldExpectation... expectedFields
	) throws Exception {
		GridWorld world = new GridWorldIO().loadGridWorld(worldPath);
		assertEquals(expectedWidth, world.getWidth());
		assertEquals(expectedHeight, world.getHeight());
		assertEquals(expectedStartX, world.getStartField().getX());
		assertEquals(expectedStartY, world.getStartField().getY());
		for(int x=0; x<world.getWidth(); x++) {
			for(int y=0; y<world.getHeight(); y++) {
				FieldExpectation tester = new FieldExpectation(x, y, DefaultFieldType.EMPTY);
				for(FieldExpectation t : expectedFields) {
					if(t._x == x && t._y == y) {
						tester = t;
						break;
					}
				}
				assertEquals(
						"Field (" + x + "," + y + ") expected: <" + tester._type + "> but was: <" + world.getField(x, y).getType() + ">",
						tester._type,
						world.getField(x, y).getType()
				);
			}
		}
	}
	
	private FieldExpectation field(int x, int y, GridWorldFieldType type) {
		return new FieldExpectation(x, y, type);
	}
	
	private class FieldExpectation {
		private int _x;
		private int _y;
		private GridWorldFieldType _type;
		
		private FieldExpectation(int x, int y, GridWorldFieldType type) {
			_x = x;
			_y = y;
			_type = type;
		}
	}
	
	@Test
	public void testSaveGridWorld_5x3_empty() throws Exception {
		assertSaveGridWorld("gridworld/5x3_empty.gridworld",
				"gridworld 5x3" + System.getProperty("line.separator") +
				"____+" + System.getProperty("line.separator") +
				"_____" + System.getProperty("line.separator") +
				"_____" + System.getProperty("line.separator") +
				"start 0x2"
		);
	}
	
	@Test
	public void testSaveGridWorld_5x3_cliff() throws Exception {
		assertSaveGridWorld("gridworld/5x3_cliff.gridworld",
				"gridworld 5x3" + System.getProperty("line.separator") +
				"_____" + System.getProperty("line.separator") +
				"_____" + System.getProperty("line.separator") +
				"_---+" + System.getProperty("line.separator") +
				"start 0x2"
		);
	}
	
	@Test
	public void testSaveGridWorld_7x6_maze() throws Exception {
		assertSaveGridWorld("gridworld/7x6_maze.gridworld",
				"gridworld 7x6" + System.getProperty("line.separator") +
				"___#__+" + System.getProperty("line.separator") +
				"_#___##" + System.getProperty("line.separator") +
				"_#_##__" + System.getProperty("line.separator") +
				"_#_____" + System.getProperty("line.separator") +
				"__###__" + System.getProperty("line.separator") +
				"_______" + System.getProperty("line.separator") +
				"start 1x5"
		);
	}
	
	private void assertSaveGridWorld(String worldPath, String expectedOutput) throws Exception {
		GridWorldIO io = new GridWorldIO();
		GridWorld world = io.loadGridWorld(worldPath);
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		io.saveGridWorld(world, baos);
		assertEquals(expectedOutput, baos.toString());
	}
}
