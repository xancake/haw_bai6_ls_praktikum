package org.haw.ls.xancake.gridworld.game.world;

import java.util.Objects;
import org.haw.ls.xancake.gridworld.game.field.DefaultFieldTypes;
import org.haw.ls.xancake.gridworld.game.field.FieldType;
import org.haw.ls.xancake.gridworld.game.player.Player;
import org.haw.ls.xancake.gridworld.util.Numbers;

public class GridWorldField {
	private int _x;
	private int _y;
	private FieldType _type;
	
	public GridWorldField(int x, int y) {
		this(x, y, DefaultFieldTypes.EMPTY);
	}
	
	public GridWorldField(int x, int y, FieldType type) {
		_x = Numbers.require(x).greaterThanOrEqual(0, "X darf nicht kleiner als 0 sein!");
		_y = Numbers.require(y).greaterThanOrEqual(0, "Y darf nicht kleiner als 0 sein!");
		_type = Objects.requireNonNull(type);
	}
	
	public int getX() {
		return _x;
	}
	
	public int getY() {
		return _y;
	}
	
	public boolean isFinish() {
		return _type == DefaultFieldTypes.FINISH;
	}
	
	public boolean canEnter(Player player) {
		return _type.canEnter(this, player);
	}
	
	public void enter(Player player) {
		_type.onEnter(this, player);
	}
	
	public FieldType getType() {
		return _type;
	}
	
	void setFieldType(FieldType type) {
		_type = Objects.requireNonNull(type);
	}
	
	@Override
	public String toString() {
		return _type.toString();
	}
}
