package com.mmodding.library.integration.reborn_energy;

import com.mmodding.library.energy.api.EnergyUnit;
import com.mmodding.library.energy.api.access.EnergyAccess;
import com.mmodding.library.energy.api.convention.FabricEnergy;
import net.fabricmc.fabric.api.transfer.v1.transaction.TransactionContext;
import team.reborn.energy.api.EnergyStorage;

public class CompatEnergyStorage implements EnergyStorage {

	private final EnergyAccess delegate;

	public CompatEnergyStorage(EnergyAccess delegate) {
		this.delegate = delegate;
	}

	@Override
	public boolean supportsInsertion() {
		return this.delegate.supportsInsertion();
	}

	@Override
	public long insert(long maxAmount, TransactionContext transaction) {
		long toDelegate = EnergyUnit.convert(maxAmount, FabricEnergy.UNIT, this.delegate.unit());
		long insertedToDelegate = this.delegate.insert(toDelegate, transaction);
		return EnergyUnit.convert(insertedToDelegate, this.delegate.unit(), FabricEnergy.UNIT);
	}

	@Override
	public boolean supportsExtraction() {
		return this.delegate.supportsExtraction();
	}

	@Override
	public long extract(long maxAmount, TransactionContext transaction) {
		long toDelegate = EnergyUnit.convert(maxAmount, FabricEnergy.UNIT, this.delegate.unit());
		long extractedFromDelegate = this.delegate.extract(toDelegate, transaction);
		return EnergyUnit.convert(extractedFromDelegate, this.delegate.unit(), FabricEnergy.UNIT);
	}

	@Override
	public long getAmount() {
		return EnergyUnit.convert(this.delegate.amount(), this.delegate.unit(), FabricEnergy.UNIT);
	}

	@Override
	public long getCapacity() {
		return EnergyUnit.convert(this.delegate.capacity(), this.delegate.unit(), FabricEnergy.UNIT);
	}
}
