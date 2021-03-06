package org.haw.ls.xancake.gridworld.game.world;

import java.util.List;
import org.haw.ls.xancake.gridworld.game.action.GridWorldAction;
import org.haw.ls.xancake.gridworld.game.player.GridWorldPlayer;

public interface PlayableGridWorld extends GridWorld {
	/**
	 * Gibt den Spieler zurück.
	 * @return Der Spieler
	 */
	GridWorldPlayer getPlayer();
	
	/**
	 * Gibt zurück, ob das Spiel vorbei ist. Das Spiel ist entweder vorbei wenn der Spieler ein Zielfeld erreicht hat
	 * oder gestorben ist.
	 * @return {@code true} wenn das Spiel vorbei ist, ansonsten {@code false}
	 * @see GridWorldPlayer#isOnFinish()
	 * @see GridWorldPlayer#isDead()
	 */
	boolean isGameOver();
	
	/**
	 * Gibt alle Aktionen zurück, die grundsätzlich auf dieser Welt möglich sind.
	 * @return Eine Liste aller Aktionen
	 */
	List<GridWorldAction> getAllActions();
	
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
	 * Setzt den Spieler wieder auf das Startfeld.
	 */
	void reset();
	
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
