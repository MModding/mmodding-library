package com.mmodding.library.woodset.impl;

import com.mmodding.library.woodset.api.WoodSetSettings;
import net.minecraft.world.level.block.Block;

import java.util.function.Supplier;

public class WoodSetSettingsImpl implements WoodSetSettings {

	private final boolean burnable;
	private final LogDisplay logDisplay;
	private final Supplier<Block> hangingSignChain;

	public WoodSetSettingsImpl(boolean burnable, LogDisplay logDisplay, Supplier<Block> hangingSignChain) {
		this.burnable = burnable;
		this.logDisplay = logDisplay;
		this.hangingSignChain = hangingSignChain;
	}

	@Override
	public boolean isBurnable() {
		return this.burnable;
	}

	@Override
	public LogDisplay getLogDisplay() {
		return this.logDisplay;
	}

	@Override
	public Block getHangingSignChain() {
		return this.hangingSignChain.get();
	}
}
