package com.mmodding.mmodding_lib.library.blocks;

import net.minecraft.block.PlantBlock;

import java.util.concurrent.atomic.AtomicBoolean;

public class CustomPlantBlock extends PlantBlock implements BlockRegistrable {

	private final AtomicBoolean registered = new AtomicBoolean(false);

	public CustomPlantBlock(Settings settings) {
		super(settings);
	}

	@Override
	public boolean isNotRegistered() {
		return !this.registered.get();
	}

	@Override
	public void setRegistered() {
		this.registered.set(true);
	}
}
