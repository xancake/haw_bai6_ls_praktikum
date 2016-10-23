package org.haw.ls.xancake.gridworld.game.world;

import org.haw.ls.xancake.gridworld.game.player.GridWorldPlayer;

public interface GridWorldListener {
	/**
	 * Wird aufgerufen, wenn der Spieler bewegt wurde.
	 * @param world Die Welt
	 * @param player Der Spieler
	 */
	void onPlayerMoved(GridWorld world, GridWorldPlayer player);
}
