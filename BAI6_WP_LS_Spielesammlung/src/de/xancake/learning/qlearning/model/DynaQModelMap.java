package de.xancake.learning.qlearning.model;

import java.util.HashMap;
import java.util.Map;
import de.xancake.learning.qlearning.agent.qvalues.Pair;

public class DynaQModelMap implements DynaQModel_I {
	private Map<StateAction, StateReward> _model;
	
	public DynaQModelMap() {
		_model = new HashMap<>();
	}
	
	@Override
	public int getEstimatedSucessorStateFor(int state, int action) {
		return getEstimatedStateRewardFor(state, action).getKey();
	}
	
	@Override
	public double getEstimatedRewardFor(int state, int action) {
		return getEstimatedStateRewardFor(state, action).getValue();
	}
	
	private StateReward getEstimatedStateRewardFor(int state, int action) {
		return _model.get(new StateAction(state, action));
	}
	
	@Override
	public void setEstimatedStateReward(int state, int action, int estimatedState, double estimatedReward) {
		_model.put(new StateAction(state, action), new StateReward(estimatedState, estimatedReward));
	}
	
	private class StateAction extends Pair<Integer, Integer> {
		public StateAction(Integer state, Integer action) {
			super(state, action);
		}
	}
	
	private class StateReward extends Pair<Integer, Double> {
		public StateReward(Integer state, Double reward) {
			super(state, reward);
		}
	}
}
