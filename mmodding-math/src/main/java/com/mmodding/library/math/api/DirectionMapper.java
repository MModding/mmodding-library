package com.mmodding.library.math.api;

import net.minecraft.core.Direction;

public enum DirectionMapper {

	TOP,
	BOTTOM,
	FRONT,
	BEHIND,
	LEFT,
	RIGHT;

	public Direction transform(Direction direction) {
		return switch (this) {
			case TOP -> direction.getCounterClockWise(Direction.Axis.X);
			case BOTTOM -> direction.getClockWise(Direction.Axis.X);
			case FRONT -> direction;
			case BEHIND -> direction.getOpposite();
			case LEFT -> direction.getCounterClockWise(Direction.Axis.Y);
			case RIGHT -> direction.getClockWise(Direction.Axis.Y);
		};
	}
}
