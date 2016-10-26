package de.xancake.learning.qlearning.agent.qvalues;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import de.xancake.pattern.listener.EventDispatcher;

/**
 * Ermöglicht das Lauschen auf Veränderungen der Q-Werte.
 */
public class ListenableQValues implements QValues_I {
	private EventDispatcher<QValuesListener_I> _eventDispatcher;
	private Set<Integer> _visitedStates;
	
	private QValues_I _delegate;
	
	public ListenableQValues(QValues_I delegate) {
		_eventDispatcher = new EventDispatcher<>();
		_visitedStates = new HashSet<>();
		_delegate = Objects.requireNonNull(delegate);
	}
	
	@Override
	public int getStateCount() {
		return _delegate.getStateCount();
	}
	
	@Override
	public int getActionCount() {
		return _delegate.getActionCount();
	}
	
	@Override
	public double getQValue(int state, int action) {
		return _delegate.getQValue(state, action);
	}
	
	@Override
	public void setQValue(int state, int action, double newQValue) {
		if(!_visitedStates.contains(state)) {
			_visitedStates.add(state);
			_eventDispatcher.fireEvent(l->l.onNewStateReached(state, getVisitedStateCount(), getStateCount()));
		}
		_delegate.setQValue(state, action, newQValue);
		_eventDispatcher.fireEvent(l -> l.onQValueChanged(state, action, newQValue));
	}
	
	@Override
	public double getBestQValueOfState(int state) {
		return _delegate.getBestQValueOfState(state);
	}
	
	@Override
	public int getBestActionOfState(int state) {
		return _delegate.getBestActionOfState(state);
	}
	
	/**
	 * Registriert den übergebenen Listener an dieser QValues.
	 * @param listener Der zu registrierende Listener
	 */
	public void addListener(QValuesListener_I listener) {
		_eventDispatcher.addListener(listener);
	}
	
	/**
	 * Trägt den übergebenen Listener aus der QValues aus.
	 * @param listener Der auszutragende Listener
	 */
	public void removeListener(QValuesListener_I listener) {
		_eventDispatcher.removeListener(listener);
	}
	
	/**
	 * Gibt zurück, wieviele Zustände bisher besucht wurden.
	 * @return Die Anzahl der besuchten Zustände
	 */
	public int getVisitedStateCount() {
		return _visitedStates.size();
	}
}
