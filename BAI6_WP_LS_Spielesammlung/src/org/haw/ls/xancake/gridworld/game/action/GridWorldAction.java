package org.haw.ls.xancake.gridworld.game.action;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.haw.ls.xancake.gridworld.game.player.Player;
import org.haw.ls.xancake.gridworld.game.world.GridWorldField;
import org.haw.ls.xancake.gridworld.game.world.PlayableGridWorld;

/**
 * Enthält alle Aktionen, die in einer {@link PlayableGridWorld GridWorld} möglich sind.
 */
public enum GridWorldAction {
	UP(0, -1),
	RIGHT(1, 0),
	DOWN(0, 1),
	LEFT(-1, 0);
	
	private int _dx;
	private int _dy;
	
	private GridWorldAction(int dx, int dy) {
		_dx = dx;
		_dy = dy;
	}
	
	/**
	 * Prüft, ob diese Aktion auf der übergebenen {@link PlayableGridWorld Welt} ausgeführt werden kann.
	 * @param world Die {@link PlayableGridWorld Welt}
	 * @return {@code true} wenn die Aktion ausgeführt werden kann, ansonsten {@code false}
	 */
	public boolean isAllowed(PlayableGridWorld world) {
		Player player = world.getPlayer();
		int moveX = player.getX()+_dx;
		int moveY = player.getY()+_dy;
		return world.isValidLocation(moveX, moveY) && world.getField(moveX, moveY).canEnter(player);
	}
	
	/**
	 * Führt diese Aktion auf der übergebenen {@link PlayableGridWorld Welt} aus, wenn sie erlaubt ist.
	 * @param world Die {@link PlayableGridWorld Welt}
	 * @see #isAllowed(PlayableGridWorld)
	 */
	public void execute(PlayableGridWorld world) {
		if(isAllowed(world)) {
			GridWorldField field = getResultingField(world);
			world.getPlayer().moveTo(field);
		}
	}
	
	/**
	 * Gibt das {@link GridWorldField Feld} zurück, auf dem der Spieler landen würde, wenn er diese Aktion auf der
	 * übergebenen {@link PlayableGridWorld Welt} ausführt.
	 * @param world Die {@link PlayableGridWorld Welt}
	 * @return Das Feld auf dem der Spieler landen würde oder {@code null}, wenn das resultierende Feld nicht mehr auf
	 *         der Welt liegt
	 */
	public GridWorldField getResultingField(PlayableGridWorld world) {
		Player player = world.getPlayer();
		int moveX = player.getX()+_dx;
		int moveY = player.getY()+_dy;
		if(world.isValidLocation(moveX, moveY)) {
			return world.getField(moveX, moveY);
		} else {
			return null;
		}
	}
	
	/**
	 * Gibt eine Liste mit allen Aktionen zurück.
	 * @return Eine Liste mit allen Aktionen
	 * @see #values()
	 */
	public static List<GridWorldAction> getAllActions() {
		return Arrays.asList(values());
	}
	
	/**
	 * Gibt eine Liste aller validen Aktionen für den Spieler auf der übergebenen {@link PlayableGridWorld Welt} zurück.
	 * @param world Die {@link PlayableGridWorld Welt}
	 * @return Eine Liste mit allen validen Aktionen für die Weltsituation
	 */
	public static List<GridWorldAction> getAllowedActions(PlayableGridWorld world) {
		List<GridWorldAction> actions = new ArrayList<>();
		for(GridWorldAction action : values()) {
			if(action.isAllowed(world)) {
				actions.add(action);
			}
		}
		return actions;
	}
}
