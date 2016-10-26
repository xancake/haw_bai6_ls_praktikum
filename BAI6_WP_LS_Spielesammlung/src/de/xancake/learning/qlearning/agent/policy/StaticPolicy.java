package de.xancake.learning.qlearning.agent.policy;

import java.util.List;
import de.xancake.learning.qlearning.agent.qvalues.QValues_I;

/**
 * Eine Entscheidungs-Policy, die immer die am besten bewertete Aktion wählt.
 * 
 * <p>Diese Policy bietet sich insbesondere für ausgelernte Agenten an. Wenn der Agent noch nicht ausgelernt hat,
 * kann es mit dieser Policy passieren, dass er obwohl es eventuell bessere Wege gibt immer bei dem besten ihm
 * bekannten Weg bleibt. Bei bestimmten Konstellationen der QWerte kann es auch passieren, dass der Agent in eine
 * Endlosschleife läuft, das ist insbesondere der Fall wenn es keine Wegkosten (negative Belohnung für jede Aktion)
 * gibt.
 */
public class StaticPolicy implements Policy_I {
	@Override
	public int chooseAction(int state, List<Integer> possibleActions, QValues_I qValues) {
		double highestQValue = -Double.MAX_VALUE;
		int choosenAction = -1;
		for(Integer action : possibleActions) {
			double currentQValue = qValues.getQValue(state, action);
			if(currentQValue > highestQValue) {
				highestQValue = currentQValue;
				choosenAction = action;
			}
		}
		return choosenAction;
	}
}
