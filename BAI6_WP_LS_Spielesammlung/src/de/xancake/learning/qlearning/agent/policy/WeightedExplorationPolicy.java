package de.xancake.learning.qlearning.agent.policy;

import java.util.List;
import java.util.Map.Entry;
import java.util.Random;
import java.util.SortedMap;
import java.util.TreeMap;
import de.xancake.learning.qlearning.agent.qvalues.QValues_I;

/**
 * Eine Entscheidungs-Policy, die zum Explorieren eingesetzt werden kann. Die Aktionen werden dabei entsprechend ihrer
 * Q-Werte gewichtet und basierend auf der Gewichtung wird eine Aktion zufällig gewählt.
 * 
 * <p>Diese Policy bietet sich für das Weiterlernen (Verbessern) an, der Agent "festigt" die ihm bekannten Wege,
 * exploriert aber gelegentlich auch abseits davon.
 */
public class WeightedExplorationPolicy implements Policy_I {
	private final Random _random;
	
	public WeightedExplorationPolicy() {
		_random = new Random();
	}
	
	@Override
	public int chooseAction(int state, List<Integer> possibleActions, QValues_I qValues) {
		SortedMap<Integer, Double> weights = getWeightedActions(state, possibleActions, qValues);
		
		double pick = _random.nextDouble();
		double lastWeight = 0;
		int chosenAction = -1;
		for(Entry<Integer, Double> e : weights.entrySet()) {
			double weight = e.getValue();
			if(pick>=lastWeight && pick<weight) {
				chosenAction = e.getKey();
				break;
			}
			lastWeight = weight;
		}
		return chosenAction;
	}
	
	/**
	 * Berechnet die Gewichtung aller möglichen Aktionen für den übergebenen Zustand und liefert sie als Map zurück.
	 * <p>Die entstehende Gewichtung ist ein prozentualer Wert (zwischen 0 und 1), wobei jeder Eintrag die Gewichtung
	 * des Vorgängers zusätzlich enthält. Dadurch entsteht ein Raum von der Gewichtung des Vorgängers bis zu der
	 * Gewichtung der aktuellen Aktion, in dem die Aktion gelten soll.
	 * 
	 * <h3>Beispiel</h3>
	 * <p>Die Q-Werte der Aktionen aus {@code state} werden ermittelt:
	 * <pre>[2|3,5|-1|6]</pre>
	 * 
	 * <p>Die Größe des Ereignisraums wird ermittelt, indem die Q-Werte aufsummiert werden:
	 * <pre>12,5 (= sum(qValue>=0 ? x : abs(qValue)*2))</pre>
	 * 
	 * <p>Die gewichteten Aktionen werden auf den Ereignisraum aufgeteilt:
	 * <pre>
	 * |   2   |     3,5     |-1 |           6           |
	 * +---+---+---+---+---+---+---+---+---+---+---+---+---+
	 * 0   1   2   3   4   5   6   7   8   9  10  11  12  13
	 * </pre>
	 * 
	 * Die resultierende Map:
	 * <pre>
	 * 1 ->  2,0/12,5
	 * 2 ->  5,5/12,5
	 * 3 ->  6,5/12,5
	 * 4 -> 12,5/12,5
	 * </pre>
	 * 
	 * @param state Der Zustand anhand dessen Q-Werte die Aktionen gewichtet werden sollen
	 * @param possibleActions Die Liste der zu gewichtenden Aktionen
	 * @param qValues Die {@link QValues_I Q-Werte}
	 * @return Ein Mapping der Aktionen zu ihrer Gewichtung
	 */
	private SortedMap<Integer, Double> getWeightedActions(int state, List<Integer> possibleActions, QValues_I qValues) {
		double ereignisraum = 0;
		for(Integer action : possibleActions) {
			double qValue = qValues.getQValue(state, action);
			ereignisraum += (qValue>=0 ? qValue : Math.abs(qValue)*2);
		}
		
		SortedMap<Integer, Double> weightedActions = new TreeMap<>();
		double weight = 0;
		for(Integer action : possibleActions) {
			double qValue = qValues.getQValue(state, action);
			weight += Math.abs(qValue);
			weightedActions.put(action, weight/ereignisraum);
		}
		return weightedActions;
	}
}
