package com.mmodding.mmodding_lib.library.utils;

import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;

import java.util.Arrays;

public class VoxelShapeUtils {

	public static RotatingVoxelShapeFactory horizontalRotatingCuboid(CuboidFactory factory, double minX, double minY, double minZ, double maxX, double maxY, double maxZ) {
		return direction -> switch (direction) {
			case NORTH -> factory.cuboid(minX, minY, minZ, maxX, maxY, maxZ);
			case WEST -> factory.cuboid(minZ, minY, minX, maxZ, maxY, maxX);
			case SOUTH -> factory.cuboid(maxX, minY, 1 - minZ, minX, maxY, 1 - maxZ);
			case EAST -> factory.cuboid(1 - minZ, minY, minX, 1 - maxZ, maxY, maxX);
			default -> throw new IllegalArgumentException(direction + " is not a horizontal direction.");
		};
	}

	public static RotatingVoxelShapeFactory rotatingUnion(RotatingVoxelShapeFactory first, RotatingVoxelShapeFactory second) {
		return direction -> VoxelShapes.union(first.create(direction), second.create(direction));
	}

	public static RotatingVoxelShapeFactory rotatingUnion(RotatingVoxelShapeFactory first, RotatingVoxelShapeFactory... others) {
		return direction -> VoxelShapes.union(
			first.create(direction),
			Arrays.stream(others).map(factory -> factory.create(direction)).toArray(VoxelShape[]::new)
		);
	}

	@FunctionalInterface
	public interface RotatingVoxelShapeFactory {

		VoxelShape create(Direction direction);
	}

	@FunctionalInterface
	public interface CuboidFactory {

		VoxelShape cuboid(double minX, double minY, double minZ, double maxX, double maxY, double maxZ);
	}
}
