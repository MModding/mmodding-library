package com.mmodding.library.portal.api.catalog;

import com.mmodding.library.math.api.Colliders;
import com.mmodding.library.math.api.PosAndRot;
import com.mmodding.library.portal.api.NodeBuildingPortal;
import com.mmodding.library.portal.api.util.PortalLookup;
import com.mmodding.library.portal.impl.rectangular.RectangularPortalShape;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Vec3i;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.BlockUtil;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.ai.village.poi.PoiType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.ScheduledTickAccess;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.NetherPortalBlock;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.level.border.WorldBorder;
import net.minecraft.world.level.dimension.DimensionType;
import net.minecraft.world.level.gamerules.GameRules;
import net.minecraft.world.level.portal.TeleportTransition;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jspecify.annotations.Nullable;

import java.util.Map;
import java.util.function.Supplier;
import java.util.function.ToIntBiFunction;

/**
 * A rectangular portal working like the Nether portal you know so well.
 */
public class RectangularPortalBlock extends DimensionScaledPortalBlock implements NodeBuildingPortal<RectangularPortalBlock.Context> {

	public static final Property<Direction.Axis> AXIS = BlockStateProperties.HORIZONTAL_AXIS;

	private static final Map<Direction.Axis, VoxelShape> SHAPES = Shapes.rotateHorizontalAxis(Block.column(4.0, 16.0, 0.0, 16.0));

	private final Supplier<Block> frameBlockSupplier;

	public RectangularPortalBlock(Supplier<Block> frameBlockSupplier, ResourceKey<Level> dimension, ResourceKey<PoiType> pointOfInterest, int lookupRadius, ToIntBiFunction<ServerLevel, BlockPos> heightComparator, Properties properties) {
		super(dimension, pointOfInterest, lookupRadius, heightComparator, properties);
		this.frameBlockSupplier = frameBlockSupplier;
		this.registerDefaultState(this.defaultBlockState().setValue(AXIS, Direction.Axis.X));
	}

	public RectangularPortalBlock(Supplier<Block> frameBlockSupplier, ResourceKey<Level> dimension, ResourceKey<PoiType> pointOfInterest, int lookupRadius, PortalLookup portalLookup, Properties properties) {
		super(dimension, pointOfInterest, lookupRadius, portalLookup, properties);
		this.frameBlockSupplier = frameBlockSupplier;
		this.registerDefaultState(this.defaultBlockState().setValue(AXIS, Direction.Axis.X));
	}

	@Override
	protected void createBlockStateDefinition(final StateDefinition.Builder<Block, BlockState> builder) {
		builder.add(AXIS);
	}

	@Override
	public Colliders createPortalFrameColliders(Context context) {
		Vec3i bottomCorner = new Vec3i(0, -1, 0).relative(context.axis().getNegative(), 1);
		return Colliders.combine(
			Colliders.column(bottomCorner, Direction.Axis.Y, 5),
			Colliders.column(bottomCorner.relative(context.axis(), 3), Direction.Axis.Y, 5),
			Colliders.column(bottomCorner, context.axis(), 4),
			Colliders.column(bottomCorner.above(4), context.axis(), 4)
		);
	}

	@Override
	public void createPortal(ServerLevel level, BlockPos placementOrigin, Context context) {
		BlockState frameBlockState = this.frameBlockSupplier.get().defaultBlockState();
		for (int i = 0; i < 5; i++) {
			level.setBlockAndUpdate(placementOrigin.above(i), frameBlockState);
			level.setBlockAndUpdate(placementOrigin.relative(context.axis(), 3).above(i), frameBlockState);
			if (i != 4) {
				level.setBlockAndUpdate(placementOrigin.relative(context.axis(), i), frameBlockState);
				level.setBlockAndUpdate(placementOrigin.above(4).relative(context.axis(), i), frameBlockState);
			}
		}
		for (int i = 1; i < 4; i++) {
			for (int j = 1; j < 3; j++) {
				level.setBlockAndUpdate(placementOrigin.relative(context.axis(), j).above(i), this.defaultBlockState().setValue(AXIS, context.axis()));
			}
		}
	}

	@Override
	public @Nullable TeleportTransition getPortalDestination(ServerLevel currentLevel, Entity entity, BlockPos portalEntryPos) {
		ResourceKey<Level> newDimension = currentLevel.dimension() == this.dimension ? Level.OVERWORLD : this.dimension;
		ServerLevel newLevel = currentLevel.getServer().getLevel(newDimension);
		if (newLevel != null) {
			WorldBorder newWorldBorder = newLevel.getWorldBorder();
			double teleportationScale = DimensionType.getTeleportationScale(currentLevel.dimensionType(), newLevel.dimensionType());
			BlockPos lookupOrigin = newWorldBorder.clampToBounds(entity.getX() * teleportationScale, entity.getY(), entity.getZ() * teleportationScale);
			return newLevel.getPortalBinder().lookupOrBuildClosestForTransition(this, entity, portalEntryPos, new Context(currentLevel.getBlockState(portalEntryPos).getValue(RectangularPortalBlock.AXIS)), lookupOrigin, this.lookupRadius).orElse(null);
		}
		else {
			return null;
		}
	}

	@Override
	public PosAndRot evaluateDestinationPosition(LevelReader level, Entity entity, BlockPos sourcePortalPos, BlockPos suitablePos) {
		BlockUtil.FoundRectangle exitPortal = BlockUtil.getLargestRectangleAround(
			suitablePos,
			level.getBlockState(suitablePos).getValue(BlockStateProperties.HORIZONTAL_AXIS),
			21, Direction.Axis.Y, 21,
			blockPos -> level.getBlockState(blockPos) == this.defaultBlockState()
		);
		return this.getDimensionTransitionFromExit(entity, sourcePortalPos, exitPortal, level, TeleportTransition.PLAY_PORTAL_SOUND.then(TeleportTransition.PLACE_PORTAL_TICKET));
	}

	private PosAndRot getDimensionTransitionFromExit(Entity entity, BlockPos portalEntryPos, BlockUtil.FoundRectangle exitPortal, LevelReader newLevel, TeleportTransition.PostTeleportTransition post) {
		BlockState blockState = entity.level().getBlockState(portalEntryPos);
		Direction.Axis axis;
		Vec3 offset;
		if (blockState.getBlock() instanceof RectangularPortalBlock) {
			axis = blockState.getValue(RectangularPortalBlock.AXIS);
			BlockUtil.FoundRectangle portalArea = BlockUtil.getLargestRectangleAround(portalEntryPos, axis, 21, Direction.Axis.Y, 21, pos -> entity.level().getBlockState(pos) == blockState);
			offset = entity.getRelativePortalPosition(axis, portalArea);
		} else {
			axis = Direction.Axis.X;
			offset = new Vec3(0.5, 0.0, 0.0);
		}
		return this.createDimensionTransition(newLevel, exitPortal, axis, offset, entity, post);
	}

	private PosAndRot createDimensionTransition(LevelReader newLevel, BlockUtil.FoundRectangle foundRectangle, Direction.Axis portalAxis, Vec3 offset, Entity entity, TeleportTransition.PostTeleportTransition post) {
		BlockPos bottomLeft = foundRectangle.minCorner;
		BlockState blockState = newLevel.getBlockState(bottomLeft);
		Direction.Axis axis = blockState.getOptionalValue(BlockStateProperties.HORIZONTAL_AXIS).orElse(Direction.Axis.X);
		double width = foundRectangle.axis1Size;
		double height = foundRectangle.axis2Size;
		EntityDimensions dimensions = entity.getDimensions(entity.getPose());
		int outputRotation = portalAxis == axis ? 0 : 90;
		double offsetRight = dimensions.width() / 2.0 + (width - dimensions.width()) * offset.x();
		double offsetUp = (height - dimensions.height()) * offset.y();
		double offsetForward = 0.5 + offset.z();
		boolean xAligned = axis == Direction.Axis.X;
		Vec3 targetPos = new Vec3(
			bottomLeft.getX() + (xAligned ? offsetRight : offsetForward), bottomLeft.getY() + offsetUp, bottomLeft.getZ() + (xAligned ? offsetForward : offsetRight)
		);
		Vec3 collisionFreePos = RectangularPortalShape.findCollisionFreePosition(targetPos, newLevel, entity, dimensions);
		return new PosAndRot(collisionFreePos, 0.0f, outputRotation);
	}

	@Override
	protected VoxelShape getShape(final BlockState state, final BlockGetter level, final BlockPos pos, final CollisionContext context) {
		return SHAPES.get(state.getValue(AXIS));
	}

	/**
	 * @see NetherPortalBlock
	 */
	@Override
	protected BlockState updateShape(BlockState state, LevelReader level, ScheduledTickAccess ticks, BlockPos pos, Direction directionToNeighbour, BlockPos neighbourPos, BlockState neighbourState, RandomSource random) {
		Direction.Axis updateAxis = directionToNeighbour.getAxis();
		Direction.Axis axis = state.getValue(AXIS);
		boolean wrongAxis = axis != updateAxis && updateAxis.isHorizontal();
		return !wrongAxis && !neighbourState.is(this) && !RectangularPortalShape.findAnyShape(level, this, pos, axis).isComplete()
			? Blocks.AIR.defaultBlockState()
			: super.updateShape(state, level, ticks, pos, directionToNeighbour, neighbourPos, neighbourState, random);
	}

	@Override
	public int getPortalTransitionTime(final ServerLevel level, final Entity entity) {
		if (entity instanceof Player player) {
			return Math.max(0, level.getGameRules().get(player.getAbilities().invulnerable ? GameRules.PLAYERS_NETHER_PORTAL_CREATIVE_DELAY : GameRules.PLAYERS_NETHER_PORTAL_DEFAULT_DELAY));
		}
		else {
			return 0;
		}
	}

	@Override
	protected BlockState rotate(final BlockState state, final Rotation rotation) {
		return switch (rotation) {
			case COUNTERCLOCKWISE_90, CLOCKWISE_90 -> switch (state.getValue(AXIS)) {
				case X -> state.setValue(AXIS, Direction.Axis.Z);
				case Z -> state.setValue(AXIS, Direction.Axis.X);
				default -> state;
			};
			default -> state;
		};
	}

	public Block getFrameBlock() {
		return this.frameBlockSupplier.get();
	}

	public record Context(Direction.Axis axis) {}
}
