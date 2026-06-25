package com.mmodding.library.energy.api.storage;

import com.mmodding.library.energy.api.EnergyUnit;
import net.fabricmc.fabric.api.transfer.v1.transaction.TransactionContext;
import org.jetbrains.annotations.ApiStatus;
import org.jspecify.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

/**
 * A special-case implementation of {@link EnergyStorage}, delegating to multiple other energy storages.
 * <br>You will need to implement the behavior of the storage collection with the two following methods:
 * <ul>
 *     <li>{@link EnergyStorage#append(TransactionContext, long)}</li>
 *     <li>{@link EnergyStorage#revoke(TransactionContext, long)}</li>
 * </ul>
 * <br>An application of this is storage redirection to multiple other energy storages.
 * <br><br>A good example would be energy cables: you collect energy storages linked through the cables,
 * and make this the energy storage of the cable, and using it would spread to other storages.
 * @apiNote Collection order is preserved.
 */
public abstract class EnergyStorageCollection implements EnergyStorage {

	private final EnergyUnit unit;
	protected final List<EnergyStorage> collection;
	protected long totalCapacity;

	protected EnergyStorageCollection(EnergyUnit unit) {
		this.unit = unit;
		this.collection = new ArrayList<>();
		this.totalCapacity = 0L;
	}

	@Override
	@ApiStatus.NonExtendable
	public long push(EnergyStorage target, long amount, @Nullable TransactionContext maybeParent) {
		return EnergyStorage.super.push(target, amount, maybeParent);
	}

	@Override
	@ApiStatus.NonExtendable
	public long pull(EnergyStorage target, long amount, @Nullable TransactionContext maybeParent) {
		return EnergyStorage.super.pull(target, amount, maybeParent);
	}

	@Override
	@ApiStatus.NonExtendable
	public void pushFixed(EnergyStorage target, long amount, @Nullable TransactionContext maybeParent) {
		EnergyStorage.super.pushFixed(target, amount, maybeParent);
	}

	@Override
	@ApiStatus.NonExtendable
	public void pullFixed(EnergyStorage target, long amount, @Nullable TransactionContext maybeParent) {
		EnergyStorage.super.pullFixed(target, amount, maybeParent);
	}

	/**
	 * Pushes a new storage into this storage collection.
	 * @param storage the energy storage
	 */
	public final void pushStorage(EnergyStorage storage) {
		this.collection.add(storage);
		this.totalCapacity += EnergyUnit.convert(storage.unit(), storage.capacity(), this.unit);
	}

	@Override
	public boolean isEmpty() {
		return this.collection.stream().allMatch(EnergyStorage::isEmpty);
	}

	@Override
	public boolean isFull() {
		return this.collection.stream().allMatch(EnergyStorage::isFull);
	}

	@Override
	public long amount() {
		return this.collection.stream()
			.map(storage -> EnergyUnit.convert(storage.unit(), storage.amount(), this.unit))
			.reduce(0L, Long::sum);
	}

	@Override
	public long capacity() {
		return this.totalCapacity;
	}

	@Override
	public EnergyUnit unit() {
		return this.unit;
	}
}
