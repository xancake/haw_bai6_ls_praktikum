package de.xancake.learning.qlearning.model;

public interface DynaQModel_I {
	
	int getEstimatedSucessorStateFor(int state, int action);
	
	
	double getEstimatedRewardFor(int state, int action);
	
	
	void setEstimatedStateReward(int state, int action, int estimatedState, double estimatedReward);
}
