package org.haw.ls.xancake.gridworld.game.world;

import java.util.List;
import org.haw.ls.xancake.gridworld.game.action.GridWorldAction;
import org.haw.ls.xancake.gridworld.game.player.GridWorldPlayer;
import org.haw.ls.xancake.gridworld.game.world.field.DefaultFieldType;
import org.haw.ls.xancake.gridworld.game.world.field.GridWorldFieldType;
import org.haw.ls.xancake.gridworld.util.Numbers;
import de.xancake.pattern.listener.EventDispatcher;

public class GridWorldImpl implements PlayableGridWorld, MutableGridWorld {
	private EventDispatcher<GridWorldListener> _dispatcher;
	private int _width;
	private int _height;
	private GridWorldField _startField;
	private GridWorldField[][] _fields;
	private GridWorldPlayer _player;
	
	public GridWorldImpl(int width, int height) {
		_dispatcher = new EventDispatcher<>();
		_width = Numbers.require(width).greaterThan(0, "Das Spielfeld muss breiter als 0 sein!");
		_height = Numbers.require(height).greaterThan(0, "Das Spielfeld muss höher als 0 sein!");
		_fields = new GridWorldField[_width][_height];
		for(int x=0; x<_width; x++) {
			for(int y=0; y<_height; y++) {
				_fields[x][y] = new GridWorldField(x, y, DefaultFieldType.EMPTY);
			}
		}
		_player = new GridWorldPlayer();
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
	public GridWorldPlayer getPlayer() {
		return _player;
	}
	
	@Override
	public boolean isPlayerOnFinish() {
		return getField(_player.getX(), _player.getY()).isFinish();
	}
	
	@Override
	public List<GridWorldAction> getAllowedActions() {
		// TODO: man könnte auch alle Aktionen zulassen. Dann müsste der Agent selbst lernen, dass die nichts bringen.
//		return GridWorldAction.getAllowedActions(this);
		return GridWorldAction.getAllActions();
	}
	
	@Override
	public void doPlayerMove(GridWorldAction action) {
		if(action.isAllowed(this)) {
			action.execute(this);
			_dispatcher.fireEvent(l -> l.onPlayerMoved(this, _player));
		}
	}
	
	@Override
	public void reset() {
		_player.moveTo(_startField);
		_dispatcher.fireEvent(l -> l.onPlayerMoved(this, _player));
	}
	
	@Override
	public void addListener(GridWorldListener listener) {
		_dispatcher.addListener(listener);
	}

	@Override
	public void removeListener(GridWorldListener listener) {
		_dispatcher.removeListener(listener);
	}
	
	@Override
	public void setFieldType(int x, int y, GridWorldFieldType type) {
		_fields[x][y].setFieldType(type);
	}
	
	@Override
	public void setStartField(int x, int y) {
		_startField = _fields[x][y];
		reset();
	}
	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		int playerX = _player!=null ? _player.getX() : -1;
		int playerY = _player!=null ? _player.getY() : -1;
		
		// X und Y vertauschen, damit für die Ausgabe die X-Achse horizontal und die Y-Achse vertikal ausgegeben wird
		for(int y=0; y<_height; y++) {
			for(int x=0; x<_width; x++) {
				builder.append("[")
						.append((x==playerX && y==playerY) ? "P" : _fields[x][y])
						.append("]");
			}
			builder.append("\n");
		}
		return builder.toString();
	}
}
