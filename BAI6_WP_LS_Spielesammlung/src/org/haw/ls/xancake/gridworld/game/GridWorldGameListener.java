package org.haw.ls.xancake.gridworld.game;

public interface GridWorldGameListener {
	
	void onGameStarted();
	
	
	void onRoundStarted();
	
	
	void onRoundEnded();
	
	
	void onGameEnded();
}
