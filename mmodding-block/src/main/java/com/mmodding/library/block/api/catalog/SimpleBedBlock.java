package com.mmodding.library.block.api.catalog;

import net.minecraft.block.BedBlock;
import net.minecraft.block.BlockRenderType;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.util.DyeColor;
import net.minecraft.util.math.BlockPos;

/**
 * A {@link BedBlock} that does not contain a {@link BlockEntity}.
 */
public class SimpleBedBlock extends BedBlock {

	public SimpleBedBlock(Settings settings) {
		super(DyeColor.WHITE, settings);
	}

	@Override
	public BlockRenderType getRenderType(BlockState state) {
		return BlockRenderType.MODEL;
	}

	@Override
	public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
		return null;
	}
}
