package de.xancake.learning.qlearning.agent;

import java.util.List;
import java.util.Objects;
import de.xancake.learning.qlearning.agent.policy.Policy_I;
import de.xancake.learning.qlearning.agent.qvalues.QValueArray;
import de.xancake.learning.qlearning.agent.qvalues.QValues_I;
import de.xancake.pattern.listener.EventDispatcher;

/**
 * Ermöglicht das Lauschen an einem {@link QLearningAgent_I} um Statusänderungen mitzubekommen.
 */
public class ListenableQLearningAgent implements QLearningAgent_I {
	private EventDispatcher<QLearningAgentListener_I> _eventDispatcher;
	
	private QLearningAgent_I _delegate;
	
	/**
	 * Instanziiert einen neuen QLearning Agenten.
	 * @param policy Die Policy, nach der der Agent Aktionen auswählt
	 * @param stateCount Die Anzahl der möglichen, unterschiedlichen Zustände
	 * @param actionCount Die Anzahl der möglichen, unterschiedlichen Aktionen
	 */
	public ListenableQLearningAgent(Policy_I policy, int stateCount, int actionCount) {
		this(new QLearningAgent(policy, stateCount, actionCount));
	}
	
	/**
	 * Instanziiert einen neuen QLearning Agenten.
	 * @param policy Die Policy, nach der der Agent Aktionen auswählt
	 * @param stateCount Die Anzahl der möglichen, unterschiedlichen Zustände
	 * @param actionCount Die Anzahl der möglichen, unterschiedlichen Aktionen
	 * @param learningRate Die Lernrate
	 * @param discountFactor Der Discount-Faktor
	 */
	public ListenableQLearningAgent(Policy_I policy, int stateCount, int actionCount, double learningRate, double discountFactor) {
		this(new QLearningAgent(policy, stateCount, actionCount, learningRate, discountFactor));
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
	public ListenableQLearningAgent(Policy_I policy, int stateCount, int actionCount, double learningRate, double discountFactor, double startQValue) {
		this(new QLearningAgent(policy, stateCount, actionCount, learningRate, discountFactor, startQValue));
	}
	
	/**
	 * Erweitert den übergebenen Agenten um die Möglichkeit belauscht zu werden. Intern delegiert diese Klasse an das
	 * übergebene Objekt und benachrichtigt die Listener über bestimmte Ereignisse.
	 * @param delegate Der zu belauschende QLearning Agent
	 * @see QLearningAgentListener_I
	 */
	public ListenableQLearningAgent(QLearningAgent_I delegate) {
		_eventDispatcher = new EventDispatcher<>();
		_delegate = Objects.requireNonNull(delegate);
	}
	
	@Override
	public int chooseAction(int state, List<Integer> possibleActions) {
		int choosenAction = _delegate.chooseAction(state, possibleActions);
		int bestAction = getQValues().getBestActionOfState(state);
		_eventDispatcher.fireEvent(l -> l.onActionChoosen(state, choosenAction, bestAction));
		return choosenAction;
	}
	
	@Override
	public void reward(int state, int action, double reward, int subsequentState) {
		double oldQValue = getQValues().getQValue(state, action);
		_delegate.reward(state, action, reward, subsequentState);
		double newQValue = getQValues().getQValue(state, action);
		_eventDispatcher.fireEvent(l -> l.onReward(state, action, reward, subsequentState, oldQValue, newQValue));
	}
	
	@Override
	public Policy_I getPolicy() {
		return _delegate.getPolicy();
	}
	
	@Override
	public QValues_I getQValues() {
		return _delegate.getQValues();
	}
	
	@Override
	public double getLearningRate() {
		return _delegate.getLearningRate();
	}
	
	@Override
	public double getDiscountFactor() {
		return _delegate.getDiscountFactor();
	}
	
	@Override
	public void setPolicy(Policy_I policy) {
		_delegate.setPolicy(policy);
		_eventDispatcher.fireEvent(l -> l.onPolicyChanged(this.getPolicy()));
	}
	
	@Override
	public void setLearningRate(double learningRate) {
		_delegate.setLearningRate(learningRate);
		_eventDispatcher.fireEvent(l -> l.onLearningRateChanged(this.getLearningRate()));
	}
	
	@Override
	public void setDiscountFactor(double discountFactor) {
		_delegate.setDiscountFactor(discountFactor);
		_eventDispatcher.fireEvent(l -> l.onDiscountFactorChanged(this.getDiscountFactor()));
	}
	
	/**
	 * Registriert den übergebenen Listener an diesem Agenten.
	 * @param listener Der hinzuzufügende Listener
	 */
	public void addListener(QLearningAgentListener_I listener) {
		_eventDispatcher.addListener(listener);
	}
	
	/**
	 * Entfernt den übergebenen Listener von diesem Agenten.
	 * @param listener Der zu entfernende Listener
	 */
	public void removeListener(QLearningAgentListener_I listener) {
		_eventDispatcher.removeListener(listener);
	}
}
