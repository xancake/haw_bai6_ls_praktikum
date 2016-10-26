package de.xancake.learning.qlearning.environment.feature.old;

import java.util.List;

/**
 * Repräsentiert einen Satz an {@link Feature_I Features}.
 */
public interface FeatureSet_I {
	/**
	 * Gibt den aktuellen Gesamtzustand, akkumuliert aus allen Features, zurück.
	 * @return Der aktuelle Gesamtzustand dieses Feature-Satzes
	 */
	int getCurrentState();
	
	/**
	 * Gibt zurück, wieviele unterschiedliche Zustände durch die Kombination aller
	 * Features in diesem Feature-Satz zustandekommen können.
	 * @return Die Anzahl der möglichen, unterschiedlichen Zustände
	 */
	int getFeatureStateRange();
	
	/**
	 * Gibt eine textuelle Beschreibung des übergebenen Zustands zurück.
	 * @param state Der Zustand
	 * @return Die textuelle Beschreibung des Zustands
	 */
	String getDescription(int state);
	
	/**
	 * Gibt eine unveränderliche Liste der Features dieses Feature-Satzes zurück.
	 * @return Eine unveränderliche Liste der Features dieses Feature-Satzes
	 */
	List<Feature_I> getFeatures();
	
	/**
	 * Aktualisiert den Zustand aller Features in diesem Feature-Satz.
	 */
	void updateFeatures();
	
	/**
	 * Registriert den übergebenen Listener an diesem Feature-Satz.
	 * @param listener Der zu registrierende Listener
	 */
	void addListener(FeatureSetListener_I listener);
	
	/**
	 * Entfernt den übergebenen Listener von diesem Feature-Satz.
	 * @param listener Der zu entfernende Listener
	 */
	void removeListener(FeatureSetListener_I listener);
}