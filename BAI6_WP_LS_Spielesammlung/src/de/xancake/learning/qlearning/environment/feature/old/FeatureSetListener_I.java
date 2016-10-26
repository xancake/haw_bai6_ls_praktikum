package de.xancake.learning.qlearning.environment.feature.old;

/**
 * Schnittstelle zum lauschen an {@link FeatureSet_I Feature-Sätzen}.
 */
public interface FeatureSetListener_I {
	/**
	 * Wird aufgerufen, wenn sich der Zustand eines Features ändert.
	 * @param feature Das Feature, das sich verändert hat
	 * @param oldState Der Zustand in dem sich das Feature vor dem Update befand
	 * @param newState Der Zustand in dem sich das Feature jetzt befindet
	 */
	void onFeatureStateChanged(Feature_I feature, int oldState, int newState);
}
