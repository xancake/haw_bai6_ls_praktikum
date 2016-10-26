package de.xancake.learning.qlearning.environment;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import de.xancake.learning.qlearning.agent.QLearningAgent_I;
import de.xancake.learning.qlearning.environment.feature.old.FeatureSet_I;

public class QLearningEnvironment {
	private QLearningAgent_I _agent;
	private FeatureSet_I _featureSet;
	private Map<String, Double> _rewards;
	
	protected int _currentState;
	protected int _lastState;
	protected int _lastAction;
	
	public QLearningEnvironment(QLearningAgent_I agent, FeatureSet_I featureSet) {
		if(agent.getQValues().getStateCount() != featureSet.getFeatureStateRange()) {
			throw new IllegalArgumentException("Die Menge der FeatureSet-Zustände muss der Anzahl der Zustände die der Agent in seiner QValueTable pflegt entsprechen!");
		}
		_agent = Objects.requireNonNull(agent);
		_featureSet = Objects.requireNonNull(featureSet);
		_rewards = new HashMap<>();
	}
	
	public QLearningAgent_I getQLearningAgent() {
		return _agent;
	}
	
	public FeatureSet_I getFeatureSet() {
		return _featureSet;
	}
	
	public int getCurrentState() {
		return _currentState;
	}
	
	public int getLastState() {
		return _lastState;
	}
	
	public int getLastAction() {
		return _lastAction;
	}
	
	public Set<String> getRewardKeys() {
		return _rewards.keySet();
	}
	
	public boolean containsRewardFor(String key) {
		return _rewards.containsKey(key);
	}
	
	public Double getReward(String key) {
		return _rewards.get(key);
	}
	
	public void setReward(String key, double reward) {
		if(!containsRewardFor(key)) {
			throw new IllegalArgumentException("Es existiert kein Reward für '" + key + "' der geändert werden könnte!");
		}
		_rewards.put(key, reward);
	}
	
	protected void putReward(String key, double reward) {
		_rewards.put(key, reward);
	}
	
	protected void updateFeatures() {
		_lastState = _currentState;
		_featureSet.updateFeatures();
		_currentState = getFeatureSet().getCurrentState();
	}
	
	protected void reward(String rewardKey) {
		reward(getReward(rewardKey));
	}
	
	protected void reward(double reward) {
		_agent.reward(_lastState, _lastAction, reward, _currentState);
	}
	
	protected int chooseAction(List<Integer> possibleActions) {
		_lastAction = _agent.chooseAction(_currentState, possibleActions);
		return _lastAction;
	}
}
