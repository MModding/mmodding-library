package com.mmodding.library.energy.impl.access;

import com.mmodding.library.energy.api.EnergyUnit;
import com.mmodding.library.energy.api.access.EnergyAccess;
import net.fabricmc.fabric.api.transfer.v1.transaction.TransactionContext;

public class VacuumEnergyAccess implements EnergyAccess {

	private final EnergyUnit unit;

	public VacuumEnergyAccess(EnergyUnit unit) {
		this.unit = unit;
	}

	@Override
	public boolean isEmpty() {
		return true;
	}

	@Override
	public boolean isFull() {
		return false;
	}

	@Override
	public long amount() {
		return 0L;
	}

	@Override
	public long remaining() {
		return Long.MAX_VALUE;
	}

	@Override
	public long capacity() {
		return Long.MAX_VALUE;
	}

	@Override
	public EnergyUnit unit() {
		return this.unit;
	}

	@Override
	public boolean supportsInsertion() {
		return true;
	}

	@Override
	public long insert(long amount, TransactionContext context) {
		return Long.MAX_VALUE;
	}

	@Override
	public boolean supportsExtraction() {
		return false;
	}

	@Override
	public long extract(long amount, TransactionContext context) {
		return 0L;
	}
}
