package org.haw.ls.xancake.gridworld.game;

import java.util.List;
import java.util.Objects;
import org.haw.ls.xancake.gridworld.game.action.GridWorldAction;
import org.haw.ls.xancake.gridworld.game.player.PlayerBehaviour;
import org.haw.ls.xancake.gridworld.game.world.GridWorld;
import org.haw.ls.xancake.gridworld.game.world.PlayableGridWorld;
import de.xancake.pattern.listener.EventDispatcher;

public class GridWorldGame {
	private EventDispatcher<GridWorldGameListener> _dispatcher;
	
	private PlayableGridWorld _world;
	private PlayerBehaviour _playerBehaviour;
	private boolean _run;
	private boolean _ended;
	
	public GridWorldGame(PlayableGridWorld world, PlayerBehaviour playerBehaviour) {
		_dispatcher = new EventDispatcher<>();
		_world = Objects.requireNonNull(world);
		_playerBehaviour = Objects.requireNonNull(playerBehaviour);
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
	
	public boolean hasEnded() {
		return _ended;
	}
	
	public void start() {
		_world.getPlayer().setBehaviour(_playerBehaviour);
		_run = true;
		gameLoop();
		_ended = true;
	}
	
	public void stop() {
		_run = false;
	}
	
	private void gameLoop() {
		_dispatcher.fireEvent(l -> l.onGameStarted());
		
		while(_run && !_world.isPlayerOnFinish()) {
			_dispatcher.fireEvent(l -> l.onRoundStarted());
			
			List<GridWorldAction> actions = _world.getAllowedActions();
			GridWorldAction choosenAction = _world.getPlayer().chooseAction(actions);
			_world.doPlayerMove(choosenAction);
			
			_dispatcher.fireEvent(l -> l.onRoundEnded());
		}
		
		_dispatcher.fireEvent(l -> l.onGameEnded());
	}
}
