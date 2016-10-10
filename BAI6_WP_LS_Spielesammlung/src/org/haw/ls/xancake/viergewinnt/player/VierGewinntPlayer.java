package org.haw.ls.xancake.viergewinnt.player;

import java.util.List;
import org.haw.ls.xancake.viergewinnt.game.VierGewinntBoard.Color;

public interface VierGewinntPlayer {
	
	Color getColor();
	
	
	void setColor(Color color);
	
	
	int chooseMove(List<Integer> validMoves);
}
