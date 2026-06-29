package com.mmodding.library.energy.api.access.catalog;

import com.mmodding.library.energy.api.EnergyUnit;
import com.mmodding.library.energy.api.access.EnergyAccess;
import net.fabricmc.fabric.api.transfer.v1.storage.StoragePreconditions;
import net.fabricmc.fabric.api.transfer.v1.transaction.TransactionContext;

/**
 * An {@link EnergyAccess} delegating to another, with a defined insertion rate and a defined extraction rate.
 */
public class LimitedEnergyAccess implements EnergyAccess {

	private final EnergyAccess delegate;
	private final long insertionRate;
	private final long extractionRate;

	public LimitedEnergyAccess(EnergyAccess delegate, long insertionRate, long extractionRate) {
		StoragePreconditions.notNegative(insertionRate);
		StoragePreconditions.notNegative(extractionRate);

		this.delegate = delegate;
		this.insertionRate = insertionRate;
		this.extractionRate = extractionRate;
	}

	@Override
	public boolean supportsInsertion() {
		return this.delegate.supportsInsertion() && this.insertionRate > 0;
	}

	@Override
	public long insert(long amount, TransactionContext context) {
		StoragePreconditions.notNegative(amount);

		return this.delegate.insert(Math.min(this.insertionRate, amount), context);
	}

	@Override
	public boolean supportsExtraction() {
		return this.delegate.supportsExtraction() && this.extractionRate > 0;
	}

	@Override
	public long extract(long amount, TransactionContext context) {
		StoragePreconditions.notNegative(amount);

		return this.delegate.extract(Math.min(this.extractionRate, amount), context);
	}

	@Override
	public boolean isEmpty() {
		return this.delegate.isEmpty();
	}

	@Override
	public boolean isFull() {
		return this.delegate.isFull();
	}

	@Override
	public long amount() {
		return this.delegate.amount();
	}

	@Override
	public long remaining() {
		return this.delegate.remaining();
	}

	@Override
	public long capacity() {
		return this.delegate.capacity();
	}

	@Override
	public EnergyUnit unit() {
		return this.delegate.unit();
	}
}
