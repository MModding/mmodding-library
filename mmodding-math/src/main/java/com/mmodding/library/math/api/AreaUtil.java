package com.mmodding.library.math.api;

import java.util.HashSet;
import java.util.Set;
import java.util.function.Consumer;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Vec3i;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.phys.Vec3;

// Will need to hide that to impl later.
public class AreaUtil {

	public static Vec3i rotatePlacementInArea(Vec3i pos, int areaLength, int areaWidth, Rotation rotation) {
		return switch (rotation) {
			case NONE -> new Vec3i(pos.getX(), pos.getY(), pos.getZ());
			case CLOCKWISE_90 -> new Vec3i(pos.getZ(), pos.getY(), pos.getX());
			case CLOCKWISE_180 -> new Vec3i(areaLength - pos.getX(), pos.getY(), areaWidth - pos.getZ());
			case COUNTERCLOCKWISE_90 -> new Vec3i(areaWidth - pos.getZ(), pos.getY(), areaLength - pos.getX());
		};
	}

	public static void forBlockPosInLine(BlockPos pos1, BlockPos pos2, Consumer<? super BlockPos> consumer) {
		Vec3 vector = Vec3.atCenterOf(pos1).vectorTo(Vec3.atCenterOf(pos2)).normalize();
		Vec3 mutable = Vec3.atCenterOf(pos1);
		do {
			mutable = mutable.add(vector);
			consumer.accept(BlockPos.containing(mutable));
		} while (!BlockPos.containing(mutable).equals(pos2));
	}

	public static void forBlockPosInCubicRadius(BlockPos pos, int radius, Consumer<? super BlockPos> execute) {
		BlockPos.betweenClosed(
			pos.getX() - radius,
			pos.getY() - radius,
			pos.getZ() - radius,
			pos.getX() + radius,
			pos.getY() + radius,
			pos.getZ() + radius
		).forEach(execute);
	}

	public static void iterateFromCenterInSquare(BlockPos center, int radius, Consumer<? super BlockPos> action) {
		AreaUtil.iterateFromCenter(center, radius, radius, radius, action);
	}

	public static void iterateFromCenter(BlockPos center, int radiusX, int radiusY, int radiusZ, Consumer<? super BlockPos> action) {
		Set<BlockPos> alreadyChecked = new HashSet<>();
		BlockPos firstExtremis = center.offset(radiusX, radiusY, radiusZ);
		BlockPos lastExtremis = center.offset(-radiusX, -radiusY, -radiusZ);
		for (Direction direction : Direction.values()) {
			AreaUtil.checkActionNext(center.relative(direction), direction.getOpposite(), alreadyChecked, firstExtremis, lastExtremis, action);
		}
	}

	private static void checkActionNext(BlockPos pos, Direction excluded, Set<BlockPos> alreadyChecked, BlockPos firstExtremis, BlockPos lastExtremis, Consumer<? super BlockPos> action) {
		if (!alreadyChecked.contains(pos)) {
			BlockPos firstSubtract = pos.subtract(firstExtremis);
			BlockPos lastSubtract = pos.subtract(lastExtremis);
			boolean firstCheck = firstSubtract.getX() <= 0 && firstSubtract.getY() <= 0 && firstSubtract.getZ() <= 0;
			boolean lastCheck = -lastSubtract.getX() <= 0 && -lastSubtract.getY() <= 0 && -lastSubtract.getZ() <= 0;

			if (firstCheck && lastCheck) {
				alreadyChecked.add(pos);
				action.accept(pos);
				for (Direction direction : Direction.values()) {
					if (direction != excluded) {
						AreaUtil.checkActionNext(pos.relative(direction), direction.getOpposite(), alreadyChecked, firstExtremis, lastExtremis, action);
					}
				}
			}
		}
	}
}
