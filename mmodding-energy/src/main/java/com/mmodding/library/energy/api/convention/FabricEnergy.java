package com.mmodding.library.energy.api.convention;

import com.mmodding.library.energy.api.EnergyUnit;

/**
 * The standard Fabric Energy unit (E). It was brought along by Tech Reborn.
 */
public final class FabricEnergy implements EnergyUnit {

	public static final EnergyUnit UNIT = new FabricEnergy();

	private FabricEnergy() {}

	@Override
	public long toFabricEnergy(long amount) {
		return amount;
	}

	@Override
	public long fromFabricEnergy(long amount) {
		return amount;
	}
}
