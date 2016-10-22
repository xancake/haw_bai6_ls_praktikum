package org.haw.ls.xancake.gridworld.game.player;

public interface PlayerListener {
	
	void onMove(Player player, int xFrom, int yFrom, int xTo, int yTo);
	
	
	default PlayerListener adapter() {
		return new PlayerListener() {
			@Override public void onMove(Player player, int xFrom, int yFrom, int xTo, int yTo) {}
		};
	}
}
