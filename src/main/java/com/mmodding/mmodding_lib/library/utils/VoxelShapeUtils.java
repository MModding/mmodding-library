package com.mmodding.mmodding_lib.library.utils;

import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;

import java.util.Arrays;

public class VoxelShapeUtils {

	public static RotatingVoxelShapeFactory horizontalRotatingCuboid(CuboidFactory factory, double minX, double minY, double minZ, double maxX, double maxY, double maxZ) {
		return direction -> switch (direction) {
			case NORTH, SOUTH -> {
				VoxelShape shape = factory.cuboid(minX, minY, minZ, maxX, maxY, maxZ);
				if (direction.equals(Direction.NORTH)) {
					yield shape;
				}
				else {
					double xMin = shape.getMin(Direction.Axis.X);
					double yMin = shape.getMin(Direction.Axis.Y);
					double zMin = shape.getMin(Direction.Axis.Z);
					double xMax = shape.getMax(Direction.Axis.X);
					double yMax = shape.getMax(Direction.Axis.Y);
					double zMax = shape.getMax(Direction.Axis.Z);
					yield VoxelShapes.cuboid(xMin, yMin, 1 - zMax, xMax, yMax, 1 - zMin);
				}
			}
			case WEST, EAST -> {
				VoxelShape shape = factory.cuboid(minZ, minY, minX, maxZ, maxY, maxX);
				if (direction.equals(Direction.WEST)) {
					yield shape;
				}
				else {
					double xMin = shape.getMin(Direction.Axis.X);
					double yMin = shape.getMin(Direction.Axis.Y);
					double zMin = shape.getMin(Direction.Axis.Z);
					double xMax = shape.getMax(Direction.Axis.X);
					double yMax = shape.getMax(Direction.Axis.Y);
					double zMax = shape.getMax(Direction.Axis.Z);
					yield VoxelShapes.cuboid(1 - xMax, yMin, zMin, 1 - xMin, yMax, zMax);
				}
			}
			default -> throw new IllegalArgumentException(direction + " is not a horizontal direction");
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
