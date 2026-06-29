package com.mmodding.library.energy.api;

/**
 * A way to represent an {@link EnergyUnit}, with conversions to standards for interconnected
 * energy transfers.
 */
public interface EnergyUnit {

	/**
	 * A method to convert an energy amount from an energy unit, to another.
	 * @param amount the specified amount
	 * @param from the sourcing energy unit
	 * @param to the targeted energy unit
	 * @return the converted amount
	 */
	static long convert(long amount, EnergyUnit from, EnergyUnit to) {
		return from == to ? amount : to.fromFabricEnergy(from.toFabricEnergy(amount));
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
