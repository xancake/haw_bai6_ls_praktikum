package org.haw.ls.xancake.gridworld.game.player;

import java.util.List;
import java.util.Objects;
import org.haw.ls.xancake.gridworld.game.action.GridWorldAction;
import org.haw.ls.xancake.gridworld.game.world.GridWorldField;
import de.xancake.pattern.listener.EventDispatcher;

public class GridWorldPlayer {
	private EventDispatcher<GridWorldPlayerListener> _dispatcher;
	private int _x;
	private int _y;
	private GridWorldPlayerBehaviour _behaviour;
	
	public GridWorldPlayer() {
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
	
	public GridWorldPlayerBehaviour getBehaviour() {
		return _behaviour;
	}
	
	public void setBehaviour(GridWorldPlayerBehaviour behaviour) {
		_behaviour = Objects.requireNonNull(behaviour);
	}
	
	public void addListener(GridWorldPlayerListener listener) {
		_dispatcher.addListener(listener);
	}
	
	public void removeListener(GridWorldPlayerListener listener) {
		_dispatcher.removeListener(listener);
	}
	
	/**
	 * Veranlasst den Spieler eine Aktion zu wählen.
	 * @param availableActions Eine Liste seiner möglichen Aktionen
	 * @return Die ausgewählte Aktion
	 * @throws NullPointerException, wenn vorher kein {@link GridWorldPlayerBehaviour} gesetzt wurde
	 * @see #setBehaviour(GridWorldPlayerBehaviour)
	 */
	public GridWorldAction chooseAction(List<GridWorldAction> availableActions) {
		return _behaviour.chooseAction(availableActions);
	}
}
