package org.haw.ls.xancake.gridworld.game.player;

import java.util.List;
import java.util.Objects;
import org.haw.ls.xancake.gridworld.game.action.GridWorldAction;
import org.haw.ls.xancake.gridworld.game.world.GridWorldField;

public class Player {
	private int _x;
	private int _y;
	private PlayerBehaviour _behaviour;
	
	public Player(PlayerBehaviour behaviour) {
		_behaviour = Objects.requireNonNull(behaviour);
	}
	
	public int getX() {
		return _x;
	}
	
	public int getY() {
		return _y;
	}
	
	public void setX(int x) {
		_x = x;
	}
	
	public void setY(int y) {
		_y = y;
	}
	
	public void moveTo(GridWorldField field) {
		setX(field.getX());
		setY(field.getY());
	}
	
	/**
	 * Veranlasst den Spieler eine Aktion zu wählen.
	 * @param availableActions Eine Liste seiner möglichen Aktionen
	 * @return Die ausgewählte Aktion
	 */
	public GridWorldAction chooseAction(List<GridWorldAction> availableActions) {
		return _behaviour.chooseAction(availableActions);
	}
}
