package com.mmodding.mmodding_lib.library.blocks;

import net.minecraft.block.FlowerBlock;
import net.minecraft.entity.effect.StatusEffect;

import java.util.concurrent.atomic.AtomicBoolean;

public class CustomFlowerBlock extends FlowerBlock implements BlockRegistrable {

	private final AtomicBoolean registered = new AtomicBoolean(false);

	public CustomFlowerBlock(StatusEffect suspiciousStewEffect, int effectDuration, Settings settings) {
		super(suspiciousStewEffect, effectDuration, settings);
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
