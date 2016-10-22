package org.haw.ls.xancake.gridworld.game.player;

import java.util.List;
import java.util.Objects;
import org.haw.ls.xancake.gridworld.game.action.GridWorldAction;
import org.haw.ls.xancake.gridworld.game.world.GridWorldField;
import de.xancake.pattern.listener.EventDispatcher;

public class Player {
	private EventDispatcher<PlayerListener> _dispatcher;
	private int _x;
	private int _y;
	private PlayerBehaviour _behaviour;
	
	public Player() {
		_dispatcher = new EventDispatcher<>();
	}
	
	public int getX() {
		return _x;
	}
	
	public int getY() {
		return _y;
	}
	
	public void moveTo(GridWorldField field) {
		int oldX = _x;
		int oldY = _y;
		_x = field.getX();
		_y = field.getY();
		_dispatcher.fireEvent(l -> l.onMove(this, oldX, oldY, _x, _y));
	}
	
	public PlayerBehaviour getBehaviour() {
		return _behaviour;
	}
	
	public void setBehaviour(PlayerBehaviour behaviour) {
		_behaviour = Objects.requireNonNull(behaviour);
	}
	
	public void addListener(PlayerListener listener) {
		_dispatcher.addListener(listener);
	}
	
	public void removeListener(PlayerListener listener) {
		_dispatcher.removeListener(listener);
	}
	
	/**
	 * Veranlasst den Spieler eine Aktion zu wählen.
	 * @param availableActions Eine Liste seiner möglichen Aktionen
	 * @return Die ausgewählte Aktion
	 * @throws NullPointerException, wenn vorher kein {@link PlayerBehaviour} gesetzt wurde
	 * @see #setBehaviour(PlayerBehaviour)
	 */
	public GridWorldAction chooseAction(List<GridWorldAction> availableActions) {
		return _behaviour.chooseAction(availableActions);
	}
}
