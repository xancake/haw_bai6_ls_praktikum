package org.haw.ls.xancake.gridworld.game;

import java.util.List;
import java.util.Objects;
import org.haw.ls.xancake.gridworld.game.action.GridWorldAction;
import org.haw.ls.xancake.gridworld.game.player.Player;
import org.haw.ls.xancake.gridworld.game.world.GridWorld;
import org.haw.ls.xancake.util.listener.EventDispatcher;

public class GridWorldGame {
	private EventDispatcher<GridWorldGameListener> _dispatcher;
	
	private GridWorld _world;
	private Player _player;
	
	public GridWorldGame(GridWorld world, Player player) {
		_dispatcher = new EventDispatcher<>();
		_world = Objects.requireNonNull(world);
		_player = Objects.requireNonNull(player);
	}
	
	public void addListener(GridWorldGameListener listener) {
		_dispatcher.addListener(listener);
	}
	
	public void removeListener(GridWorldGameListener listener) {
		_dispatcher.removeListener(listener);
	}
	
	public GridWorld getWorld() {
		return _world;
	}
	
	public Player getPlayer() {
		return _player;
	}
	
	public void start() {
		gameLoop();
	}
	
	private void gameLoop() {
		_dispatcher.fireEvent(l -> l.onGameStarted());
		
		_player.moveTo(_world.getStartField());
		while(!_world.getField(_player.getX(), _player.getY()).isFinish()) {
			_dispatcher.fireEvent(l -> l.onRoundStarted());
			
			
			List<GridWorldAction> actions = _world.getAllowedActions(_player);
			GridWorldAction choosenAction = _player.chooseAction(actions);
			if(choosenAction.isAllowed(_world, _player)) {
				choosenAction.execute(_world, _player);
			}
			
			_dispatcher.fireEvent(l -> l.onRoundEnded());
		}
		
		_dispatcher.fireEvent(l -> l.onGameEnded());
	}
}
