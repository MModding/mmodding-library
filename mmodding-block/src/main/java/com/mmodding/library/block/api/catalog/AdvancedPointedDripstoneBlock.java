package com.mmodding.library.block.api.catalog;

import com.mmodding.library.block.mixin.PointedDripstoneBlockAccessor;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.PointedDripstoneBlock;
import net.minecraft.block.enums.Thickness;
import net.minecraft.fluid.Fluids;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.WorldAccess;

import java.util.function.Supplier;

public class AdvancedPointedDripstoneBlock extends PointedDripstoneBlock {

	private final Supplier<Block> dripstoneBlock;

	public AdvancedPointedDripstoneBlock(Supplier<Block> dripstoneBlock, Settings settings) {
		super(settings);
		this.dripstoneBlock = dripstoneBlock;
	}

	@Override
	public void randomTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
		PointedDripstoneBlock.dripTick(state, world, pos, random.nextFloat());
		if (random.nextFloat() < 0.011377778f && PointedDripstoneBlockAccessor.mmodding$isHeldByPointedDripstone(state, world, pos)) {
			this.tryGrowCustom(state, world, pos, random);
		}
	}

	public boolean canGrow(BlockState dripstoneBlockState, BlockState waterState) {
		return dripstoneBlockState.isOf(this.dripstoneBlock.get()) && waterState.isOf(Blocks.WATER) && waterState.getFluidState().isStill();
	}

	public void tryGrowCustom(BlockState state, ServerWorld world, BlockPos pos, Random random) {
		BlockState firstState = world.getBlockState(pos.up(1));
		BlockState secondState = world.getBlockState(pos.up(2));

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

	public void tryGrowCustomStalagmite(ServerWorld world, BlockPos pos) {
		BlockPos.Mutable mutable = pos.mutableCopy();

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

			if (PointedDripstoneBlockAccessor.mmodding$canPlaceAtWithDirection(world, mutable, Direction.UP) && !world.isWater(mutable.down())) {
				this.tryGrowCustom(world, mutable.down(), Direction.UP);
				return;
			}

			if (!PointedDripstoneBlockAccessor.mmodding$canDripThrough(world, mutable, state)) {
				return;
			}
		}
	}

	public void tryGrowCustom(ServerWorld world, BlockPos pos, Direction direction) {
		BlockPos directionPos = pos.offset(direction);
		BlockState state = world.getBlockState(directionPos);

		if (PointedDripstoneBlockAccessor.mmodding$isTip(state, direction.getOpposite())) {
			this.growCustomMerged(state, world, directionPos);
		} else if (state.isAir() || state.isOf(Blocks.WATER)) {
			this.placeCustom(world, directionPos, direction, Thickness.TIP);
		}
	}

	public void placeCustom(WorldAccess world, BlockPos pos, Direction direction, Thickness thickness) {
		BlockState state = this.getDefaultState()
			.with(VERTICAL_DIRECTION, direction)
			.with(THICKNESS, thickness)
			.with(WATERLOGGED, world.getFluidState(pos).getFluid() == Fluids.WATER);
		world.setBlockState(pos, state, Block.NOTIFY_ALL);
	}

	public void growCustomMerged(BlockState state, WorldAccess world, BlockPos pos) {
		BlockPos firstPos = state.get(VERTICAL_DIRECTION) == Direction.UP ? pos.up() : pos;
		BlockPos secondPos = state.get(VERTICAL_DIRECTION) == Direction.UP ? pos : pos.down();

		this.placeCustom(world, firstPos, Direction.DOWN, Thickness.TIP_MERGE);
		this.placeCustom(world, secondPos, Direction.UP, Thickness.TIP_MERGE);
	}
}
