package de.xancake.learning.qlearning.environment.feature.old;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import de.xancake.pattern.listener.EventDispatcher;

public class FeatureSet implements FeatureSet_I {
	private EventDispatcher<FeatureSetListener_I> _eventDispatcher;
	
	private List<Feature_I> _features;
	
	public FeatureSet(Feature_I... features) {
		this(Arrays.asList(features));
	}
	
	public FeatureSet(Collection<? extends Feature_I> features) {
		_eventDispatcher = new EventDispatcher<>();
		_features = new ArrayList<>(Objects.requireNonNull(features));
	}
	
	@Override
	public void updateFeatures() {
		for(Feature_I feature : _features) {
			int oldState = feature.getCurrentState();
			feature.updateState();
			int newState = feature.getCurrentState();
			_eventDispatcher.fireEvent(l -> l.onFeatureStateChanged(feature, oldState, newState));
		}
	}
	
	@Override
	public int getCurrentState() {
		int state = 0;
		int stateAllocationScope = 1;
		// Hier ist die Reihenfolge wichtig, damit es konsistent mit der Sortierung in #getDescription(int) bleibt
		for(int i=_features.size()-1; i>=0; i--) {
			Feature_I feature = _features.get(i);
			state += feature.getCurrentState() * stateAllocationScope;
			stateAllocationScope *= feature.getPossibleAllocations();
		}
		return state;
	}
	
	@Override
	public int getFeatureStateRange() {
		int possibleStates = 1;
		// Hier ist die Reihenfolge wichtig, damit es konsistent mit der Sortierung in #getDescription(int) bleibt
		for(int i=_features.size()-1; i>=0; i--) {
			Feature_I feature = _features.get(i);
			possibleStates *= feature.getPossibleAllocations();
		}
		return possibleStates;
	}
	
	@Override
	public String getDescription(int state) {
		String representation = "";
		int stateAllocationScope = 1;
		// Die umgedrehte Reihenfolge sorgt dafür, dass die Repräsentation der Features wie bei aussagenlogischen
		// Wahr-/Falsch-Tabellen aufgelistet wird (000, 001, 002, 010, 011, ...)
		for(int i=_features.size()-1; i>=0; i--) {
			Feature_I feature = _features.get(i);
			
			if(i != _features.size()) {
				representation = " " + representation;
			}
			representation = String.valueOf(state / stateAllocationScope % feature.getPossibleAllocations()) + representation;
			
			stateAllocationScope *= feature.getPossibleAllocations();
		}
		return representation;
	}
	
	@Override
	public List<Feature_I> getFeatures() {
		return Collections.unmodifiableList(_features);
	}
	
	@Override
	public void addListener(FeatureSetListener_I listener) {
		_eventDispatcher.addListener(listener);
	}
	
	@Override
	public void removeListener(FeatureSetListener_I listener) {
		_eventDispatcher.removeListener(listener);
	}
}
