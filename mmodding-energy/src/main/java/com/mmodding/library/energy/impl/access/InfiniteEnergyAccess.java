package com.mmodding.library.energy.impl.access;

import com.mmodding.library.energy.api.EnergyUnit;
import com.mmodding.library.energy.api.access.EnergyAccess;
import net.fabricmc.fabric.api.transfer.v1.transaction.TransactionContext;

public class InfiniteEnergyAccess implements EnergyAccess {

	private final EnergyUnit unit;

	public InfiniteEnergyAccess(EnergyUnit unit) {
		this.unit = unit;
	}

	@Override
	public boolean isEmpty() {
		return false;
	}

	@Override
	public boolean isFull() {
		return true;
	}

	@Override
	public long amount() {
		return Long.MAX_VALUE;
	}

	@Override
	public long remaining() {
		return 0;
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
		return false;
	}

	@Override
	public long insert(long amount, TransactionContext context) {
		return 0L;
	}

	@Override
	public boolean supportsExtraction() {
		return true;
	}

	@Override
	public long extract(long amount, TransactionContext context) {
		return Long.MAX_VALUE;
	}
}
