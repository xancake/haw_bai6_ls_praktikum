package de.xancake.learning.qlearning.agent.qvalues;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class QValuesMap implements QValues_I {
	private Map<StateAction, Double> _qValues;
	private Map<Integer, Set<Integer>> _stateActionMapping;
	
	public QValuesMap() {
		_qValues = new HashMap<>();
		_stateActionMapping = new HashMap<>();
	}
	
	@Override
	public int getStateCount() {
		return _stateActionMapping.size();
	}
	
	@Override
	public int getActionCount() {
		int maxActionCount = 0;
		for(Integer state : _stateActionMapping.keySet()) {
    		Set<Integer> actions = _stateActionMapping.get(state);
    		if(maxActionCount < actions.size()) {
    			maxActionCount = actions.size();
    		}
		}
		return maxActionCount;
	}
	
	@Override
	public double getQValue(int state, int action) {
		Double q = _qValues.get(new StateAction(state, action));
		return q==null ? 0 : q;
	}
	
	@Override
	public void setQValue(int state, int action, double newQValue) {
		_qValues.put(new StateAction(state, action), newQValue);
		
		Set<Integer> actions = _stateActionMapping.get(state);
		if(actions == null) {
			actions = new HashSet<>();
			_stateActionMapping.put(state, actions);
		}
		if(!actions.contains(action)) {
			actions.add(action);
		}
	}
	
	@Override
	public double getBestQValueOfState(int state) {
		return _qValues.get(new StateAction(state, getBestActionOfState(state)));
	}
	
	@Override
	public int getBestActionOfState(int state) {
		Set<Integer> actions = _stateActionMapping.get(state);
		if(actions != null) {
			int bestAction = -1;
			double bestQ = Double.MIN_VALUE;
			for(Integer action : actions) {
				double q = _qValues.get(new StateAction(state, action));
				if(q > bestQ) {
					bestQ = q;
					bestAction = action;
				}
			}
			return bestAction;
		}
		return -1;
	}
	
	private class StateAction extends Pair<Integer, Integer> {
		public StateAction(Integer state, Integer action) {
			super(state, action);
		}
	}
}
