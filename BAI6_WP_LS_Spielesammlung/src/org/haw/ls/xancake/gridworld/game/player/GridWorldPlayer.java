package org.haw.ls.xancake.gridworld.game.player;

import java.util.List;
import java.util.Objects;
import org.haw.ls.xancake.gridworld.game.action.GridWorldAction;
import org.haw.ls.xancake.gridworld.game.world.GridWorldField;
import de.xancake.pattern.listener.EventDispatcher;

public class GridWorldPlayer {
	private EventDispatcher<GridWorldPlayerListener> _dispatcher;
	private GridWorldPlayerBehaviour _behaviour;
	private GridWorldField _field;
	private boolean _dead;
	
	public GridWorldPlayer() {
		_dispatcher = new EventDispatcher<>();
	}
	
	public GridWorldPlayerBehaviour getBehaviour() {
		return _behaviour;
	}
	
	public void setBehaviour(GridWorldPlayerBehaviour behaviour) {
		_behaviour = Objects.requireNonNull(behaviour);
	}
	
	public GridWorldField getField() {
		return _field;
	}
	
	public int getX() {
		return _field.getX();
	}
	
	public int getY() {
		return _field.getY();
	}
	
	public boolean isOnFinish() {
		return _field.isFinish();
	}
	
	public boolean isDead() {
		return _dead;
	}
	
	public void moveTo(GridWorldField field) {
		if(field.canEnter(this)) {
    		GridWorldField oldField = _field;
    		_field = Objects.requireNonNull(field);
    		if(oldField != null) {
    			_dispatcher.fireEvent(l -> l.onMove(this, oldField.getX(), oldField.getY(), _field.getX(), _field.getY()));
    		}
    		_field.enter(this);
		}
	}
	
	public void setDead(boolean dead) {
		_dead = dead;
		if(_dead) {
			_dispatcher.fireEvent(l -> l.onHasDied(this));
		}
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
