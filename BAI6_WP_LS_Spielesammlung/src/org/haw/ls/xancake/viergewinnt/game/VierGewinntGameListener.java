package org.haw.ls.xancake.viergewinnt.game;

import java.util.List;
import org.haw.ls.xancake.viergewinnt.game.VierGewinntBoard.Color;
import org.haw.ls.xancake.viergewinnt.player.VierGewinntPlayer;

public interface VierGewinntGameListener {
	
	void onGameStart();
	
	
	void onGameEnd(Color winner);
	
	
	void onRoundStart();
	
	
	void onRoundEnd();
	
	
	void onBeforePlayersTurn(VierGewinntPlayer player, List<Integer> validChoices);
	
	
	void onAfterPlayersTurn(VierGewinntPlayer player, List<Integer> validChoices, Integer choice);
	
	
	void onIllegalChoice(VierGewinntPlayer player, List<Integer> validChoices, Integer choice);
}
