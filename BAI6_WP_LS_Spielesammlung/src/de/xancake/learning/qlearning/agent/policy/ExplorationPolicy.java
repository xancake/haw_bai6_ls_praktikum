package de.xancake.learning.qlearning.agent.policy;

import java.util.List;
import java.util.Objects;
import java.util.Random;
import de.xancake.learning.qlearning.agent.qvalues.QValues_I;

/**
 * Eine Entscheidungs-Policy, die grundsätzlich erstmal immer die beste Aktion wählt, aber steuerbar über die
 * {@link #setExplorationRate(double) Explorationsrate} auch andere Möglichkeiten erkundet. Bei der Exploration sind
 * die Aktionen nicht gewichtet, sodass jede zum gleichen Anteil ausgewählt werden kann.
 * 
 * <p>Diese Policy bietet sich für das Weiterlernen (Verbessern) an, der Agent "festigt" die ihm bekannten Wege,
 * exploriert aber gelegentlich auch abseits davon.
 */
public class ExplorationPolicy implements Policy_I {
	private static final double DEFAULT_EXPLORATION_RATE = 0.1;
	
	private final Random _random;
	private Policy_I _nonExplorePolicy;
	private Policy_I _explorationPolicy;
	private double _explorationRate;
	
	public ExplorationPolicy() {
		this(DEFAULT_EXPLORATION_RATE, QLearningAgentPolicies.RANDOM_POLICY);
	}
	
	public ExplorationPolicy(double explorationRate, Policy_I explorationPolicy) {
		_random = new Random();
		_nonExplorePolicy = QLearningAgentPolicies.STATIC_POLICY;
		setExplorationPolicy(explorationPolicy);
		setExplorationRate(explorationRate);
	}
	
	@Override
	public int chooseAction(int state, List<Integer> possibleActions, QValues_I qValues) {
		Policy_I policy = (_random.nextDouble() > _explorationRate) ? _nonExplorePolicy : _explorationPolicy;
		return policy.chooseAction(state, possibleActions, qValues);
	}
	
	public Policy_I getExplorationPolicy() {
		return _explorationPolicy;
	}
	
	public double getExplorationRate() {
		return _explorationRate;
	}
	
	public void setExplorationPolicy(Policy_I explorationPolicy) {
		_explorationPolicy = Objects.requireNonNull(explorationPolicy);
	}
	
	public void setExplorationRate(double explorationRate) {
		if(explorationRate < 0 || explorationRate > 1) {
			throw new IllegalArgumentException("The exploration-rate must be between 0 and 1!");
		}
		_explorationRate = explorationRate;
	}
}
