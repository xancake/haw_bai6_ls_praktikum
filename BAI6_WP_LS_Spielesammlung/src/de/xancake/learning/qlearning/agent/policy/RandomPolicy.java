package de.xancake.learning.qlearning.agent.policy;

import java.util.List;
import java.util.Random;
import de.xancake.learning.qlearning.agent.qvalues.QValues_I;

/**
 * Eine Entscheidungs-Policy, die die Aktion immer zufällig wählt.
 * 
 * <p>Diese Policy bietet sich für das Anlernen eines komplett ungelernten Agenten an, sodass sich der Agent eine
 * breite Erfahrungsbasis aufbauen kann und möglichst viele Zustände besucht.
 */
public class RandomPolicy implements Policy_I {
	private final Random _randomNumberGenerator = new Random();
	
	@Override
	public int chooseAction(int state, List<Integer> possibleActions, QValues_I qValues) {
		return possibleActions.get(_randomNumberGenerator.nextInt(possibleActions.size()));
	}
}
