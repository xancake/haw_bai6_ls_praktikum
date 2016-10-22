package org.haw.ls.xancake.gridworld.game.world;

import org.haw.ls.xancake.gridworld.game.player.Player;

public interface GridWorldListener {
	
	void onPlayerMoved(Player player);
	
	
	default GridWorldListener adapter() {
		return new GridWorldListener() {
			@Override public void onPlayerMoved(Player player) {}
		};
	}
}
