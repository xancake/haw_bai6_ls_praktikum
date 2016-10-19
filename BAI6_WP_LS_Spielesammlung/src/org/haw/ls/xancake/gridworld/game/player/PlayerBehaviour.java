package org.haw.ls.xancake.gridworld.game.player;

import java.util.List;
import org.haw.ls.xancake.gridworld.game.action.GridWorldAction;

public interface PlayerBehaviour {
	
	GridWorldAction chooseAction(List<GridWorldAction> availableActions);
}
