package com.mmodding.library.block.api.catalog;

import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ColorParticleOption;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.ParticleUtils;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.ScheduledTickAccess;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.LeavesBlock;
import net.minecraft.world.level.block.TintedParticleLeavesBlock;
import net.minecraft.world.level.block.UntintedParticleLeavesBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import org.jetbrains.annotations.Nullable;

public class AdvancedLeavesBlock extends LeavesBlock {

	private final ParticleOptions leafParticle;

	// Leave leafParticle parameter as null to use tinted leaf particles.
	public AdvancedLeavesBlock(float leafParticleChance, @Nullable ParticleOptions leafParticle, Properties settings) {
		super(leafParticleChance, settings);
		this.leafParticle = leafParticle;
		this.registerDefaultState(
			this.defaultBlockState()
				.setValue(this.getDistanceProperty(), this.getMaxDistance())
				.setValue(AdvancedLeavesBlock.PERSISTENT, false)
				.setValue(AdvancedLeavesBlock.WATERLOGGED, false)
		);
	}

	@Override
	public MapCodec<? extends LeavesBlock> codec() {
		return this.leafParticle == null ? TintedParticleLeavesBlock.CODEC : UntintedParticleLeavesBlock.CODEC;
	}

	public IntegerProperty getDistanceProperty() {
		return AdvancedLeavesBlock.DISTANCE;
	}

	protected int getMaxDistance() {
		return 7;
	}

	protected boolean isLogValid(BlockState state) {
		return state.is(BlockTags.LOGS);
	}

	protected boolean areLeavesValid(BlockState state) {
		return state.is(this);
	}

	public boolean areLeavesConnected() {
		return true;
	}

	@Override
	public boolean isRandomlyTicking(BlockState state) {
		return state.getValue(this.getDistanceProperty()) == this.getMaxDistance() && !state.getValue(AdvancedLeavesBlock.PERSISTENT);
	}

	@Override
	protected boolean decaying(BlockState state) {
		return !state.getValue(AdvancedLeavesBlock.PERSISTENT) && state.getValue(this.getDistanceProperty()) == this.getMaxDistance();
	}

	@Override
	public void tick(BlockState state, ServerLevel world, BlockPos pos, RandomSource random) {
		world.setBlock(pos, this.updateDistance(state, world, pos), Block.UPDATE_ALL);
	}

	@Override
	protected BlockState updateShape(BlockState state, LevelReader level, ScheduledTickAccess ticks, BlockPos pos, Direction directionToNeighbour, BlockPos neighbourPos, BlockState neighbourState, RandomSource random) {
		if (state.getValue(AdvancedLeavesBlock.WATERLOGGED)) {
			ticks.scheduleTick(pos, Fluids.WATER, Fluids.WATER.getTickDelay(level));
		}

		int i = this.getDistanceAt(neighbourState) + 1;
		if (i != 1 || state.getValue(this.getDistanceProperty()) != i) {
			ticks.scheduleTick(pos, this, 1);
		}

		return state;
	}

	private BlockState updateDistance(BlockState state, LevelAccessor world, BlockPos pos) {
		int distance = this.getMaxDistance();
		BlockPos.MutableBlockPos mutable = new BlockPos.MutableBlockPos();

		if (!this.areLeavesConnected()) {
			for (int i = -1; i <= 1; i++) {
				for (int j = -1; j <= 1; j++) {
					for (int k = -1; k <= 1; k++) {
						mutable.set(pos.offset(i, j, k));
						distance = Math.min(distance, this.getDistanceAt(world.getBlockState(mutable)) + 1);
						if (distance == 1) {
							break;
						}
					}
				}
			}
		}
		else {
			for (Direction direction : Direction.values()) {
				mutable.setWithOffset(pos, direction);
				distance = Math.min(distance, this.getDistanceAt(world.getBlockState(mutable)) + 1);
				if (distance == 1) {
					break;
				}
			}
		}

		return state.setValue(this.getDistanceProperty(), distance);
	}

	private int getDistanceAt(BlockState state) {
		if (this.isLogValid(state)) {
			return 0;
		}
		else if (this.areLeavesValid(state)) {
			return state.getValue(this.getDistanceProperty());
		}
		else {
			return this.getMaxDistance();
		}
	}

	@Override
	protected void spawnFallingLeavesParticle(Level level, BlockPos pos, RandomSource random) {
		if (this.leafParticle == null) {
			ColorParticleOption particle = ColorParticleOption.create(ParticleTypes.TINTED_LEAVES, level.getClientLeafTintColor(pos));
			ParticleUtils.spawnParticleBelow(level, pos, random, particle);
		}
		else {
			ParticleUtils.spawnParticleBelow(level, pos, random, this.leafParticle);
		}
	}

	@Override
	protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
		builder.add(this.getDistanceProperty());
		builder.add(AdvancedLeavesBlock.PERSISTENT);
		builder.add(AdvancedLeavesBlock.WATERLOGGED);
	}

	@Override
	public BlockState getStateForPlacement(BlockPlaceContext ctx) {
		FluidState fluidState = ctx.getLevel()
			.getFluidState(ctx.getClickedPos());

		BlockState blockState = this.defaultBlockState()
			.setValue(AdvancedLeavesBlock.PERSISTENT, true)
			.setValue(AdvancedLeavesBlock.WATERLOGGED, fluidState.getType() == Fluids.WATER);

		return this.updateDistance(blockState, ctx.getLevel(), ctx.getClickedPos());
	}
}
