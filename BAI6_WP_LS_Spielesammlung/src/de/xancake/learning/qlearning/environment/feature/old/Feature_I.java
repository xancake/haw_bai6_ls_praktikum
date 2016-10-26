package de.xancake.learning.qlearning.environment.feature.old;

import java.util.List;

/**
 * Repräsentiert ein Feature für einen QLearningAgent.
 * 
 * <p>Ein Feature definiert einen Zustandsbereich in dem sich der aktuelle Zustand befinden kann.
 */
public interface Feature_I {
	/**
	 * Gibt den aktuellen Zustand des Features als numerischen Wert zurück.
	 * @return Der aktuelle Zustand des Features als numerischer Wert
	 */
	int getCurrentState();
	
	/**
	 * Gibt zurück, wieviele unterschiedliche Zustände durch dieses Feature dargestellt werden können.
	 * @return Die Anzahl der möglichen, unterschiedlichen Zustände
	 */
	int getPossibleAllocations();
	
	/**
	 * Gibt den aktuellen Zustand des Features zurück.
	 * @return Der aktuelle Zustand des Features
	 */
	FeatureState_I getCurrentFeatureState();
	
	/**
	 * Gibt zurück, alle möglichen Zustände dieses Feature zurück.
	 * @return Eine unveränderliche Collection aller Zustände dieses Features
	 */
	List<? extends FeatureState_I> getFeatureStates();
	
	/**
	 * Gibt den Namen des Features zurück.
	 * @return Der Name des Features
	 */
	String getName();
	
	/**
	 * Aktualisiert den Zustand dieses Features.
	 */
	void updateState();
	
	/**
	 * Eine Schnittstelle die genau einen Zustand eines Features beschreibt.
	 */
	public interface FeatureState_I {
		/**
		 * Gibt die Nummer des Zustands im Feature zurück.
		 * @return Die Nummer des Zustands
		 */
		int getStateNumber();
		
		/**
		 * Gibt die Beschreibung des Zustands zurück.
		 * @return Die Beschreibung
		 */
		String getDescription();
	}
}
