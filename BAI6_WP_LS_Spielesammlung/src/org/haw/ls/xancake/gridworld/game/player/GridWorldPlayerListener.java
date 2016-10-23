package org.haw.ls.xancake.gridworld.game.player;

public interface GridWorldPlayerListener {
	/**
	 * Wird aufgerufen, wenn der Spieler von einem Ort zu einem anderen bewegt wird.
	 * @param player Der Spieler
	 * @param xFrom Die alte X-Koordinate
	 * @param yFrom Die alte Y-Koordinate
	 * @param xTo Die neue X-Koordinate
	 * @param yTo Die neue Y-Koordinate
	 */
	void onMove(GridWorldPlayer player, int xFrom, int yFrom, int xTo, int yTo);
	
	/**
	 * Wird aufgerufen, wenn der Spieler gestorben ist.
	 * @param player Der Spieler
	 */
	void onHasDied(GridWorldPlayer player);
}
