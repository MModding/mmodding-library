package com.mmodding.library.energy.api;

import org.jetbrains.annotations.ApiStatus;

@ApiStatus.NonExtendable
public interface EnergyComponent extends EnergyView {

	/**
	 * Generates an adapted amount of energy to the remaining space
	 * @param amount the amount
	 * @return the generated amount
	 */
	long generate(long amount);

	/**
	 * Consumes an adapted amount of energy to the current amount
	 * @param amount the amount
	 * @return the consumed amount
	 */
	long consume(long amount);

	/**
	 * Generates a fixed amount of energy, if there is enough remaining space.
	 * @param amount the amount
	 * @return if it had been generated
	 */
	boolean generateFixed(long amount);

	/**
	 * Consumes a fixed amount of energy, if there is enough energy amount.
	 * @param amount the amount
	 * @return if it had been consumed
	 */
	boolean consumeFixed(long amount);
}
