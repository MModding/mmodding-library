package com.mmodding.library.energy.api.storage;

import com.mmodding.library.energy.api.EnergyUnit;
import net.fabricmc.fabric.api.transfer.v1.storage.Storage;
import net.fabricmc.fabric.api.transfer.v1.transaction.Transaction;
import net.fabricmc.fabric.api.transfer.v1.transaction.TransactionContext;
import org.jetbrains.annotations.ApiStatus;

/**
 * An energy storage.
 * @implNote we are not using {@link Storage}, because the "kind" of energy does not vary (unlike fluids or items).
 * <br>And we are not using the energy units as the "kinds", because this API aims to allow conversion between
 * multiple units.
 */
@ApiStatus.NonExtendable
public interface EnergyStorage {

	/**
	 * Tries appending the specified amount of energy.
	 * @apiNote It clamps the value to the energy storage capacity.
	 */
	void append(TransactionContext context, long amount);

	/**
	 * Tries revoking the specified amount of energy.
	 * @apiNote It clamps the value to the current possessed amount.
	 */
	void revoke(TransactionContext context, long amount);

	/**
	 * Provides a specified amount of energy to a targeted {@link EnergyStorage}.
	 * @param target the targeted storage
	 * @param amount the specified energy amount in this current storage's energy unit
	 */
	default void provide(EnergyStorage target, long amount) {
		try (Transaction transaction = Transaction.openOuter()) {
			if (this.amount() >= amount) {
				this.revoke(transaction, amount);
				long converted = this.unit().convertTo(target.unit(), amount);
				if (target.capacity() - target.amount() >= converted) {
					target.append(transaction, converted);
					transaction.commit();
				}
			}
		}
	}

	/**
	 * Pulls a specified amount of energy from a targeted {@link EnergyStorage}.
	 * @param target the targeted storage
	 * @param amount the specified energy amount in this current storage's energy unit
	 */
	default void pull(EnergyStorage target, long amount) {
		try (Transaction transaction = Transaction.openOuter()) {
			long converted = this.unit().convertTo(target.unit(), amount);
			if (target.amount() >= converted) {
				target.revoke(transaction, converted);
				if (this.capacity() - this.amount() >= amount) {
					this.append(transaction, amount);
					transaction.commit();
				}
			}
		}
	}

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
	 * This amount of energy in this storage.
	 * @return the energy amount
	 */
	long amount();

	/**
	 * The energy capacity of this storage.
	 * @return the energy capacity
	 */
	long capacity();

	/**
	 * The energy unit of this storage.
	 * @return the energy unit
	 */
	EnergyUnit unit();
}
