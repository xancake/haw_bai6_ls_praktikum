package org.haw.ls.xancake.gridworld.game.player;

public interface GridWorldPlayerListener {
	
	void onMove(GridWorldPlayer player, int xFrom, int yFrom, int xTo, int yTo);
	
	
	default GridWorldPlayerListener adapter() {
		return new GridWorldPlayerListener() {
			@Override public void onMove(GridWorldPlayer player, int xFrom, int yFrom, int xTo, int yTo) {}
		};
	}
}
