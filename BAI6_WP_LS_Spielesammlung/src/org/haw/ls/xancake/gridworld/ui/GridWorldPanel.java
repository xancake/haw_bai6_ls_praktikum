package org.haw.ls.xancake.gridworld.ui;

import org.haw.ls.xancake.gridworld.game.world.GridWorld;

public interface GridWorldPanel {
	
	GridWorld getWorld();
	
	
	void setWorld(GridWorld world);
	
	
	int getGridSize();
	
	
	void setGridSize(int size);
	
	
	boolean isHighlightPossibleActions();
	
	
	void setHighlightPossibleActions(boolean highlight);
	
	
	boolean isHighlightMouseLocation();
	
	
	void setHighlightMouseLocation(boolean highlight);
}
