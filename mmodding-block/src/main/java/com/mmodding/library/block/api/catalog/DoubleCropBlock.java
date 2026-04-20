package com.mmodding.library.block.api.catalog;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.ScheduledTickAccess;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.CropBlock;
import net.minecraft.world.level.block.PitcherCropBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.DoubleBlockHalf;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

public abstract class DoubleCropBlock extends PitcherCropBlock {

	public DoubleCropBlock(Properties properties) {
		super(properties);
	}

	protected abstract IntegerProperty getAgeProperty();

	public abstract int getMaxAge();

	public int getAge(final BlockState state) {
		return state.getValue(this.getAgeProperty());
	}

	@Override
	public abstract VoxelShape getShape(final BlockState state, final BlockGetter level, final BlockPos pos, final CollisionContext context);

	@Override
	public abstract VoxelShape getCollisionShape(final BlockState state, final BlockGetter level, final BlockPos pos, final CollisionContext context);

	/**
	 * Indicates if the plant block now takes two blocks depending on the age.
	 * @return the boolean
	 */
	public abstract boolean isDoubleBlock(int age);

	protected abstract int getNaturalAgeIncrease(final Level level);

	protected abstract int getBonemealAgeIncrease(final Level level);

	@Override
	public BlockState updateShape(
		final BlockState state,
		final LevelReader level,
		final ScheduledTickAccess ticks,
		final BlockPos pos,
		final Direction directionToNeighbour,
		final BlockPos neighbourPos,
		final BlockState neighbourState,
		final RandomSource random
	) {
		if (this.isDoubleBlock(this.getAge(state))) {
			return super.updateShape(state, level, ticks, pos, directionToNeighbour, neighbourPos, neighbourState, random);
		} else {
			return state.canSurvive(level, pos) ? state : Blocks.AIR.defaultBlockState();
		}
	}

	@Override
	public boolean isRandomlyTicking(final BlockState state) {
		return state.getValue(HALF) == DoubleBlockHalf.LOWER && this.getAge(state) < this.getMaxAge();
	}

	@Override
	public void randomTick(final BlockState state, final ServerLevel level, final BlockPos pos, final RandomSource random) {
		float growthSpeed = CropBlock.getGrowthSpeed(this, level, pos);
		boolean shouldProgressGrowth = random.nextInt((int) (25.0f / growthSpeed) + 1) == 0;
		if (shouldProgressGrowth) {
			this.growCrops(level, state, pos, this.getNaturalAgeIncrease(level));
		}
	}

	public boolean canGrow(final LevelReader level, final BlockPos lowerPos, final BlockState lowerState, final int newAge) {
		return this.getAge(lowerState) < this.getMaxAge()
			&& CropBlock.hasSufficientLight(level, lowerPos)
			&& level.isInsideBuildHeight(lowerPos.above())
			&& (!this.isDoubleBlock(newAge) || canGrowInto(level, lowerPos, lowerPos.above()));
	}

	public void growCrops(final ServerLevel level, final BlockState lowerState, final BlockPos lowerPos, final int increase) {
		int updatedAge = Math.min(lowerState.getValue(this.getAgeProperty()) + increase, this.getMaxAge());
		if (this.canGrow(level, lowerPos, lowerState, updatedAge)) {
			BlockState newLowerState = lowerState.setValue(this.getAgeProperty(), updatedAge);
			level.setBlock(lowerPos, newLowerState, 2);
			if (this.isDoubleBlock(updatedAge)) {
				level.setBlock(lowerPos.above(), newLowerState.setValue(HALF, DoubleBlockHalf.UPPER), 3);
			}
		}
	}

	public BlockPos getLowerPos(LevelReader level, BlockPos pos) {
		return level.getBlockState(pos).getValue(HALF).equals(DoubleBlockHalf.LOWER) ? pos : pos.below();
	}

	@Override
	public boolean isValidBonemealTarget(LevelReader level, BlockPos pos, BlockState state) {
		BlockPos lowerPos = this.getLowerPos(level, pos);
		BlockState lowerState = level.getBlockState(lowerPos);
		return this.canGrow(level, lowerPos, lowerState, this.getAge(lowerState) + 1);
	}

	@Override
	public void performBonemeal(ServerLevel level, RandomSource random, BlockPos pos, BlockState state) {
		BlockPos lowerPos = this.getLowerPos(level, pos);
		BlockState lowerState = level.getBlockState(lowerPos);
		this.growCrops(level, lowerState, lowerPos, this.getBonemealAgeIncrease(level));
	}

	public static boolean canGrowInto(final LevelReader level, final BlockPos pos, final BlockPos abovePos) {
		BlockState state = level.getBlockState(abovePos);
		return state.isAir() || state.is(level.getBlockState(pos).getBlock());
	}

	@Override
	protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
		builder.add(this.getAgeProperty());
		builder.add(HALF);
	}
}
