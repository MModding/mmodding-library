package com.mmodding.library.block.api.catalog;

import net.minecraft.core.BlockPos;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.level.block.BedBlock;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

/**
 * A {@link BedBlock} that does not contain a {@link BlockEntity},
 * and that inverts the horizontal facing direction (in the opposite of vanilla beds),
 * to match the usual standard of horizontal facing blocks to apply horizontal models in the same way as usual.
 * @implNote <br>relies on {@link com.mmodding.library.block.mixin.BedBlockMixin} for direction inverts
 */
public class SimpleBedBlock extends BedBlock {

	public SimpleBedBlock(Properties settings) {
		super(DyeColor.WHITE, settings);
	}

	@Override
	public RenderShape getRenderShape(BlockState state) {
		return RenderShape.MODEL;
	}

	@Override
	public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
		return null;
	}
}
