package org.haw.ls.xancake.gridworld.game.world;

import java.util.List;
import org.haw.ls.xancake.gridworld.game.action.GridWorldAction;
import org.haw.ls.xancake.gridworld.game.field.DefaultFieldTypes;
import org.haw.ls.xancake.gridworld.game.field.FieldType;
import org.haw.ls.xancake.gridworld.game.player.Player;
import org.haw.ls.xancake.gridworld.util.Numbers;

public class GridWorldImpl implements MutableGridWorld {
	private int _width;
	private int _height;
	private GridWorldField _startField;
	private GridWorldField[][] _fields;
	
	public GridWorldImpl(int width, int height) {
		_width = Numbers.require(width).greaterThan(0, "Das Spielfeld muss breiter als 0 sein!");
		_height = Numbers.require(height).greaterThan(0, "Das Spielfeld muss höher als 0 sein!");
		_fields = new GridWorldField[_width][_height];
		for(int x=0; x<_width; x++) {
			for(int y=0; y<_height; y++) {
				_fields[x][y] = new GridWorldField(x, y, DefaultFieldTypes.EMPTY);
			}
		}
	}
	
	@Override
	public int getWidth() {
		return _width;
	}
	
	@Override
	public int getHeight() {
		return _height;
	}
	
	@Override
	public boolean isValidLocation(int x, int y) {
		return x>=0 && x<getWidth() && y>=0 && y<getHeight();
	}
	
	@Override
	public GridWorldField getField(int x, int y) {
		return _fields[x][y];
	}
	
	@Override
	public GridWorldField getStartField() {
		return _startField;
	}
	
	@Override
	public List<GridWorldAction> getAllowedActions(Player player) {
		// TODO: man könnte auch alle Aktionen zulassen. Dann müsste der Agent selbst lernen, dass die nichts bringen.
		return GridWorldAction.getAllowedActions(this, player);
	}
	
	@Override
	public void setFieldType(int x, int y, FieldType type) {
		_fields[x][y].setFieldType(type);
	}
	
	@Override
	public void setStartField(int x, int y) {
		_startField = _fields[x][y];
	}
	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		// X und Y vertauschen, damit für die Ausgabe die X-Achse horizontal und die Y-Achse vertikal ausgegeben wird
		// Y von hinten durchgehen, damit der Ursprung unten liegt
		for(int y=_height-1; y>=0; y--) {
			for(int x=0; x<_width; x++) {
				builder.append("[").append(_fields[x][y]).append("]");
			}
			builder.append("\n");
		}
		return builder.toString();
	}
}
