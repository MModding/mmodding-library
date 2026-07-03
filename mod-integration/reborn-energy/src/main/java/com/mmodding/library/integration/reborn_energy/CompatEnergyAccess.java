package com.mmodding.library.integration.reborn_energy;

import com.mmodding.library.energy.api.EnergyUnit;
import com.mmodding.library.energy.api.access.EnergyAccess;
import com.mmodding.library.energy.api.convention.FabricEnergy;
import net.fabricmc.fabric.api.transfer.v1.transaction.TransactionContext;
import team.reborn.energy.api.EnergyStorage;

public class CompatEnergyAccess implements EnergyAccess {

	private final EnergyStorage delegate;

	public CompatEnergyAccess(EnergyStorage delegate) {
		this.delegate = delegate;
	}

	@Override
	public boolean supportsInsertion() {
		return this.delegate.supportsInsertion();
	}

	@Override
	public long insert(long amount, TransactionContext context) {
		return this.delegate.insert(amount, context);
	}

	@Override
	public boolean supportsExtraction() {
		return this.delegate.supportsExtraction();
	}

	@Override
	public long extract(long amount, TransactionContext context) {
		return this.delegate.extract(amount, context);
	}

	@Override
	public boolean isEmpty() {
		return this.amount() == 0;
	}

	@Override
	public boolean isFull() {
		return this.amount() == this.capacity();
	}

	@Override
	public long amount() {
		return this.delegate.getAmount();
	}

	@Override
	public long remaining() {
		return this.capacity() - this.amount();
	}

	@Override
	public long capacity() {
		return this.delegate.getCapacity();
	}

	@Override
	public EnergyUnit unit() {
		return FabricEnergy.UNIT;
	}
}
