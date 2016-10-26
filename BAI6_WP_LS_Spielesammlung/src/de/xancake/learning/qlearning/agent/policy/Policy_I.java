package de.xancake.learning.qlearning.agent.policy;

import java.util.List;
import de.xancake.learning.qlearning.agent.QLearningAgent_I;
import de.xancake.learning.qlearning.agent.qvalues.QValueArray;
import de.xancake.learning.qlearning.agent.qvalues.QValues_I;

/**
 * Schnittstelle für eine Entscheidungs-Policy, die in einem {@link QLearningAgent_I} verwendet wird.
 */
public interface Policy_I {
	/**
	 * Wählt eine Aktion aus der übergebenen Liste aus.
	 * @param state Der aktuelle Zustand des Agenten
	 * @param possibleActions Eine Liste aller möglichen Aktionen in dem Zustand
	 * @param qValues Die {@link QValueArray} in der die Bewertung der Aktionen pro Zustand enthalten ist
	 * @return Die ausgewählte Aktion
	 */
	int chooseAction(int state, List<Integer> possibleActions, QValues_I qValues);
}
