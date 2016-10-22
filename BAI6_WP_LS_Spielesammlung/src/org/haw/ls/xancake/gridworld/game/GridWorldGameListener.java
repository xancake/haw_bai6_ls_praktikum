package org.haw.ls.xancake.gridworld.game;

public interface GridWorldGameListener {
	
	void onGameStarted();
	
	
	void onGameEnded();
	
	
	void onRoundStarted();
	
	
	void onRoundEnded();
	
	
	default GridWorldGameListener adapter() {
		return new GridWorldGameListener() {
			@Override public void onRoundStarted() {}
			@Override public void onRoundEnded() {}
			@Override public void onGameStarted() {}
			@Override public void onGameEnded() {}
		};
	}
}
