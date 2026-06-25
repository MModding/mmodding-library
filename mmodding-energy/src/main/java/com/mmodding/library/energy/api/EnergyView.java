package com.mmodding.library.energy.api;

import com.mmodding.library.energy.api.access.EnergyAccess;
import org.jetbrains.annotations.ApiStatus;

/**
 * Abstraction of read-only property accesses for {@link EnergyComponent} and {@link EnergyAccess}
 */
@ApiStatus.NonExtendable
public interface EnergyView {

	/**
	 * Checks if the storage is empty.
	 * @return the result
	 */
	boolean isEmpty();

	/**
	 * Checks if the storage is full.
	 * @return the result
	 */
	boolean isFull();

	/**
	 * The energy amount of this component.
	 * @return the amount
	 */
	long amount();

	/**
	 * The remaining energy space of this component.
	 * @return the remaining amount
	 */
	long remaining();

	/**
	 * The capacity of this component.
	 * @return the capacity
	 */
	long capacity();

	/**
	 * The unit supported by this component.
	 * @return the unit
	 */
	EnergyUnit unit();
}
