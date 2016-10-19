package org.haw.ls.xancake.gridworld.game.world;

import java.util.List;
import org.haw.ls.xancake.gridworld.game.action.GridWorldAction;
import org.haw.ls.xancake.gridworld.game.player.Player;

public interface GridWorld {
	/**
	 * Gibt die Breite der Spielwelt zurück.
	 * @return Die Breite
	 */
	int getWidth();
	
	/**
	 * Gibt die Höhe der Spielwelt zurück.
	 * @return Die Höhe
	 */
	int getHeight();
	
	/**
	 * Gibt zurück, ob es sich bei den X,Y-Koordinaten um Koordinaten in dieser GridWorld handelt.
	 * @param x Die X-Koordinate
	 * @param y Die Y-Koordinate
	 * @return {@code true} wenn die Koordinaten in der GridWorld liegen, ansonsten {@code false}
	 */
	boolean isValidLocation(int x, int y);
	
	/**
	 * Gibt das Feld an den X,Y-Koordinaten zurück.
	 * @param x Die X-Koordinate
	 * @param y Die Y-Koordinate
	 * @return Das Feld an den X,Y-Koordinaten
	 */
	GridWorldField getField(int x, int y);
	
	/**
	 * Gibt das Startfeld zurück. Es kann immer nur ein Startfeld existieren.
	 * @return Das Startfeld
	 */
	GridWorldField getStartField();
	
	/**
	 * Gibt die möglichen Aktionen des Spielers zurück.
	 * @param Player Der Spieler
	 * @return Die Aktionen, die dem Spieler möglich sind
	 */
	List<GridWorldAction> getAllowedActions(Player player);
}
