package de.xancake.learning.qlearning.model;

public class DynaQModelArray implements DynaQModel_I {
	private int[][] _successorStates;
	private double[][] _estimatedRewards;
	
	public DynaQModelArray(int states, int actions) {
		_successorStates = new int[states][actions];
		_estimatedRewards = new double[states][actions];
	}
	
	@Override
	public int getEstimatedSucessorStateFor(int state, int action) {
		return _successorStates[state][action];
	}
	
	@Override
	public double getEstimatedRewardFor(int state, int action) {
		return _estimatedRewards[state][action];
	}
	
	@Override
	public void setEstimatedStateReward(int state, int action, int estimatedState, double estimatedReward) {
		_successorStates[state][action] = estimatedState;
		_estimatedRewards[state][action] = estimatedReward;
	}
}
