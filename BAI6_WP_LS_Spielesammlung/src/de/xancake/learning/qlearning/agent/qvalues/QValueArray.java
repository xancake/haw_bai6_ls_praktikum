package de.xancake.learning.qlearning.agent.qvalues;

public class QValueArray implements QValues_I {
	private double[][] _qValues;
	
	/**
	 * Erzeugt ein zweidimensionales Array das die Q-Werte für alle Zustand/Aktion Paare repräsentiert.
	 * Alle Q-Werte werden mit {@code 0.0} vorbelegt.
	 * @param stateCount Die Anzahl der repräsentierten Zustände
	 * @param actionCount Die Anzahl der repräsentierten Aktionen
	 */
	public QValueArray(int stateCount, int actionCount) {
		_qValues = new double[stateCount][actionCount];
	}
	
	/**
	 * Erzeugt ein zweidimensionales Array das die Q-Werte für alle Zustand/Aktion Paare repräsentiert.
	 * Alle Q-Werte werden mit dem übergebenen Startwert vorbelegt.
	 * @param stateCount Die Anzahl der repräsentierten Zustände
	 * @param actionCount Die Anzahl der repräsentierten Aktionen
	 * @param startQValue Der Startwert aller Q-Werte
	 */
	public QValueArray(int stateCount, int actionCount, double startQValue) {
		_qValues = new double[stateCount][actionCount];
		for(int x=0; x<stateCount; x++) {
			for(int y=0; y<actionCount; y++) {
				_qValues[x][y] = startQValue;
			}
		}
	}
	
	@Override
	public int getStateCount() {
		return _qValues.length;
	}
	
	@Override
	public int getActionCount() {
		return _qValues[0].length;
	}
	
	@Override
	public double getQValue(int state, int action) {
		return _qValues[state][action];
	}
	
	@Override
	public void setQValue(int state, int action, double newQValue) {
		_qValues[state][action] = newQValue;
	}
	
	@Override
	public double getBestQValueOfState(int state) {
		double maxQ = -Double.MAX_VALUE;
		for(double d : _qValues[state]) {
			if(d > maxQ) {
				maxQ = d;
			}
		}
		return maxQ;
	}
	
	@Override
	public int getBestActionOfState(int state) {
		double maxQ = -Double.MAX_VALUE;
		int action = -1;
		for(int a=0; a<_qValues[state].length; a++) {
			if(_qValues[state][a] > maxQ) {
				maxQ = _qValues[state][a];
				action = a;
			}
		}
		return action;
	}
}
