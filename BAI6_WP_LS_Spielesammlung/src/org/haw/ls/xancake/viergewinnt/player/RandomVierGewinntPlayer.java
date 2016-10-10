package org.haw.ls.xancake.viergewinnt.player;

import java.util.List;
import java.util.Objects;
import java.util.Random;

public class RandomVierGewinntPlayer extends AbstractVierGewinntPlayer implements VierGewinntPlayer {
	private Random _random;
	
	public RandomVierGewinntPlayer() {
		this(new Random());
	}
	
	public RandomVierGewinntPlayer(long seed) {
		this(new Random(seed));
	}
	
	public RandomVierGewinntPlayer(Random random) {
		_random = Objects.requireNonNull(random);
	}
	
	@Override
	public int chooseMove(List<Integer> validMoves) {
		return validMoves.get(_random.nextInt(validMoves.size()));
	}
}
