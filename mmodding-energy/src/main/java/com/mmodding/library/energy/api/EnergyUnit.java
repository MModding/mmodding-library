package com.mmodding.library.energy.api;

import org.jetbrains.annotations.ApiStatus;

/**
 * A way to represent an {@link EnergyUnit}, with conversions to standards for interconnected
 * energy transfers.
 */
public interface EnergyUnit {

	/**
	 * Converts a specified amount of energy from this unit into Fabric Energy (E).
	 * @param amount the amount of energy
	 * @return the converted amount
	 */
	long toFabricEnergy(long amount);

	/**
	 * Converts a specified amount of Fabric Energy (E) into this unit.
	 * @param amount the amount of energy
	 * @return the converted amount
	 */
	long fromFabricEnergy(long amount);

	@ApiStatus.NonExtendable
	default long convertTo(EnergyUnit another, long amount) {
		return this == another ? amount : another.fromFabricEnergy(this.toFabricEnergy(amount));
	}
}
