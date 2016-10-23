package org.haw.ls.xancake.gridworld.game.world.field;

import org.haw.ls.xancake.gridworld.game.player.GridWorldPlayer;
import org.haw.ls.xancake.gridworld.game.world.GridWorldField;

public enum DefaultFieldType implements GridWorldFieldType {
	EMPTY('_', true),
	WALL('#', false),
	FINISH('+', true),
	DEATH('-', true);
	
	private char _symbol;
	private boolean _canEnter;
	
	private DefaultFieldType(char symbol, boolean canEnter) {
		_symbol = symbol;
		_canEnter = canEnter;
	}
	
	@Override
	public boolean canEnter(GridWorldField field, GridWorldPlayer player) {
		return _canEnter;
	}
	
	@Override
	public void onEnter(GridWorldField field, GridWorldPlayer player) {
		if(canEnter(field, player)) {
			player.moveTo(field);
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
}
