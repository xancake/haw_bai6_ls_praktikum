package org.haw.ls.xancake.gridworld.game.world;

import java.util.List;
import org.haw.ls.xancake.gridworld.game.action.GridWorldAction;
import org.haw.ls.xancake.gridworld.game.player.Player;

public interface PlayableGridWorld extends GridWorld {
	/**
	 * Gibt den Spieler zurück.
	 * @return Der Spieler
	 */
	Player getPlayer();
	
	/**
	 * Gibt zurück, ob sich der Spieler auf einem Zielfeld befindet.
	 * @return {@code true} wenn sich der Spieler auf einem Zielfeld befindet, ansonsten {@code false}
	 */
	boolean isPlayerOnFinish();
	
	/**
	 * Gibt die möglichen Aktionen des Spielers für die aktuelle Weltsituation zurück.
	 * @return Die Aktionen, die dem Spieler möglich sind
	 */
	List<GridWorldAction> getAllowedActions();
	
	/**
	 * Führt die übergebene Aktion aus.
	 * @param action Die auszuführende Aktion
	 */
	void doPlayerMove(GridWorldAction action);
	
	/**
	 * Registriert den übergebenen Listener an dieser GridWorld.
	 * @param listener Der zu registrierende Listener
	 */
	void addListener(GridWorldListener listener);
	
	/**
	 * Entfernt den übergebenen Listener von dieser GridWorld.
	 * @param listener Der zu entfernende Listener
	 */
	void removeListener(GridWorldListener listener);
}
