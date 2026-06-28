package com.mmodding.library.energy.api.access;

import com.mmodding.library.energy.api.EnergyComponent;
import com.mmodding.library.energy.api.EnergyUnit;
import com.mmodding.library.energy.api.EnergyView;
import com.mmodding.library.energy.impl.access.EnergyComponentAccess;
import com.mmodding.library.energy.impl.access.InfiniteEnergyAccess;
import net.fabricmc.fabric.api.transfer.v1.transaction.Transaction;
import net.fabricmc.fabric.api.transfer.v1.transaction.TransactionContext;
import org.jetbrains.annotations.ApiStatus;
import org.jspecify.annotations.Nullable;

/**
 * Access to energy. It has the ability to do transfer operations with others accesses.
 */
public interface EnergyAccess extends EnergyView {

	/**
	 * Creates an {@link EnergyAccess} encapsulating the given {@link EnergyComponent}.
	 * @param component the component
	 * @return the newly created access
	 */
	static EnergyAccess from(EnergyComponent component) {
		return new EnergyComponentAccess(component);
	}

	/**
	 * Creates an {@link EnergyAccess} to infinite energy.
	 * @param unit the energy unit
	 * @return the newly created access
	 */
	static EnergyAccess infinite(EnergyUnit unit) {
		return new InfiniteEnergyAccess(unit);
	}

	/**
	 * Pushes a specified amount of energy to a targeted {@link EnergyAccess}.
	 * <br><br>This amount is adaptive: the amount is capped to the remaining energy
	 * amount of the source and to the remaining space of the target before
	 * being transferred.
	 * @param target the targeted access
	 * @param amount the specified energy amount in this current access' energy unit
	 * @param maybeParent the possibly specified transition context parent, in case of nested transactions
	 */
	@ApiStatus.NonExtendable
	default long transferTo(@Nullable EnergyAccess target, long amount, @Nullable TransactionContext maybeParent) {
		if (target == null) return 0;
		long sourceClamped = Math.min(this.amount(), amount);
		long targetClamped = Math.min(EnergyUnit.convert(target.unit(), target.remaining(), this.unit()), sourceClamped);
		this.transferToFixed(target, targetClamped, maybeParent);
		return targetClamped;
	}

	/**
	 * Pushes a specified amount of energy to a targeted {@link EnergyAccess}.
	 * <br><br>This amount is not adaptive: if the source does not have enough
	 * energy amount or that the target does not have enough remaining space,
	 * the transaction is canceled.
	 * @param target the targeted access
	 * @param amount the specified energy amount in this current access' energy unit
	 * @param maybeParent the possibly specified transition context parent, in case of nested transactions
	 */
	@ApiStatus.NonExtendable
	default void transferToFixed(@Nullable EnergyAccess target, long amount, @Nullable TransactionContext maybeParent) {
		if (target == null) return;
		try (Transaction transaction = Transaction.openNested(maybeParent)) {
			if (this.amount() >= amount) {
				this.revoke(transaction, amount);
				long converted = EnergyUnit.convert(this.unit(), amount, target.unit());
				if (target.remaining() >= converted) {
					target.append(transaction, converted);
					transaction.commit();
				}
			}
		}
	}

	/**
	 * <b>Should only be used by method overriders.</b>
	 * <br>Appending a given amount of energy. Assumes it is capable of receiving it.
	 * @param context the transaction context
	 * @param amount the amount
	 */
	@ApiStatus.OverrideOnly
	void append(TransactionContext context, long amount);

	/**
	 * <b>Should only be used by method overriders.</b>
	 * <br>Revoking a given amount of energy. Assumes it is capable of removing it.
	 * @param context the transaction context
	 * @param amount the amount
	 */
	@ApiStatus.OverrideOnly
	void revoke(TransactionContext context, long amount);
}
