package com.mmodding.library.block.api.catalog;

import net.minecraft.block.BlockState;
import net.minecraft.block.SugarCaneBlock;
import net.minecraft.fluid.FluidState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.WorldView;

import java.util.function.BiPredicate;
import java.util.function.Predicate;

public class SimpleSugarCaneBlock extends SugarCaneBlock {

	private final Predicate<BlockState> validFloor;
	private final BiPredicate<BlockState, FluidState> validFluid;

	public SimpleSugarCaneBlock(Predicate<BlockState> validFloor, BiPredicate<BlockState, FluidState> validFluid, Settings settings) {
		super(settings);
		this.validFloor = validFloor;
		this.validFluid = validFluid;
	}

	@Override
	public boolean canPlaceAt(BlockState state, WorldView world, BlockPos pos) {
		BlockState floorState = world.getBlockState(pos.down());

		if (floorState.isOf(this)) {
			return true;
		}

		if (this.validFloor.test(floorState)) {
			for (Direction direction : Direction.Type.HORIZONTAL) {
				BlockState blockState = world.getBlockState(pos.down().offset(direction));
				FluidState fluidState = world.getFluidState(pos.down().offset(direction));

				if (this.validFluid.test(blockState, fluidState)) {
					return true;
				}
			}
		}

		return false;
	}
}
