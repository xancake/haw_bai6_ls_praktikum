package de.xancake.learning.qlearning.agent.qvalues;

/**
 * Ein Listener, um an {@link ListenableQValues_I}s zu lauschen.
 */
public interface QValuesListener_I {
	/**
	 * Wird aufgerufen, wenn sich ein Wert im {@link QValueArray} verändert hat.
	 * @param state Der Zustand zu dem der Wert gehört, der sich geändert hat
	 * @param action Die Aktion zu der der Wert gehört, der sich geändert hat
	 * @param value Der Wert der sich geändert hat
	 */
	void onQValueChanged(int state, int action, double value);
	
	/**
	 * Wird aufgerufen, wenn ein Zustand besucht wird, der vorher noch nie besucht wurde.
	 * @param state Der Zustand, der zum ersten Mal besucht wurde
	 * @param reachedStates Wieviele Zustände bisher erreicht wurden
	 * @param maxStates Die Anzahl der Gesamtzustände
	 */
	void onNewStateReached(int state, int reachedStates, int maxStates);
}
