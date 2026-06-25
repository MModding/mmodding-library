package com.mmodding.library.energy.api;

/**
 * A way to represent an {@link EnergyUnit}, with conversions to standards for interconnected
 * energy transfers.
 */
public interface EnergyUnit {

	static long convert(EnergyUnit current, long amount, EnergyUnit other) {
		return current == other ? amount : other.fromFabricEnergy(current.toFabricEnergy(amount));
	}

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
}
