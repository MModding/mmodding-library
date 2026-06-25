package com.mmodding.library.energy.impl.data;

public class DedicatedEnergyData extends EnergyData {

	private long energyAmount;

	public DedicatedEnergyData(long energyAmount) {
		this.energyAmount = energyAmount;
	}

	@Override
	public long getAmount() {
		return this.energyAmount;
	}

	@Override
	public void setAmount(long amount) {
		this.energyAmount = amount;
	}
}
