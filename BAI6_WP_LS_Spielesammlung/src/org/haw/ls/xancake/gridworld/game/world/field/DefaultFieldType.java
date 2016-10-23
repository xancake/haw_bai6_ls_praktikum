package org.haw.ls.xancake.gridworld.game.world.field;

import org.haw.ls.xancake.gridworld.game.player.GridWorldPlayer;
import org.haw.ls.xancake.gridworld.game.world.GridWorldField;

public enum DefaultFieldType implements GridWorldFieldType {
	EMPTY('_', true),
	WALL('#', false),
	FINISH('+', true),
	DEATH('-', true, (f, p) -> p.setDead(true));
	
	private char _symbol;
	private boolean _canEnter;
	private FieldBehaviour _behaviour;
	
	private DefaultFieldType(char symbol, boolean canEnter) {
		this(symbol, canEnter, null);
	}
	
	private DefaultFieldType(char symbol, boolean canEnter, FieldBehaviour behaviour) {
		_symbol = symbol;
		_canEnter = canEnter;
		_behaviour = behaviour;
	}
	
	@Override
	public boolean canEnter(GridWorldField field, GridWorldPlayer player) {
		return _canEnter;
	}
	
	@Override
	public void onEnter(GridWorldField field, GridWorldPlayer player) {
		if(_behaviour != null) {
			_behaviour.onEnter(field, player);
		}
	}
	
	@Override
	public String toString() {
		return String.valueOf(_symbol);
	}
	
	public static DefaultFieldType getForSymbol(char symbol) {
		for(DefaultFieldType t : values()) {
			if(symbol == t._symbol) {
				return t;
			}
		}
		return null;
	}
	
	private interface FieldBehaviour {
		void onEnter(GridWorldField field, GridWorldPlayer player);
	}
}
