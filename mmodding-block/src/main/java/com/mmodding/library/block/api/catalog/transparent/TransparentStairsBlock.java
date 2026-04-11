package com.mmodding.library.block.api.catalog.transparent;

import net.minecraft.core.Direction;
import net.minecraft.world.level.block.StairBlock;
import net.minecraft.world.level.block.state.BlockState;

public class TransparentStairsBlock extends StairBlock {

	public TransparentStairsBlock(BlockState baseBlockState, Properties settings) {
		super(baseBlockState, settings);
	}

	@Override
	public boolean skipRendering(BlockState state, BlockState stateFrom, Direction direction) {
		return stateFrom.is(this) || super.skipRendering(state, stateFrom, direction);
	}
}
