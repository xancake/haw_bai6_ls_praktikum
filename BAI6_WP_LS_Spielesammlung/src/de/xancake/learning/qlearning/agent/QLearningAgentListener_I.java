package de.xancake.learning.qlearning.agent;

import de.xancake.learning.qlearning.agent.policy.Policy_I;

/**
 * Eine Schnittstelle um Änderungen eines {@link QLearningAgent_I} mitzubekommen.
 */
public interface QLearningAgentListener_I {
	/**
	 * Wird dann vom Agenten aufgerufen, wenn er eine Aktion ausgewählt hat.
	 * <p>Wenn die gewählte Aktion von der Besten abweicht kann davon ausgegangen werden, dass exploriert wurde.
	 * @param state Der Zustand für den eine Aktion gewählt wurde
	 * @param choosenAction Die ausgewählte Aktion
	 * @param bestAction Die am besten bewertete Aktion für den Zustand
	 */
	void onActionChoosen(int state, int choosenAction, int bestAction);
	
	/**
	 * Wird dann vom Agenten aufgerufen, wenn er für eine Aktion in einem Zustand belohnt wird.
	 * @param state Der Zustand für den die Belohnung gelten soll
	 * @param action Die Aktion für die die Belohnung gelten soll
	 * @param reward Die Belohnung, negative Werte entsprechen einer Bestrafung
	 * @param subsequentState Der Nachfolgezustand (der Zustand nach {@code state})
	 * @param oldQValue Der alte Q-Wert für das Zustand/Aktion Paar
	 * @param newQValue Der neue Q-Wert für das Zustand/Aktion Paar
	 */
	void onReward(int state, int action, double reward, int subsequentState, double oldQValue, double newQValue);
	
	/**
	 * Wird dann vom Agenten aufgerufen, wenn sich seine Policy geändert hat.
	 * @param policy Die neue Policy
	 */
	void onPolicyChanged(Policy_I policy);
	
	/**
	 * Wird dann vom Agenten aufgerufen, wenn sich seine Lernrate geändert hat.
	 * @param learningRate Die neue Lernrate
	 */
	void onLearningRateChanged(double learningRate);
	
	/**
	 * Wird dann vom Agenten aufgerufen, wenn sich sein Discount-Faktor geändert hat.
	 * @param discountFactor Der neue Discount-Faktor
	 */
	void onDiscountFactorChanged(double discountFactor);
}
