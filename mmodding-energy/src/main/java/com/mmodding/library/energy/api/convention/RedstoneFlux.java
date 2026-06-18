package com.mmodding.library.energy.api.convention;

import com.mmodding.library.energy.api.EnergyUnit;

/**
 * RedstoneFlux (RF) energy unit. Brought along by the MinecraftForge ecosystem, continued by NeoForge.
 * It was renamed to Forge Energy (FE); this class is not named this way to prevent mod developers from
 * thinking of a sign of multi-loader support.
 */
public final class RedstoneFlux implements EnergyUnit {

	public static final EnergyUnit UNIT = new RedstoneFlux();

	private RedstoneFlux() {}

	@Override
	public long toFabricEnergy(long amount) {
		return Math.floorDiv(amount, 4);
	}

	@Override
	public long fromFabricEnergy(long amount) {
		return 4 * amount;
	}
}
