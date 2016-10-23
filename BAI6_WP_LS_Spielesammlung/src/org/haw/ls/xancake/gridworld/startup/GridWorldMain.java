package org.haw.ls.xancake.gridworld.startup;

import java.util.List;
import java.util.Scanner;
import org.haw.ls.xancake.gridworld.game.GridWorldGame;
import org.haw.ls.xancake.gridworld.game.GridWorldGameListener;
import org.haw.ls.xancake.gridworld.game.action.GridWorldAction;
import org.haw.ls.xancake.gridworld.game.player.GridWorldPlayerBehaviour;
import org.haw.ls.xancake.gridworld.game.world.PlayableGridWorld;
import org.haw.ls.xancake.gridworld.game.world.io.GridWorldIO;

public class GridWorldMain {
	private static Scanner _keyboard;
	private static PlayableGridWorld _world;
	
	public static void main(String[] args) throws Exception {
		_keyboard = new Scanner(System.in);
		_world = new GridWorldIO().loadGridWorld("gridworld/7x6_maze.gridworld");
		GridWorldGame game = new GridWorldGame(_world, new CLIBehaviour());
		game.addListener(new CLIGameListener());
		game.start();
	}
	
	private static class CLIBehaviour implements GridWorldPlayerBehaviour {
		@Override
		public GridWorldAction chooseAction(List<GridWorldAction> availableActions) {
			System.out.println(_world);
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
			System.out.println(_world);
			System.out.println("Game Over!");
		}

		@Override
		public void onRoundStarted() {
			
		}

		@Override
		public void onRoundEnded() {
			
		}
	}
}
