package org.haw.ls.xancake.gridworld.startup;

import java.awt.BorderLayout;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.filechooser.FileFilter;
import org.haw.ls.xancake.gridworld.game.GridWorldGame;
import org.haw.ls.xancake.gridworld.game.action.GridWorldAction;
import org.haw.ls.xancake.gridworld.game.player.GridWorldPlayerBehaviour;
import org.haw.ls.xancake.gridworld.game.world.PlayableGridWorld;
import org.haw.ls.xancake.gridworld.game.world.io.GridWorldIO;
import org.haw.ls.xancake.gridworld.ui.swing.JGridWorldPanel;

@SuppressWarnings("serial")
public class JGridWorldPanelDemo extends JFrame {
	private GridWorldGame _game;
	private PlayableGridWorld _world;
	
	private JGridWorldPanel _worldPanel;
	private JButton _load;
	private JButton _reset;
	private List<JButton> _buttons;
	
	private JFileChooser _fileChooser;
	
	public JGridWorldPanelDemo() {
		super("Grid World Panel Demo");
		
		_worldPanel = new JGridWorldPanel();
		_worldPanel.setHighlightPossibleActions(true);
		_worldPanel.setHighlightMouseLocation(true);
		
		_load = new JButton("Load...");
		_reset = new JButton("Reset");
		
		_load.addActionListener(e -> onLoad());
		_reset.addActionListener(e -> onReset());
		_reset.setEnabled(false);
		
		try {
			_fileChooser = new JFileChooser(new File(getClass().getClassLoader().getResource("gridworld").toURI()));
			_fileChooser.setMultiSelectionEnabled(false);
			_fileChooser.setFileFilter(new FileFilter() {
				private static final String FILETYPE = ".gridworld";
				
				@Override
				public String getDescription() {
					return "*" + FILETYPE;
				}
				
				@Override
				public boolean accept(File f) {
					return f.isDirectory() || f.getName().endsWith(FILETYPE);
				}
			});
		} catch(URISyntaxException e) {
			throw new RuntimeException(e);
		}
		
		_buttons = new ArrayList<>();
		for(GridWorldAction action : GridWorldAction.values()) {
			_buttons.add(new JButton(action.toString()));
		}
		
		JPanel controls = new JPanel();
		controls.setLayout(new BoxLayout(controls, BoxLayout.LINE_AXIS));
		controls.add(_load);
		controls.add(_reset);
		controls.add(Box.createHorizontalGlue());
		
		JPanel actions = new JPanel();
		actions.setLayout(new BoxLayout(actions, BoxLayout.PAGE_AXIS));
		for(JButton button : _buttons) {
			actions.add(button);
		}
		actions.add(Box.createVerticalGlue());
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		getContentPane().add(new JScrollPane(_worldPanel), BorderLayout.CENTER);
		getContentPane().add(controls, BorderLayout.NORTH);
		getContentPane().add(actions, BorderLayout.EAST);
		pack();
		setSize(600, 400);
		setLocationRelativeTo(null);
	}
	
	private void onLoad() {
		int choice = _fileChooser.showOpenDialog(this);
		if(choice == JFileChooser.APPROVE_OPTION) {
			try {
				GridWorldIO io = new GridWorldIO();
				_world = io.loadGridWorld(_fileChooser.getSelectedFile());
				_worldPanel.setWorld(_world);
				if(_game != null) {
					_game.stop();
				}
				_game = new GridWorldGame(_world, new GUIBehaviour(_buttons));
				new Thread(() -> _game.start()).start();
				_reset.setEnabled(true);
			} catch(IOException e) {
				JOptionPane.showMessageDialog(this, e.toString(), e.getMessage(), JOptionPane.ERROR_MESSAGE);
			}
		}
	}
	
	private void onReset() {
		if(_world != null) {
    		_world.reset();
    		if(_game.hasEnded()) {
    			_game = new GridWorldGame(_world, new GUIBehaviour(_buttons));
    			new Thread(() -> _game.start()).start();
    		}
		}
	}
	
	private class GUIBehaviour implements GridWorldPlayerBehaviour {
		private GridWorldAction _choosenAction;
		
		private GUIBehaviour(List<JButton> actionButtons) {
			for(JButton button : actionButtons) {
				button.addActionListener(e -> {
					GUIBehaviour.this._choosenAction = GridWorldAction.valueOf(button.getText());
					synchronized(GUIBehaviour.this) {
						GUIBehaviour.this.notify();
					}
				});
			}
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
	
	public static void main(String[] args) throws Exception {
		new JGridWorldPanelDemo().setVisible(true);
	}
}
