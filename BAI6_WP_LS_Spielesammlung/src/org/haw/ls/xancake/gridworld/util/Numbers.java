package org.haw.ls.xancake.gridworld.util;

public final class Numbers {
	private Numbers() {}
	
	public static NumberRequirement require(int x) {
		return new NumberRequirement(x);
	}
	
	public static class NumberRequirement {
		private int _number;
		
		private NumberRequirement(int number) {
			_number = number;
		}
		
		public int equal(int number, String errorMessage) {
			if(_number != number) {
				throw new IllegalArgumentException(errorMessage);
			}
			return _number;
		}
		
		public int notEqual(int number, String errorMessage) {
			if(_number == number) {
				throw new IllegalArgumentException(errorMessage);
			}
			return _number;
		}
		
		public int greaterThan(int number, String errorMessage) {
			if(_number <= number) {
				throw new IllegalArgumentException(errorMessage);
			}
			return _number;
		}
		
		public int greaterThanOrEqual(int number, String errorMessage) {
			if(_number < number) {
				throw new IllegalArgumentException(errorMessage);
			}
			return _number;
		}
		
		public int lessThan(int number, String errorMessage) {
			if(_number >= number) {
				throw new IllegalArgumentException(errorMessage);
			}
			return _number;
		}
		
		public int lessThanOrEqual(int number, String errorMessage) {
			if(_number > number) {
				throw new IllegalArgumentException(errorMessage);
			}
			return _number;
		}
	}
}
