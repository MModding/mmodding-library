package com.mmodding.mmodding_lib.library.blockentities;

import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.util.math.BlockPos;

public class CustomBlockEntity extends BlockEntity {

	public CustomBlockEntity(BlockEntityType<? extends BlockEntity> blockEntityType, BlockPos pos, BlockState state) {
		super(blockEntityType, pos, state);
	}
}
