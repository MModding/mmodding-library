package com.mmodding.library.portal.impl.rectangular;

import java.util.Optional;
import java.util.function.Predicate;

import com.mmodding.library.portal.api.catalog.RectangularPortalBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.BlockPos.MutableBlockPos;
import net.minecraft.core.Direction.Axis;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.portal.PortalShape;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.apache.commons.lang3.mutable.MutableInt;
import org.jspecify.annotations.Nullable;

/**
 * A variant of {@link PortalShape} that accepts a {@link RectangularPortalBlock}.
 */
public class RectangularPortalShape {

	private static final int MIN_WIDTH = 2;
	public static final int MAX_WIDTH = 21;
	private static final int MIN_HEIGHT = 3;
	public static final int MAX_HEIGHT = 21;
	private static final float SAFE_TRAVEL_MAX_ENTITY_XY = 4.0F;
	private static final double SAFE_TRAVEL_MAX_VERTICAL_DELTA = 1.0;

	private final Axis axis;
	private final Direction rightDir;
	private final int numPortalBlocks;
	private final BlockPos bottomLeft;
	private final int height;
	private final int width;

	private RectangularPortalShape(Axis axis, int portalBlockCount, Direction rightDir, BlockPos bottomLeft, int width, int height) {
		this.axis = axis;
		this.numPortalBlocks = portalBlockCount;
		this.rightDir = rightDir;
		this.bottomLeft = bottomLeft;
		this.width = width;
		this.height = height;
	}

	public static Optional<RectangularPortalShape> findEmptyPortalShape(LevelAccessor level, RectangularPortalBlock portal, BlockPos pos, Axis preferredAxis) {
		return findPortalShape(level, portal, pos, shape -> shape.isValid() && shape.numPortalBlocks == 0, preferredAxis);
	}

	public static Optional<RectangularPortalShape> findPortalShape(LevelAccessor level, RectangularPortalBlock portal, BlockPos pos, Predicate<RectangularPortalShape> isValid, Axis preferredAxis) {
		Optional<RectangularPortalShape> firstAxis = Optional.of(findAnyShape(level, portal, pos, preferredAxis)).filter(isValid);
		if (firstAxis.isPresent()) {
			return firstAxis;
		} else {
			Axis otherAxis = preferredAxis == Axis.X ? Axis.Z : Axis.X;
			return Optional.of(findAnyShape(level, portal, pos, otherAxis)).filter(isValid);
		}
	}

	public static RectangularPortalShape findAnyShape(BlockGetter level, RectangularPortalBlock portal, BlockPos pos, Axis axis) {
		Direction rightDir = axis == Axis.X ? Direction.WEST : Direction.SOUTH;
		BlockPos bottomLeft = calculateBottomLeft(level, portal, rightDir, pos);
		if (bottomLeft == null) {
			return new RectangularPortalShape(axis, 0, rightDir, pos, 0, 0);
		} else {
			int width = calculateWidth(level, portal, bottomLeft, rightDir);
			if (width == 0) {
				return new RectangularPortalShape(axis, 0, rightDir, bottomLeft, 0, 0);
			} else {
				MutableInt portalBlockCountOutput = new MutableInt();
				int height = calculateHeight(level, portal, bottomLeft, rightDir, width, portalBlockCountOutput);
				return new RectangularPortalShape(axis, portalBlockCountOutput.intValue(), rightDir, bottomLeft, width, height);
			}
		}
	}

	@Nullable
	private static BlockPos calculateBottomLeft(BlockGetter level, RectangularPortalBlock portal, Direction rightDir, BlockPos pos) {
		int minY = Math.max(level.getMinY(), pos.getY() - MAX_HEIGHT);

		while (pos.getY() > minY && isEmpty(portal, level.getBlockState(pos.below()))) {
			pos = pos.below();
		}

		Direction leftDir = rightDir.getOpposite();
		int edge = getDistanceUntilEdgeAboveFrame(level, portal, pos, leftDir) - 1;
		return edge < 0 ? null : pos.relative(leftDir, edge);
	}

	private static int calculateWidth(BlockGetter level, RectangularPortalBlock portal, BlockPos bottomLeft, Direction rightDir) {
		int width = getDistanceUntilEdgeAboveFrame(level, portal, bottomLeft, rightDir);
		return width >= MIN_WIDTH && width <= MAX_WIDTH ? width : 0;
	}

	private static int getDistanceUntilEdgeAboveFrame(BlockGetter level, RectangularPortalBlock portal, BlockPos pos, Direction direction) {
		MutableBlockPos blockPos = new MutableBlockPos();

		for (int width = 0; width <= MAX_WIDTH; width++) {
			blockPos.set(pos).move(direction, width);
			BlockState blockState = level.getBlockState(blockPos);
			if (!isEmpty(portal, blockState)) {
				if (blockState.is(portal.getFrameBlock())) {
					return width;
				}
				break;
			}

			BlockState belowState = level.getBlockState(blockPos.move(Direction.DOWN));
			if (!belowState.is(portal.getFrameBlock())) {
				break;
			}
		}

		return 0;
	}

	private static int calculateHeight(BlockGetter level, RectangularPortalBlock portal, BlockPos bottomLeft, Direction rightDir, int width, MutableInt portalBlockCount) {
		MutableBlockPos pos = new MutableBlockPos();
		int height = getDistanceUntilTop(level, portal, bottomLeft, rightDir, pos, width, portalBlockCount);
		return height >= MIN_HEIGHT && height <= MAX_HEIGHT && hasTopFrame(level, portal, bottomLeft, rightDir, pos, width, height) ? height : 0;
	}

	private static boolean hasTopFrame(BlockGetter level, RectangularPortalBlock portal, BlockPos bottomLeft, Direction rightDir, MutableBlockPos pos, int width, int height) {
		for (int i = 0; i < width; i++) {
			MutableBlockPos framePos = pos.set(bottomLeft).move(Direction.UP, height).move(rightDir, i);
			if (!level.getBlockState(framePos).is(portal.getFrameBlock())) {
				return false;
			}
		}

		return true;
	}

	private static int getDistanceUntilTop(BlockGetter level, RectangularPortalBlock portal, BlockPos bottomLeft, Direction rightDir, MutableBlockPos pos, int width, MutableInt portalBlockCount) {
		for (int height = 0; height < MAX_HEIGHT; height++) {
			pos.set(bottomLeft).move(Direction.UP, height).move(rightDir, -1);
			if (!level.getBlockState(pos).is(portal.getFrameBlock())) {
				return height;
			}

			pos.set(bottomLeft).move(Direction.UP, height).move(rightDir, width);
			if (!level.getBlockState(pos).is(portal.getFrameBlock())) {
				return height;
			}

			for (int i = 0; i < width; i++) {
				pos.set(bottomLeft).move(Direction.UP, height).move(rightDir, i);
				BlockState state = level.getBlockState(pos);
				if (!isEmpty(portal, state)) {
					return height;
				}

				if (state.is(portal)) {
					portalBlockCount.increment();
				}
			}
		}

		return MAX_HEIGHT;
	}

	private static boolean isEmpty(RectangularPortalBlock portal, BlockState state) {
		return state.isAir() || state.is(BlockTags.FIRE) || state.is(portal);
	}

	public boolean isValid() {
		return this.width >= MIN_WIDTH && this.width <= MAX_WIDTH && this.height >= MIN_HEIGHT && this.height <= MAX_HEIGHT;
	}

	public void createPortalBlocks(LevelAccessor level, RectangularPortalBlock portal) {
		BlockState portalState = portal.defaultBlockState().setValue(BlockStateProperties.HORIZONTAL_AXIS, this.axis);
		BlockPos.betweenClosed(this.bottomLeft, this.bottomLeft.relative(Direction.UP, this.height - 1).relative(this.rightDir, this.width - 1))
			.forEach(pos -> level.setBlock(pos, portalState, 18));
	}

	public boolean isComplete() {
		return this.isValid() && this.numPortalBlocks == this.width * this.height;
	}

	public static Vec3 findCollisionFreePosition(Vec3 bottomCenter, LevelReader level, Entity entity, EntityDimensions dimensions) {
		if (!(dimensions.width() > SAFE_TRAVEL_MAX_ENTITY_XY) && !(dimensions.height() > SAFE_TRAVEL_MAX_ENTITY_XY)) {
			double halfHeight = dimensions.height() / 2.0;
			Vec3 center = bottomCenter.add(0.0, halfHeight, 0.0);
			VoxelShape allowedCenters = Shapes.create(AABB.ofSize(center, dimensions.width(), 0.0, dimensions.width()).expandTowards(0.0, SAFE_TRAVEL_MAX_VERTICAL_DELTA, 0.0).inflate(1.0E-6));
			Optional<Vec3> collisionFreePosition = level.findFreePosition(
				entity, allowedCenters, center, dimensions.width(), dimensions.height(), dimensions.width()
			);
			Optional<Vec3> collisionFreeBottomCenter = collisionFreePosition.map(vec -> vec.subtract(0.0, halfHeight, 0.0));
			return collisionFreeBottomCenter.orElse(bottomCenter);
		} else {
			return bottomCenter;
		}
	}
}
