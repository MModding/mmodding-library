package com.mmodding.library.block.api.catalog;

import com.mmodding.library.block.mixin.PointedDripstoneBlockAccessor;
import java.util.function.Supplier;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.PointedDripstoneBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.DripstoneThickness;
import net.minecraft.world.level.material.Fluids;

public class AdvancedPointedDripstoneBlock extends PointedDripstoneBlock {

	private final Supplier<Block> dripstoneBlock;

	public AdvancedPointedDripstoneBlock(Supplier<Block> dripstoneBlock, Properties settings) {
		super(settings);
		this.dripstoneBlock = dripstoneBlock;
	}

	@Override
	public void randomTick(BlockState state, ServerLevel world, BlockPos pos, RandomSource random) {
		PointedDripstoneBlock.maybeTransferFluid(state, world, pos, random.nextFloat());
		if (random.nextFloat() < 0.011377778f && PointedDripstoneBlockAccessor.mmodding$isHeldByPointedDripstone(state, world, pos)) {
			this.tryGrowCustom(state, world, pos, random);
		}
	}

	public boolean canGrow(BlockState dripstoneBlockState, BlockState waterState) {
		return dripstoneBlockState.is(this.dripstoneBlock.get()) && waterState.is(Blocks.WATER) && waterState.getFluidState().isSource();
	}

	public void tryGrowCustom(BlockState state, ServerLevel world, BlockPos pos, RandomSource random) {
		BlockState firstState = world.getBlockState(pos.above(1));
		BlockState secondState = world.getBlockState(pos.above(2));

		if (this.canGrow(firstState, secondState)) {

			BlockPos blockPos = PointedDripstoneBlockAccessor.mmodding$getTipPos(state, world, pos, 7, false);
			if (blockPos != null) {

				BlockState thirdState = world.getBlockState(blockPos);
				if (PointedDripstoneBlock.canDrip(thirdState) && PointedDripstoneBlockAccessor.mmodding$canGrow(thirdState, world, blockPos)) {

					if (random.nextBoolean()) {
						this.tryGrowCustom(world, blockPos, Direction.DOWN);
					} else {
						this.tryGrowCustomStalagmite(world, blockPos);
					}
				}
			}
		}
	}

	public void tryGrowCustomStalagmite(ServerLevel world, BlockPos pos) {
		BlockPos.MutableBlockPos mutable = pos.mutable();

		for(int i = 0; i < 10; i++) {
			mutable.move(Direction.DOWN);
			BlockState state = world.getBlockState(mutable);

			if (!state.getFluidState().isEmpty()) {
				return;
			}

			if (PointedDripstoneBlockAccessor.mmodding$isTip(state, Direction.UP) && PointedDripstoneBlockAccessor.mmodding$canGrow(state, world, mutable)) {
				this.tryGrowCustom(world, mutable, Direction.UP);
				return;
			}

			if (PointedDripstoneBlockAccessor.mmodding$canPlaceAtWithDirection(world, mutable, Direction.UP) && !world.isWaterAt(mutable.below())) {
				this.tryGrowCustom(world, mutable.below(), Direction.UP);
				return;
			}

			if (!PointedDripstoneBlockAccessor.mmodding$canDripThrough(world, mutable, state)) {
				return;
			}
		}
	}

	public void tryGrowCustom(ServerLevel world, BlockPos pos, Direction direction) {
		BlockPos directionPos = pos.relative(direction);
		BlockState state = world.getBlockState(directionPos);

		if (PointedDripstoneBlockAccessor.mmodding$isTip(state, direction.getOpposite())) {
			this.growCustomMerged(state, world, directionPos);
		} else if (state.isAir() || state.is(Blocks.WATER)) {
			this.placeCustom(world, directionPos, direction, DripstoneThickness.TIP);
		}
	}

	public void placeCustom(LevelAccessor world, BlockPos pos, Direction direction, DripstoneThickness thickness) {
		BlockState state = this.defaultBlockState()
			.setValue(TIP_DIRECTION, direction)
			.setValue(THICKNESS, thickness)
			.setValue(WATERLOGGED, world.getFluidState(pos).getType() == Fluids.WATER);
		world.setBlock(pos, state, Block.UPDATE_ALL);
	}

	public void growCustomMerged(BlockState state, LevelAccessor world, BlockPos pos) {
		BlockPos firstPos = state.getValue(TIP_DIRECTION) == Direction.UP ? pos.above() : pos;
		BlockPos secondPos = state.getValue(TIP_DIRECTION) == Direction.UP ? pos : pos.below();

		this.placeCustom(world, firstPos, Direction.DOWN, DripstoneThickness.TIP_MERGE);
		this.placeCustom(world, secondPos, Direction.UP, DripstoneThickness.TIP_MERGE);
	}
}
