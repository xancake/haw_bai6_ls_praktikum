package org.haw.ls.xancake.gridworld.game.world;

import org.haw.ls.xancake.gridworld.game.field.FieldType;

public interface MutableGridWorld extends GridWorld {
	
	void setFieldType(int x, int y, FieldType field);
	
	
	void setStartField(int x, int y);
}
