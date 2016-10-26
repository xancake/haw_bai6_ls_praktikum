package de.xancake.learning.qlearning.agent;

import java.util.List;
import de.xancake.learning.qlearning.agent.policy.Policy_I;
import de.xancake.learning.qlearning.agent.qvalues.QValues_I;

/**
 * Eine Schnittstelle für einen QLearning-Agenten. Der Agent bietet Schnittstellen
 * zum Belohnen (bzw. Bestrafen) und dem Auswählen einer Aktion.
 */
public interface QLearningAgent_I {
	/**
	 * Wählt anhand der hinterlegten {@link Policy_I} eine Aktion aus den übergebenen möglichen Aktionen aus.
	 * @param possibleActions Eine Liste der möglichen Aktionen
	 * @return Die ausgewählte Aktion
	 */
	int chooseAction(int state, List<Integer> possibleActions);
	
	/**
	 * Erhält eine Belohnung (oder Bestrafung bei negativen Werten) für die übergebene Aktion
	 * die in dem übergebenen Zustand getätigt wurde.
	 * @param state Der Zustand für den die Belohnung gelten soll
	 * @param action Die Aktion für die die Belohnung gelten soll
	 * @param reward Die Belohnung, negative Werte entsprechen einer Bestrafung
	 * @param subsequentState Der Nachfolgezustand (der Zustand nach {@code state})
	 */
	void reward(int state, int action, double reward, int subsequentState);
	
	/**
	 * Gibt die aktuelle Policy anhand derer der Agent Entscheidungen trifft zurück.
	 * @return Die aktuelle Policy
	 */
	Policy_I getPolicy();
	
	/**
	 * Gibt die {@link QValues_I} zurück, die der Agent nutzt.
	 * @return Die {@link QValues_I}
	 */
	QValues_I getQValues();
	
	/**
	 * Gibt die aktuelle Lernrate zurück. Die Lernrate legt fest, zu welchem Grad neue
	 * Q-Werte alte überschreiben
	 * @return Die aktuelle Lernrate
	 */
	double getLearningRate();
	
	/**
	 * Gibt den aktuellen Discount-Faktor zurück.
	 * Der Discount-Faktor bestimmt die Wichtigkeit zukünftiger Belohnungen / Bestrafungen.
	 * @return Der aktuelle Discount-Faktor
	 */
	double getDiscountFactor();
	
	/**
	 * Stellt die Policy anhand derer der Agent Entscheidungen trifft auf die übergebene um.
	 * @param policy Die neue Policy
	 */
	void setPolicy(Policy_I policy);
	
	/**
	 * Setzt die Lernrate auf den übergebenen Wert.
	 * <p>Die Lernrate legt fest, zu welchem Grad neue Q-Werte alte überschreiben. Q-Werte berechnen sich vereinfacht
	 * gemäß der Formel {@code qValueAlt + lernrate * (reward + qValueAlt)}. Bei einer Lernrate von {@code 0} bedeutet
	 * das, dass der Agent gemachte Erfahrungen nicht berücksichtigt, wohingegen ein Wert von {@code 1} bedeutet, dass
	 * der Agent ausschließlich die letzte Erfahrung berücksichtigt.
	 * @param learningRate Der neue Wert für die Lernrate
	 */
	void setLearningRate(double learningRate);
	
	/**
	 * Setzt den Discount-Faktor auf den übergebenen Wert.
	 * Der Discount-Faktor bestimmt die Wichtigkeit zukünftiger Belohnungen / Bestrafungen.
	 * @param discountFactor Der neue Wert für den Discount-Faktor
	 */
	void setDiscountFactor(double discountFactor);
}
