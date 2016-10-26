package de.xancake.learning.qlearning.agent.policy;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Eine Sammlung aller {@link Policy_I Policies}, die ein Agent zum Treffen seiner Entscheidungen benutzen könnte.
 */
public final class QLearningAgentPolicies {
	public static final Policy_I STATIC_POLICY = new StaticPolicy();
	public static final Policy_I RANDOM_POLICY = new RandomPolicy();
	public static final Policy_I EXPLORATION_POLICY = new ExplorationPolicy();
	public static final Policy_I WEIGHTED_EXPLORATION_POLICY = new WeightedExplorationPolicy();
	
	/**
	 * Eine unveränderliche Liste aller mitgelieferten {@link Policy_I Policies}.
	 */
	public static final List<Policy_I> ALL_POLICIES = Collections.unmodifiableList(Arrays.asList(
			STATIC_POLICY,
			RANDOM_POLICY,
			EXPLORATION_POLICY,
			WEIGHTED_EXPLORATION_POLICY
	));
	
	private QLearningAgentPolicies() {}
}
