package org.haw.ls.xancake.viergewinnt.player;

import java.util.Objects;
import org.haw.ls.xancake.viergewinnt.game.VierGewinntBoard.Color;

public abstract class AbstractVierGewinntPlayer implements VierGewinntPlayer {
	private Color _color;
	
	@Override
	public Color getColor() {
		return _color;
	}
	
	@Override
	public void setColor(Color color) {
		_color = Objects.requireNonNull(color);
	}
}
