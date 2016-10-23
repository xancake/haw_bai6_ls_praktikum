package org.haw.ls.xancake.gridworld.game.world;

import org.haw.ls.xancake.gridworld.game.world.field.GridWorldFieldType;

public interface MutableGridWorld extends GridWorld {
	
	void setFieldType(int x, int y, GridWorldFieldType field);
	
	
	void setStartField(int x, int y);
}
