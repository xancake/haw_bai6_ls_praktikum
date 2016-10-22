package org.haw.ls.xancake.gridworld.ui.swing;

import java.awt.Color;
import java.awt.Graphics2D;
import org.haw.ls.xancake.gridworld.game.action.GridWorldAction;
import org.haw.ls.xancake.gridworld.game.player.Player;
import org.haw.ls.xancake.gridworld.game.world.GridWorld;
import org.haw.ls.xancake.gridworld.game.world.GridWorldField;
import org.haw.ls.xancake.gridworld.game.world.PlayableGridWorld;
import org.haw.ls.xancake.gridworld.game.world.field.DefaultFieldTypes;
import org.haw.ls.xancake.gridworld.game.world.field.FieldType;

public class GridWorldPainter {
	
	public void drawWorld(Graphics2D g, GridWorld world, int gridSize) {
		if(world != null) {
			for(int x=0; x<world.getWidth(); x++) {
				for(int y=0; y<world.getHeight(); y++) {
					drawField(g, world, x, y, gridSize, getFieldColor(world, x, y));
				}
			}
			if(world instanceof PlayableGridWorld) {
				PlayableGridWorld pworld = (PlayableGridWorld)world;
				drawPlayer(g, pworld.getPlayer(), gridSize);
			}
		}
	}
	
	public void drawPossibleActions(Graphics2D g, GridWorld world, int gridSize) {
		if(world != null && world instanceof PlayableGridWorld) {
			PlayableGridWorld pworld = ((PlayableGridWorld)world);
			for(GridWorldAction action : pworld.getAllowedActions()) {
				GridWorldField field = action.getResultingField(pworld);
				if(field != null) {
					highlightField(g, world, field.getX(), field.getY(), gridSize, 3, Color.MAGENTA);
				}
			}
		}
	}
	
	public void drawField(Graphics2D g, GridWorld world, int x, int y, int gridSize, Color c) {
		g.setColor(c);
		g.fillRect(x*gridSize, y*gridSize, gridSize, gridSize);
		g.setColor(Color.BLACK);
		g.drawRect(x*gridSize, y*gridSize, gridSize, gridSize);
	}
	
	public void highlightField(Graphics2D g, GridWorld world, int x, int y, int gridSize, int thickness, Color c) {
		g.setColor(c);
		// bei 1 anfangen, damit der Rahmen des Feldes nicht übermalt wird
		for(int t=1; t<=thickness; t++) {
			g.drawRect(x*gridSize+t, y*gridSize+t, gridSize-t*2, gridSize-t*2);
			if(t+1==thickness) {
				g.setColor(Color.WHITE);
			}
		}
	}
	
	public void drawPlayerIfThere(Graphics2D g, GridWorld world, int x, int y, int gridSize) {
		if(world instanceof PlayableGridWorld) {
    		Player player = ((PlayableGridWorld)world).getPlayer();
    		if(x==player.getX() && y==player.getY()) {
    			drawPlayer(g, player, gridSize);
    		}
    	}
	}
	
	public void drawPlayer(Graphics2D g, Player player, int gridSize) {
		int s = (int)(gridSize*0.25);
		int x = player.getX()*gridSize + s/2;
		int y = player.getY()*gridSize + s/2;
		int w = gridSize - s;
		int h = gridSize - s;
		
		g.setColor(Color.YELLOW);
		g.fillOval(x, y, w, h);
		g.setColor(Color.BLACK);
		g.drawOval(x, y, w, h);
	}
	
	public Color getFieldColor(GridWorld world, int x, int y) {
		GridWorldField field = world.getField(x, y);
		if(world.getStartField() == field) {
			return Color.GREEN;
		}
		FieldType type = field.getType();
		if(type == DefaultFieldTypes.EMPTY) {
			return new Color(220, 220, 220);
		} else if(type == DefaultFieldTypes.WALL) {
			return Color.BLACK;
		} else if(type == DefaultFieldTypes.FINISH) {
			return Color.YELLOW;
		} else if(type == DefaultFieldTypes.DEATH) {
			return Color.RED;
		} else {
			throw new IllegalArgumentException("Der Feldtyp '" + type + "' wird nicht unterstützt!");
		}
	}
}
