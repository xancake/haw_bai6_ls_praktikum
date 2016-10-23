package org.haw.ls.xancake.gridworld.game.world.field;

import org.haw.ls.xancake.gridworld.game.player.GridWorldPlayer;
import org.haw.ls.xancake.gridworld.game.world.GridWorldField;

public interface GridWorldFieldType {
	/**
	 * Prüft, ob der Spieler das übergebene Feld betreten kann.
	 * @param field Das Feld
	 * @param player Der Spieler
	 * @return {@code true} wenn der Spieler das Feld betreten darf, ansonsten {@code false}
	 */
	boolean canEnter(GridWorldField field, GridWorldPlayer player);
	
	/**
	 * Wird aufgerufen, wenn der übergebene Spieler das übergebene Feld betritt.
	 * @param field Das Feld
	 * @param player Der Spieler
	 */
	void onEnter(GridWorldField field, GridWorldPlayer player);
}
