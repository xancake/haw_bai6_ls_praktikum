package org.haw.ls.xancake.gridworld.game.action;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.haw.ls.xancake.gridworld.game.player.Player;
import org.haw.ls.xancake.gridworld.game.world.GridWorld;

public enum GridWorldAction {
	UP(0, 1),
	RIGHT(1, 0),
	DOWN(0, -1),
	LEFT(-1, 0);
	
	private int _dx;
	private int _dy;
	
	private GridWorldAction(int dx, int dy) {
		_dx = dx;
		_dy = dy;
	}
	
	public boolean isAllowed(GridWorld world, Player player) {
		int moveX = player.getX()+_dx;
		int moveY = player.getY()+_dy;
		return world.isValidLocation(moveX, moveY)
				&& world.getField(moveX, moveY).canEnter(player);
	}
	
	public void execute(GridWorld world, Player player) {
		if(isAllowed(world, player)) {
			player.setX(player.getX()+_dx);
			player.setY(player.getY()+_dy);
		}
	}
	
	public static List<GridWorldAction> getAllActions() {
		return Arrays.asList(UP, RIGHT, DOWN, LEFT);
	}
	
	public static List<GridWorldAction> getAllowedActions(GridWorld world, Player player) {
		List<GridWorldAction> actions = new ArrayList<>();
		addActionIfPossible(world, player, actions, UP);
		addActionIfPossible(world, player, actions, RIGHT);
		addActionIfPossible(world, player, actions, DOWN);
		addActionIfPossible(world, player, actions, LEFT);
		return actions;
	}
	
	private static void addActionIfPossible(GridWorld world, Player player, List<GridWorldAction> actions, GridWorldAction action) {
		if(action.isAllowed(world, player)) {
			actions.add(action);
		}
	}
}
