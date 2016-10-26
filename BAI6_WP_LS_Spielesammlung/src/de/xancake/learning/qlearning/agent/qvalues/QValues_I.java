package de.xancake.learning.qlearning.agent.qvalues;

/**
 * Repräsentiert die Q-Werte, anhand derer ein QLearningAgent lernt.
 */
public interface QValues_I {
	/**
	 * Gibt die Anzahl der Zustände zurück, die diese QValueTable verwaltet.
	 * @return Die Anzahl der Zustände
	 */
	int getStateCount();
	
	/**
	 * Gibt die Anzahl der Aktionen zurück, die diese QValueTable verwaltet.
	 * @return Die Anzahl der Aktionen
	 */
	int getActionCount();
	
	/**
	 * Gibt den Q-Wert für die Kombination aus dem übergebenen Zustand und der
	 * Aktion zurück.
	 * @param state Der Zustand
	 * @param action Die Aktion
	 * @return Der Q-Wert für den Zustand und die Aktion
	 */
	double getQValue(int state, int action);
	
	/**
	 * Setzt den Q-Wert für die Kombination aus dem übergebenen Zustand und der
	 * Aktion.
	 * @param state Der Zustand
	 * @param action Die Aktion
	 * @param newQValue Der zu setzende Q-Wert
	 */
	void setQValue(int state, int action, double newQValue);
	
	/**
	 * Gibt den optimalen Q-Wert für den übergebenen Zustand zurück.
	 */
	double getBestQValueOfState(int state);
	
	/**
	 * Gibt die Aktion mit den besten Q-Werten in dem übergebenen Zustand zurück.
	 * @param state Der Zustand
	 * @return Die bestebewertetste Aktion in dem Zustand
	 */
	int getBestActionOfState(int state);
}
