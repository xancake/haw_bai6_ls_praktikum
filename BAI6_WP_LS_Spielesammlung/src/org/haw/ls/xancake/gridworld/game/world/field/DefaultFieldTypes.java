package org.haw.ls.xancake.gridworld.game.world.field;

import org.haw.ls.xancake.gridworld.game.player.Player;
import org.haw.ls.xancake.gridworld.game.world.GridWorldField;

public enum DefaultFieldTypes implements FieldType {
	EMPTY(' ', true),
	WALL('#', false),
	FINISH('+', true);
	
	private char _symbol;
	private boolean _canEnter;
	
	private DefaultFieldTypes(char symbol, boolean canEnter) {
		_symbol = symbol;
		_canEnter = canEnter;
	}
	
	@Override
	public boolean canEnter(GridWorldField field, Player player) {
		return _canEnter;
	}
	
	@Override
	public void onEnter(GridWorldField field, Player player) {
		player.moveTo(field);
	}
	
	@Override
	public String toString() {
		return String.valueOf(_symbol);
	}
}
