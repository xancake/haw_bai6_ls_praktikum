package de.xancake.learning.qlearning.agent;

import java.util.List;
import java.util.Objects;
import de.xancake.learning.qlearning.agent.policy.Policy_I;
import de.xancake.learning.qlearning.agent.qvalues.QValueArray;
import de.xancake.learning.qlearning.agent.qvalues.QValues_I;

/**
 * Eine allgemeine Implementation für einen Q-Learning-Agenten. Der Agent bietet Schnittstellen
 * zum Belohnen (bzw. Bestrafen) und dem Auswählen einer Aktion.
 */
public class QLearningAgent implements QLearningAgent_I {
	public static final double DEFAULT_START_QVALUE = 0.0;
	public static final double DEFAULT_LEARNING_RATE = 0.2;
	public static final double DEFAULT_DISCOUNT_FACTOR = 0.9;
	
	private Policy_I _policy;
	private QValues_I _qValues;
	private double _learningRate;
	private double _discountFactor;
	
	/**
	 * Instanziiert einen neuen QLearning Agenten.
	 * @param policy Die Policy, nach der der Agent Aktionen auswählt
	 * @param stateCount Die Anzahl der möglichen, unterschiedlichen Zustände
	 * @param actionCount Die Anzahl der möglichen, unterschiedlichen Aktionen
	 */
	public QLearningAgent(Policy_I policy, int stateCount, int actionCount) {
		this(policy, stateCount, actionCount, DEFAULT_LEARNING_RATE, DEFAULT_DISCOUNT_FACTOR, DEFAULT_START_QVALUE);
	}
	
	/**
	 * Instanziiert einen neuen QLearning Agenten.
	 * @param policy Die Policy, nach der der Agent Aktionen auswählt
	 * @param stateCount Die Anzahl der möglichen, unterschiedlichen Zustände
	 * @param actionCount Die Anzahl der möglichen, unterschiedlichen Aktionen
	 * @param learningRate Die Lernrate
	 * @param discountFactor Der Discount-Faktor
	 */
	public QLearningAgent(Policy_I policy, int stateCount, int actionCount, double learningRate, double discountFactor) {
		this(policy, stateCount, actionCount, learningRate, discountFactor, DEFAULT_START_QVALUE);
	}
	
	/**
	 * Instanziiert einen neuen QLearning Agenten.
	 * @param policy Die Policy, nach der der Agent Aktionen auswählt
	 * @param stateCount Die Anzahl der möglichen, unterschiedlichen Zustände
	 * @param actionCount Die Anzahl der möglichen, unterschiedlichen Aktionen
	 * @param learningRate Die Lernrate
	 * @param discountFactor Der Discount-Faktor
	 * @param startQValue Der Startwert für alle Einträge der internen {@link QValueArray}
	 */
	public QLearningAgent(Policy_I policy, int stateCount, int actionCount, double learningRate, double discountFactor, double startQValue) {
		_policy = Objects.requireNonNull(policy);
		_qValues = new QValueArray(stateCount, actionCount, startQValue);
		_learningRate = learningRate;
		_discountFactor = discountFactor;
	}
	
	@Override
	public int chooseAction(int state, List<Integer> possibleActions) {
		return _policy.chooseAction(state, possibleActions, _qValues);
	}
	
	@Override
	public void reward(int state, int action, double reward, int subsequentState) {
		double oldQValue = _qValues.getQValue(state, action);
		double bestQValue = _qValues.getBestQValueOfState(subsequentState);
		double newQValue = oldQValue + _learningRate * (reward + _discountFactor * bestQValue - oldQValue);
		_qValues.setQValue(state, action, newQValue);
	}
	
	@Override
	public Policy_I getPolicy() {
		return _policy;
	}
	
	@Override
	public QValues_I getQValues() {
		return _qValues;
	}
	
	@Override
	public double getLearningRate() {
		return _learningRate;
	}
	
	@Override
	public double getDiscountFactor() {
		return _discountFactor;
	}
	
	@Override
	public void setPolicy(Policy_I policy) {
		_policy = Objects.requireNonNull(policy);
	}
	
	@Override
	public void setLearningRate(double learningRate) {
		_learningRate = learningRate;
	}
	
	@Override
	public void setDiscountFactor(double discountFactor) {
		_discountFactor = discountFactor;
	}
}
