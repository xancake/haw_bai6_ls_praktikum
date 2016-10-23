package org.haw.ls.xancake.gridworld.game.world;

import java.util.Objects;
import org.haw.ls.xancake.gridworld.game.player.GridWorldPlayer;
import org.haw.ls.xancake.gridworld.game.world.field.DefaultFieldType;
import org.haw.ls.xancake.gridworld.game.world.field.GridWorldFieldType;
import org.haw.ls.xancake.gridworld.util.Numbers;

public class GridWorldField {
	private int _x;
	private int _y;
	private GridWorldFieldType _type;
	
	public GridWorldField(int x, int y) {
		this(x, y, DefaultFieldType.EMPTY);
	}
	
	public GridWorldField(int x, int y, GridWorldFieldType type) {
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
		return _type == DefaultFieldType.FINISH;
	}
	
	public GridWorldFieldType getType() {
		return _type;
	}
	
	public boolean canEnter(GridWorldPlayer player) {
		return _type.canEnter(this, player);
	}
	
	public void enter(GridWorldPlayer player) {
		_type.onEnter(this, player);
	}
	
	void setFieldType(GridWorldFieldType type) {
		_type = Objects.requireNonNull(type);
	}
	
	@Override
	public String toString() {
		return _type.toString();
	}
}
