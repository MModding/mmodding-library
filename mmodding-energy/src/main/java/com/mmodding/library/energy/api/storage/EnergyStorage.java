package com.mmodding.library.energy.api.storage;

import com.mmodding.library.energy.api.EnergyUnit;
import net.fabricmc.fabric.api.transfer.v1.storage.Storage;
import net.fabricmc.fabric.api.transfer.v1.transaction.Transaction;
import net.fabricmc.fabric.api.transfer.v1.transaction.TransactionContext;
import org.jetbrains.annotations.ApiStatus;
import org.jspecify.annotations.Nullable;

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
	 * Pushes a specified amount of energy to a targeted {@link EnergyStorage}.
	 * <br><br>This amount is adaptive: the amount is capped to the remaining energy
	 * amount of the source and to the remaining storage space of the target before
	 * being transferred.
	 * @param target the targeted storage
	 * @param amount the specified energy amount in this current storage's energy unit
	 * @param maybeParent the possibly specified transition context parent, in case of nested transactions
	 */
	default long push(EnergyStorage target, long amount, @Nullable TransactionContext maybeParent) {
		long sourceClamped = Math.min(this.amount(), amount);
		long targetClamped = Math.min(EnergyUnit.convert(target.unit(), target.capacity() - target.amount(), this.unit()), sourceClamped);
		this.pushFixed(target, targetClamped, maybeParent);
		return targetClamped;
	}

	/**
	 * Pulls a specified amount of energy from a targeted {@link EnergyStorage}.
	 * <br><br>This amount is adaptive: the amount is capped to remaining storage space
	 * amount of the source and to the remaining energy amount of the target before
	 * being transferred.
	 * @param target the targeted storage
	 * @param amount the specified energy amount in this current storage's energy unit
	 * @param maybeParent the possibly specified transition context parent, in case of nested transactions
	 */
	default long pull(EnergyStorage target, long amount, @Nullable TransactionContext maybeParent) {
		long targetClamped = Math.min(EnergyUnit.convert(target.unit(), target.amount(), this.unit()), amount);
		long sourceClamped = Math.min(this.capacity() - this.amount(), targetClamped);
		this.pullFixed(target, sourceClamped, maybeParent);
		return sourceClamped;
	}

	/**
	 * Pushes a specified amount of energy to a targeted {@link EnergyStorage}.
	 * <br><br>This amount is not adaptive: if the source does not have enough
	 * energy amount or that the target does not have enough remaining storage space,
	 * the transaction is canceled.
	 * @param target the targeted storage
	 * @param amount the specified energy amount in this current storage's energy unit
	 * @param maybeParent the possibly specified transition context parent, in case of nested transactions
	 */
	default void pushFixed(EnergyStorage target, long amount, @Nullable TransactionContext maybeParent) {
		try (Transaction transaction = Transaction.openNested(maybeParent)) {
			if (this.amount() >= amount) {
				this.revoke(transaction, amount);
				long converted = EnergyUnit.convert(this.unit(), amount, target.unit());
				if (target.capacity() - target.amount() >= converted) {
					target.append(transaction, converted);
					transaction.commit();
				}
			}
		}
	}

	/**
	 * Pulls a specified amount of energy from a targeted {@link EnergyStorage}.
	 * <br><br>This amount is not adaptive: if the source does not have enough
	 * remaining storage space or that the target does not have enough energy amount,
	 * the transaction is canceled.
	 * @param target the targeted storage
	 * @param amount the specified energy amount in this current storage's energy unit
	 * @param maybeParent the possibly specified transition context parent, in case of nested transactions
	 */
	default void pullFixed(EnergyStorage target, long amount, @Nullable TransactionContext maybeParent) {
		try (Transaction transaction = Transaction.openNested(maybeParent)) {
			long converted = EnergyUnit.convert(this.unit(), amount, target.unit());
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
