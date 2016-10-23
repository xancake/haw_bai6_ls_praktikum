package org.haw.ls.xancake.gridworld.game.player;

import java.util.List;
import org.haw.ls.xancake.gridworld.game.action.GridWorldAction;

/**
 * Das Verhalten für einen {@link GridWorldPlayer Spieler}.
 */
public interface GridWorldPlayerBehaviour {
	/**
	 * Wählt eine Aktion aus der übergebenen Liste der möglichen Aktionen aus.
	 * @param availableActions Eine Liste der möglichen Aktionen
	 * @return Die ausgewählte Aktion
	 */
	GridWorldAction chooseAction(List<GridWorldAction> availableActions);
}
