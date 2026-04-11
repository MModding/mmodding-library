package com.mmodding.library.block.api.catalog;

import java.util.function.BiPredicate;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.IceBlock;
import net.minecraft.world.level.block.WaterlilyBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;

public class SimpleLilyPadBlock extends WaterlilyBlock {

	private final BiPredicate<FluidState, BlockState> placementConditions;

	public SimpleLilyPadBlock(Properties settings) {
		this((fluid, floor) -> fluid.is(Fluids.WATER) || floor.getBlock() instanceof IceBlock, settings);
	}

	public SimpleLilyPadBlock(BiPredicate<FluidState, BlockState> placementConditions, Properties settings) {
		super(settings);
		this.placementConditions = placementConditions;
	}

	@Override
	protected boolean mayPlaceOn(BlockState floor, BlockGetter world, BlockPos pos) {
		return this.placementConditions.test(world.getFluidState(pos), floor) && world.getFluidState(pos.above()).isEmpty();
	}
}
