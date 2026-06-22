package com.mmodding.library.energy.impl.storage;

import com.mmodding.library.energy.api.EnergyUnit;
import com.mmodding.library.energy.api.storage.EnergyStorage;
import net.fabricmc.fabric.api.transfer.v1.transaction.TransactionContext;
import net.fabricmc.fabric.api.transfer.v1.transaction.base.SnapshotParticipant;

public abstract class AbstractEnergyStorageImpl extends SnapshotParticipant<Long> implements EnergyStorage {

	private final long capacity;
	private final EnergyUnit unit;

	public AbstractEnergyStorageImpl(long capacity, EnergyUnit unit) {
		this.capacity = capacity;
		this.unit = unit;
	}

	public abstract void setAmount(long amount);

	@Override
	public void append(TransactionContext context, long amount) {
		updateSnapshots(context);
		this.setAmount(this.amount() + Math.min(this.capacity - this.amount(), amount));
	}

	@Override
	public void revoke(TransactionContext context, long amount) {
		updateSnapshots(context);
		this.setAmount(this.amount() - Math.min(this.amount(), amount));
	}

	@Override
	public boolean isEmpty() {
		return this.amount() == 0;
	}

	@Override
	public boolean isFull() {
		return this.amount() == this.capacity;
	}

	@Override
	public long capacity() {
		return this.capacity;
	}

	@Override
	public EnergyUnit unit() {
		return this.unit;
	}

	@Override
	protected Long createSnapshot() {
		return this.amount();
	}

	@Override
	protected void readSnapshot(Long snapshot) {
		this.setAmount(snapshot);
	}
}
