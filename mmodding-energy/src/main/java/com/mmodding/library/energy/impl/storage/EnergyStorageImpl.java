package com.mmodding.library.energy.impl.storage;

import com.mmodding.library.energy.api.EnergyUnit;
import com.mmodding.library.energy.api.storage.EnergyStorage;
import net.fabricmc.fabric.api.transfer.v1.transaction.TransactionContext;
import net.fabricmc.fabric.api.transfer.v1.transaction.base.SnapshotParticipant;

public class EnergyStorageImpl extends SnapshotParticipant<Long> implements EnergyStorage {

	private final long capacity;
	private final EnergyUnit unit;

	private long energyAmount = 0;

	public EnergyStorageImpl(long capacity, EnergyUnit unit) {
		this.capacity = capacity;
		this.unit = unit;
	}

	public void setAmount(long energyAmount) {
		this.energyAmount = energyAmount;
	}

	@Override
	public void append(TransactionContext context, long amount) {
		updateSnapshots(context);
		this.energyAmount += Math.min(this.capacity - this.energyAmount, amount);
	}

	@Override
	public void revoke(TransactionContext context, long amount) {
		updateSnapshots(context);
		this.energyAmount -= Math.max(this.energyAmount, amount);
	}

	@Override
	public boolean isEmpty() {
		return this.energyAmount == 0;
	}

	@Override
	public boolean isFull() {
		return this.energyAmount == this.capacity;
	}

	@Override
	public long amount() {
		return this.energyAmount;
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
		return this.energyAmount;
	}

	@Override
	protected void readSnapshot(Long snapshot) {
		this.energyAmount = snapshot;
	}
}
