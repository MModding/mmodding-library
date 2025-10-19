package com.mmodding.library.block.api.catalog;

import net.minecraft.block.BlockState;
import net.minecraft.block.IceBlock;
import net.minecraft.block.LilyPadBlock;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.BlockView;

import java.util.function.BiPredicate;

public class SimpleLilyPadBlock extends LilyPadBlock {

	private final BiPredicate<FluidState, BlockState> placementConditions;

	public SimpleLilyPadBlock(Settings settings) {
		this((fluid, floor) -> fluid.isOf(Fluids.WATER) || floor.getBlock() instanceof IceBlock, settings);
	}

	public SimpleLilyPadBlock(BiPredicate<FluidState, BlockState> placementConditions, Settings settings) {
		super(settings);
		this.placementConditions = placementConditions;
	}

	@Override
	protected boolean canPlantOnTop(BlockState floor, BlockView world, BlockPos pos) {
		return this.placementConditions.test(world.getFluidState(pos), floor) && world.getFluidState(pos.up()).isEmpty();
	}
}
