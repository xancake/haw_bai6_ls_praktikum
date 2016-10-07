package org.haw.ls.xancake.viergewinnt.game;

import org.haw.ls.xancake.viergewinnt.game.VierGewinntBoard.Color;

public interface VierGewinntBoardListener {
	
	void onTurn(Color color, int x, int y);
	
	
	void onGameOver(Color winner);
}
