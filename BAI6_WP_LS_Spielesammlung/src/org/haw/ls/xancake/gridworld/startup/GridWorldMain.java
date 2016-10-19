package org.haw.ls.xancake.gridworld.startup;

import java.util.List;
import java.util.Scanner;
import org.haw.ls.xancake.gridworld.game.GridWorldGame;
import org.haw.ls.xancake.gridworld.game.GridWorldGameListener;
import org.haw.ls.xancake.gridworld.game.action.GridWorldAction;
import org.haw.ls.xancake.gridworld.game.field.DefaultFieldTypes;
import org.haw.ls.xancake.gridworld.game.player.Player;
import org.haw.ls.xancake.gridworld.game.player.PlayerBehaviour;
import org.haw.ls.xancake.gridworld.game.world.GridWorld;
import org.haw.ls.xancake.gridworld.game.world.GridWorldImpl;
import org.haw.ls.xancake.gridworld.game.world.MutableGridWorld;

public class GridWorldMain {
	private static Scanner _keyboard;
	private static GridWorld _world;
	
	public static void main(String[] args) {
		_keyboard = new Scanner(System.in);
		_world = initWorld();
		Player player = new Player(new CLIBehaviour());
		GridWorldGame game = new GridWorldGame(_world, player);
		game.addListener(new CLIGameListener());
		game.start();
	}
	
	private static GridWorld initWorld() {
		MutableGridWorld world = new GridWorldImpl(5, 4);
		world.setStartField(0, 0);
		world.setFieldType(1, 1, DefaultFieldTypes.WALL);
		world.setFieldType(1, 2, DefaultFieldTypes.WALL);
		world.setFieldType(3, 1, DefaultFieldTypes.WALL);
		world.setFieldType(3, 3, DefaultFieldTypes.WALL);
		world.setFieldType(4, 0, DefaultFieldTypes.WALL);
		world.setFieldType(4, 3, DefaultFieldTypes.FINISH);
		return world;
	}
	
	private static class CLIBehaviour implements PlayerBehaviour {
		@Override
		public GridWorldAction chooseAction(List<GridWorldAction> availableActions) {
			System.out.println(_world.toString());
			GridWorldAction action;
			do {
				System.out.print("Aktion: ");
				String input = _keyboard.nextLine();
				action = GridWorldAction.valueOf(input.toUpperCase().trim());
			} while(!availableActions.contains(action));
			return action;
		}
	}
	
	private static class CLIGameListener implements GridWorldGameListener {
		@Override
		public void onGameStarted() {
			
		}

		@Override
		public void onGameEnded() {
			
		}

		@Override
		public void onRoundStarted() {
			
		}

		@Override
		public void onRoundEnded() {
			
		}
	}
}
