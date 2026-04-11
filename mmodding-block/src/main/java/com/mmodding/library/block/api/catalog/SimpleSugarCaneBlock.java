package com.mmodding.library.block.api.catalog;

import java.util.function.BiPredicate;
import java.util.function.Predicate;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.SugarCaneBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.FluidState;

public class SimpleSugarCaneBlock extends SugarCaneBlock {

	private final Predicate<BlockState> validFloor;
	private final BiPredicate<BlockState, FluidState> validFluid;

	public SimpleSugarCaneBlock(Predicate<BlockState> validFloor, BiPredicate<BlockState, FluidState> validFluid, Properties settings) {
		super(settings);
		this.validFloor = validFloor;
		this.validFluid = validFluid;
	}

	@Override
	public boolean canSurvive(BlockState state, LevelReader world, BlockPos pos) {
		BlockState floorState = world.getBlockState(pos.below());

		if (floorState.is(this)) {
			return true;
		}

		if (this.validFloor.test(floorState)) {
			for (Direction direction : Direction.Plane.HORIZONTAL) {
				BlockState blockState = world.getBlockState(pos.below().relative(direction));
				FluidState fluidState = world.getFluidState(pos.below().relative(direction));

				if (this.validFluid.test(blockState, fluidState)) {
					return true;
				}
			}
		}

		return false;
	}
}
