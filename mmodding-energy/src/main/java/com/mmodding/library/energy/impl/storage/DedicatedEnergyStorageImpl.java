package com.mmodding.library.energy.impl.storage;

import com.mmodding.library.energy.api.EnergyUnit;

public class DedicatedEnergyStorageImpl extends AbstractEnergyStorageImpl {

	private long energyAmount = 0;

	public DedicatedEnergyStorageImpl(long capacity, EnergyUnit unit) {
		super(capacity, unit);
	}

	public void setAmount(long energyAmount) {
		this.energyAmount = energyAmount;
	}

	@Override
	public long amount() {
		return this.energyAmount;
	}
}
