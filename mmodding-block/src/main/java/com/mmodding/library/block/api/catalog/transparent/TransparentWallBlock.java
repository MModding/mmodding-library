package com.mmodding.library.block.api.catalog.transparent;

import net.minecraft.core.Direction;
import net.minecraft.world.level.block.WallBlock;
import net.minecraft.world.level.block.state.BlockState;

public class TransparentWallBlock extends WallBlock {

	public TransparentWallBlock(Properties settings) {
		super(settings);
	}

	@Override
	public boolean skipRendering(BlockState state, BlockState stateFrom, Direction direction) {
		return stateFrom.is(this) || super.skipRendering(state, stateFrom, direction);
	}
}
