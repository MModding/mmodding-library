package com.mmodding.mmodding_lib.library.utils;

import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;

import java.util.HashSet;
import java.util.Set;
import java.util.function.Consumer;

public class RadiusUtils {

	public static void forBlockPosInLine(BlockPos pos1, BlockPos pos2, Consumer<? super BlockPos> consumer) {
		Vec3d vector = Vec3d.ofCenter(pos1).relativize(Vec3d.ofCenter(pos2)).normalize();
		Vec3d mutable = Vec3d.ofCenter(pos1);
		do {
			mutable = mutable.add(vector);
			consumer.accept(new BlockPos(mutable));
		} while (!new BlockPos(mutable).equals(pos2));
	}

	public static void forBlockPosInCubicRadius(BlockPos pos, int radius, Consumer<? super BlockPos> execute) {
		BlockPos.iterate(
			pos.getX() - radius,
			pos.getY() - radius,
			pos.getZ() - radius,
			pos.getX() + radius,
			pos.getY() + radius,
			pos.getZ() + radius
		).forEach(execute);
	}

	public static void iterateFromCenterInSquare(BlockPos center, int radius, Consumer<? super BlockPos> action) {
		RadiusUtils.iterateFromCenter(center, radius, radius, radius, action);
	}

	public static void iterateFromCenter(BlockPos center, int radiusX, int radiusY, int radiusZ, Consumer<? super BlockPos> action) {
		Set<BlockPos> alreadyChecked = new HashSet<>();
		BlockPos firstExtremis = center.add(radiusX, radiusY, radiusZ);
		BlockPos lastExtremis = center.add(-radiusX, -radiusY, -radiusZ);
		for (Direction direction : Direction.values()) {
			RadiusUtils.checkActionNext(center.offset(direction), direction.getOpposite(), alreadyChecked, firstExtremis, lastExtremis, action);
		}
	}

	private static void checkActionNext(BlockPos pos, Direction excluded, Set<BlockPos> alreadyChecked, BlockPos firstExtremis, BlockPos lastExtremis, Consumer<? super BlockPos> action) {
		if (alreadyChecked.stream().noneMatch(current -> current.equals(pos))) {
			BlockPos firstSubtract = pos.subtract(firstExtremis);
			BlockPos lastSubtract = pos.subtract(lastExtremis);
			boolean firstCheck = firstSubtract.getX() <= 0 && firstSubtract.getY() <= 0 && firstSubtract.getZ() <= 0;
			boolean lastCheck = -lastSubtract.getX() <= 0 && -lastSubtract.getY() <= 0 && -lastSubtract.getZ() <= 0;

			if (firstCheck && lastCheck) {
				alreadyChecked.add(pos);
				action.accept(pos);
				for (Direction direction : Direction.values()) {
					if (direction != excluded) {
						RadiusUtils.checkActionNext(pos.offset(direction), direction.getOpposite(), alreadyChecked, firstExtremis, lastExtremis, action);
					}
				}
			}
		}
	}
}
