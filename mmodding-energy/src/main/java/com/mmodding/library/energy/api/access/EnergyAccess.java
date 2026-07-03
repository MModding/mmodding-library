package com.mmodding.library.energy.api.access;

import com.mmodding.library.energy.api.EnergyComponent;
import com.mmodding.library.energy.api.EnergyUnit;
import com.mmodding.library.energy.api.EnergyView;
import com.mmodding.library.energy.api.access.catalog.LimitedEnergyAccess;
import com.mmodding.library.energy.api.access.catalog.SidedEnergyAccess;
import com.mmodding.library.energy.impl.access.EnergyComponentAccess;
import com.mmodding.library.energy.impl.access.InfiniteEnergyAccess;
import com.mmodding.library.energy.impl.access.VacuumEnergyAccess;
import net.fabricmc.fabric.api.transfer.v1.transaction.Transaction;
import net.fabricmc.fabric.api.transfer.v1.transaction.TransactionContext;
import net.minecraft.core.Direction;
import org.jetbrains.annotations.ApiStatus;
import org.jspecify.annotations.Nullable;

import java.util.Map;

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
	 * Creates an {@link EnergyAccess}, with a limited insertion rate and no extraction support.
	 * @param access the delegated energy access
	 * @param insertionRate the insertion rate
	 * @return the newly created access
	 */
	static EnergyAccess onlyInput(EnergyAccess access, long insertionRate) {
		return new LimitedEnergyAccess(access, insertionRate, 0L);
	}

	/**
	 * Creates an {@link EnergyAccess}, with no insertion support and a limited extraction rate.
	 * @param access the delegated energy access
	 * @param extractionRate the extraction rate
	 * @return the newly created access
	 */
	static EnergyAccess onlyOutput(EnergyAccess access, long extractionRate) {
		return new LimitedEnergyAccess(access, 0L, extractionRate);
	}

	/**
	 * Creates an {@link EnergyAccess}, with a limited insertion rate and a limited extraction rate.
	 * @param access the delegated energy access
	 * @param insertionRate the insertion rate
	 * @param extractionRate the extraction rate
	 * @return the newly created access
	 */
	static EnergyAccess limited(EnergyAccess access, long insertionRate, long extractionRate) {
		return new LimitedEnergyAccess(access, insertionRate, extractionRate);
	}

	/**
	 * Creates an {@link EnergyAccess}, providing sided access definition and passing along the current side.
	 * @param access the delegated energy access
	 * @param definition the definition
	 * @param side the current side
	 * @return the newly created access
	 */
	static EnergyAccess sided(EnergyAccess access, Map<Direction, SidedEnergyAccess.SideAccess> definition, Direction side) {
		return new SidedEnergyAccess(access, definition, side);
	}

	/**
	 * Creates an {@link EnergyAccess} to vacuum energy.
	 * @param unit the energy unit
	 * @return the newly created access
	 */
	static EnergyAccess vacuum(EnergyUnit unit) {
		return new VacuumEnergyAccess(unit);
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
	 * <br><br>This amount is adaptive: it will be clamped to what's actually being inserted into the targeted
	 * access and what's actually being extracted from the current access.
	 * @param target the targeted access
	 * @param amount the specified energy amount in this current access' energy unit
	 * @param maybeParent the possibly specified transition context parent, in case of nested transactions
	 */
	@ApiStatus.NonExtendable
	default long transferTo(@Nullable EnergyAccess target, long amount, @Nullable TransactionContext maybeParent) {
		if (target == null) return 0;
		long extracted;
		try (Transaction simulation = Transaction.openNested(maybeParent)) { extracted = this.extract(amount, simulation); }
		long computed = 0;
		try (Transaction transfer = Transaction.openNested(maybeParent)) {
			if (extracted > 0) {
				long extractedInTargetUnit = EnergyUnit.convert(extracted, this.unit(), target.unit());
				long insertedInTargetUnit = target.insert(extractedInTargetUnit, transfer);
				long inserted = EnergyUnit.convert(insertedInTargetUnit, target.unit(), this.unit());
				if (inserted > 0) {
					computed = this.extract(inserted, transfer);
					transfer.commit();
				}
			}
		}
		return computed;
	}

	/**
	 * Indicates if the insertion method will always return <code>0</code>, meaning that this access
	 * does not have to be considered for insertion.
	 * <br>In example, this is useful for energy cables.
	 * @return a boolean which indicates if insertion is supported
	 */
	boolean supportsInsertion();

	/**
	 * Inserting an amount of energy, computed from a specified amount.
	 * @param context the transaction context
	 * @param amount the energy amount
	 * @return the actually inserted amount
	 */
	long insert(long amount, TransactionContext context);

	/**
	 * Indicates if the extraction method will always return <code>0</code>, meaning that this access
	 * does not have to be considered for extraction.
	 * <br>In example, this is useful for energy cables.
	 * @return a boolean which indicates if extraction is supported
	 */
	boolean supportsExtraction();

	/**
	 * Extracting an amount of energy, computed from a specified amount.
	 * @param context the transaction context
	 * @param amount the energy amount
	 * @return the actually extracted amount
	 */
	long extract(long amount, TransactionContext context);
}
