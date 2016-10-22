package org.haw.ls.xancake.gridworld.startup;

import java.awt.BorderLayout;
import java.util.ArrayList;
import java.util.List;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import org.haw.ls.xancake.gridworld.game.GridWorldGame;
import org.haw.ls.xancake.gridworld.game.action.GridWorldAction;
import org.haw.ls.xancake.gridworld.game.player.PlayerBehaviour;
import org.haw.ls.xancake.gridworld.game.world.GridWorldImpl;
import org.haw.ls.xancake.gridworld.game.world.PlayableGridWorld;
import org.haw.ls.xancake.gridworld.game.world.field.DefaultFieldTypes;
import org.haw.ls.xancake.gridworld.ui.swing.JGridWorldPanel;

@SuppressWarnings("serial")
public class JGridWorldPanelDemo extends JFrame {
	private GridWorldGame _game;
	private GUIBehaviour _behaviour;
	
	private JGridWorldPanel _worldPanel;
	private List<JButton> _buttons;
	
	public JGridWorldPanelDemo() {
		super("Grid World Panel Demo");
		
		_worldPanel = new JGridWorldPanel();
		_worldPanel.setHighlightPossibleActions(true);
		_worldPanel.setHighlightMouseLocation(true);
		
		_behaviour = new GUIBehaviour();
		_buttons = new ArrayList<>();
		for(GridWorldAction action : GridWorldAction.values()) {
			JButton button = new JButton(action.toString());
			_behaviour.initActionListener(button, action);
			_buttons.add(button);
		}
		
		JPanel controls = new JPanel();
		controls.setLayout(new BoxLayout(controls, BoxLayout.PAGE_AXIS));
		for(JButton button : _buttons) {
			controls.add(button);
		}
		controls.add(Box.createVerticalGlue());
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		getContentPane().add(new JScrollPane(_worldPanel), BorderLayout.CENTER);
		getContentPane().add(controls, BorderLayout.EAST);
		pack();
		setSize(600, 400);
		setLocationRelativeTo(null);
	}
	
	public void start() {
		PlayableGridWorld world = initWorld();
		_worldPanel.setWorld(world);
		_game = new GridWorldGame(world, _behaviour);
		setVisible(true);
		_game.start();
	}
	
	private class GUIBehaviour implements PlayerBehaviour {
		private GridWorldAction _choosenAction;
		
		private void initActionListener(JButton button, GridWorldAction action) {
			button.addActionListener(e -> {
				GUIBehaviour.this._choosenAction = action;
				synchronized(GUIBehaviour.this) {
					GUIBehaviour.this.notify();
				}
			});
		}
		
		@Override
		public GridWorldAction chooseAction(List<GridWorldAction> availableActions) {
			_choosenAction = null;
			for(JButton button : _buttons) {
				button.setEnabled(availableActions.contains(GridWorldAction.valueOf(button.getText())));
			}
			synchronized(this) {
				while(_choosenAction == null) {
					try {
						wait();
					} catch(InterruptedException e) {}
				}
			}
			for(JButton button : _buttons) {
				button.setEnabled(false);
			}
			return _choosenAction;
		}
	}
	
	public static void main(String[] args) {
		new JGridWorldPanelDemo().start();
	}
	
	private static PlayableGridWorld initWorld() {
		GridWorldImpl world = new GridWorldImpl(7, 6);
		world.setFieldType(1, 1, DefaultFieldTypes.WALL);
		world.setFieldType(1, 2, DefaultFieldTypes.WALL);
		world.setFieldType(1, 3, DefaultFieldTypes.WALL);
		world.setFieldType(2, 4, DefaultFieldTypes.WALL);
		world.setFieldType(3, 0, DefaultFieldTypes.WALL);
		world.setFieldType(3, 2, DefaultFieldTypes.WALL);
		world.setFieldType(3, 4, DefaultFieldTypes.WALL);
		world.setFieldType(4, 2, DefaultFieldTypes.WALL);
		world.setFieldType(4, 4, DefaultFieldTypes.WALL);
		world.setFieldType(5, 1, DefaultFieldTypes.WALL);
		world.setFieldType(6, 1, DefaultFieldTypes.WALL);
		world.setFieldType(6, 0, DefaultFieldTypes.FINISH);
		world.setStartField(1, 5);
		return world;
	}
}
