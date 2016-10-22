package org.haw.ls.xancake.gridworld.ui.swing;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JPanel;
import org.haw.ls.xancake.gridworld.game.player.Player;
import org.haw.ls.xancake.gridworld.game.world.GridWorld;
import org.haw.ls.xancake.gridworld.game.world.GridWorldListener;
import org.haw.ls.xancake.gridworld.game.world.PlayableGridWorld;
import org.haw.ls.xancake.gridworld.ui.GridWorldPanel;
import org.haw.ls.xancake.gridworld.util.Numbers;

@SuppressWarnings("serial")
public class JGridWorldPanel extends JPanel implements GridWorldPanel, GridWorldListener {
	private static final int DEFAULT_GRID_SIZE = 32;
	
	private GridWorldPainter _painter;
	
	private GridWorld _world;
	private int _gridSize;
	
	private boolean _highlightMouseLocation;
	private boolean _highlightPossibleActions;
	
	public JGridWorldPanel() {
		this(null);
	}
	
	public JGridWorldPanel(GridWorld world) {
		this(world, DEFAULT_GRID_SIZE);
	}
	
	public JGridWorldPanel(GridWorld world, int gridSize) {
		_painter = new GridWorldPainter();
		setGridSize(gridSize);
		setWorld(world);
		
		addMouseMotionListener(new MouseAdapter() {
			@Override
			public void mouseMoved(MouseEvent e) {
				if(isHighlightMouseLocation()) {
					repaint();
				}
			}
		});
	}

	@Override
	public GridWorld getWorld() {
		return _world;
	}

	@Override
	public void setWorld(GridWorld world) {
		if(_world != null && _world instanceof PlayableGridWorld) {
			((PlayableGridWorld)_world).removeListener(this);
		}
		_world = world;
		if(_world != null && _world instanceof PlayableGridWorld) {
			((PlayableGridWorld)_world).addListener(this);
		}
	}
	
	@Override
	public int getGridSize() {
		return _gridSize;
	}

	@Override
	public void setGridSize(int size) {
		_gridSize = Numbers.require(size).greaterThan(1, "Die Größe der Felder darf nicht kleiner als 1 sein!");
	}
	
	@Override
	public boolean isHighlightPossibleActions() {
		return _highlightPossibleActions;
	}
	
	@Override
	public void setHighlightPossibleActions(boolean highlight) {
		_highlightPossibleActions = highlight;
	}
	
	@Override
	public boolean isHighlightMouseLocation() {
		return _highlightMouseLocation;
	}
	
	@Override
	public void setHighlightMouseLocation(boolean highlight) {
		_highlightMouseLocation = highlight;
	}
	
	@Override
	public int getWidth() {
		return _world==null ? 0 : _world.getWidth()*_gridSize+1;
	}
	
	@Override
	public int getHeight() {
		return _world==null ? 0 : _world.getHeight()*_gridSize+1;
	}
	
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		
		Graphics2D g2 = (Graphics2D)g;
		_painter.drawWorld(g2, _world, _gridSize);
		
		if(isHighlightPossibleActions()) {
			_painter.drawPossibleActions(g2, _world, _gridSize);
		}
		
		
		if(isHighlightMouseLocation()) {
			drawMouseHighlight(g2);
		}
	}
	
	private void drawMouseHighlight(Graphics2D g) {
		Point mouse = getMousePosition();
		if(mouse != null && mouse.x>=0 && mouse.x<=getWidth() && mouse.y>=0 && mouse.y<=getHeight()) {
			int fieldX = mouse.x / _gridSize;
			int fieldY = mouse.y / _gridSize;
			_painter.highlightField(g, _world, fieldX, fieldY, _gridSize, 3, Color.ORANGE);
			_painter.drawPlayerIfThere(g, _world, fieldX, fieldY, _gridSize);
		}
	}
	
	@Override
	public void onPlayerMoved(Player player) {
		repaint();
	}
}
