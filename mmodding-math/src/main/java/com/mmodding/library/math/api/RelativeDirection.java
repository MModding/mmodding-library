package com.mmodding.library.math.api;

import net.minecraft.util.math.Direction;

public enum RelativeDirection {

	TOP,
	BOTTOM,
	FRONT,
	BEHIND,
	LEFT,
	RIGHT;

	public Direction transform(Direction direction) {
		return switch (this) {
			case TOP -> direction.rotateCounterclockwise(Direction.Axis.X);
			case BOTTOM -> direction.rotateClockwise(Direction.Axis.X);
			case FRONT -> direction;
			case BEHIND -> direction.getOpposite();
			case LEFT -> direction.rotateCounterclockwise(Direction.Axis.Y);
			case RIGHT -> direction.rotateClockwise(Direction.Axis.Y);
		};
	}
}
