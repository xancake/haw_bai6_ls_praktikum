package de.xancake.learning.qlearning.environment.feature.old;

import java.util.List;
import java.util.Objects;

/**
 * Abstrakte Oberklasse für {@link Feature_I Features}.
 */
public abstract class AbstractFeature implements Feature_I {
	private String _name;
	private AbstractFeatureState _currentState;
	private List<AbstractFeatureState> _featureStates;
	
	public AbstractFeature(String name) {
		_name = Objects.requireNonNull(name);
	}
	
	@Override
	public int getCurrentState() {
		return getCurrentFeatureState().getStateNumber();
	}
	
	@Override
	public int getPossibleAllocations() {
		return getStatesLazily().size();
	}
	
	@Override
	public FeatureState_I getCurrentFeatureState() {
		return _currentState;
	}
	
	@Override
	public List<? extends FeatureState_I> getFeatureStates() {
		return getStatesLazily();
	}
	
	@Override
	public String getName() {
		return _name;
	}
	
	@Override
	public String toString() {
		return getName();
	}
	
	@Override
	public void updateState() {
		for(AbstractFeatureState state : getStatesLazily()) {
			if(state.appliesTo()) {
				_currentState = state;
				break;
			}
		}
	}
	
	private List<AbstractFeatureState> getStatesLazily() {
		if(_featureStates == null) {
			_featureStates = createStates();
			_currentState = _featureStates.get(0);
		}
		return _featureStates;
	}
	
	protected abstract List<AbstractFeatureState> createStates();
	
	protected static class AbstractFeatureState implements FeatureState_I {
		private int _number;
		private String _description;
		private StateChecker_I _checker;
		
		public AbstractFeatureState(int number, String description, StateChecker_I checker) {
			_number = number;
			_description = description;
			_checker = Objects.requireNonNull(checker);
		}
		
		@Override
		public int getStateNumber() {
			return _number;
		}
		
		@Override
		public String getDescription() {
			return _description;
		}
		
		protected boolean appliesTo() {
			return _checker.appliesTo();
		}
	}
	
	@FunctionalInterface
	protected static interface StateChecker_I {
		/**
		 * Prüft ob dieser Zustand zutrifft (aktiv ist/werden muss).
		 * @return {@code true} wenn der Zustand zutrifft, ansonsten {@code false}
		 */
		boolean appliesTo();
	}
}
