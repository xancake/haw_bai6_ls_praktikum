package org.haw.ls.xancake.gridworld.game.world;

import org.haw.ls.xancake.gridworld.game.player.GridWorldPlayer;

public interface GridWorldListener {
	
	void onPlayerMoved(GridWorldPlayer player);
	
	
	default GridWorldListener adapter() {
		return new GridWorldListener() {
			@Override public void onPlayerMoved(GridWorldPlayer player) {}
		};
	}
}
