package com.mmodding.mmodding_lib.library.blockentities;

import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.util.math.BlockPos;

import java.util.concurrent.atomic.AtomicBoolean;

public class CustomBlockEntity extends BlockEntity implements BlockEntityRegisterable {
	private final AtomicBoolean registered = new AtomicBoolean(false);

	public CustomBlockEntity(BlockEntityType<? extends BlockEntity> blockEntityType, BlockPos pos, BlockState state) {
		super(blockEntityType, pos, state);
	}

	public boolean isNotRegistered() {
		return !this.registered.get();
	}

	@Override
	public void setRegistered() {
		this.registered.set(true);
	}
}
