package com.mmodding.library.energy.impl;

import com.mmodding.library.energy.api.EnergyComponent;
import com.mmodding.library.energy.api.EnergyUnit;
import com.mmodding.library.energy.impl.data.EnergyData;

import java.util.function.Supplier;

public record EnergyComponentImpl(Supplier<Long> capacitySupplier, EnergyUnit unit, EnergyData data) implements EnergyComponent {

	@Override
	public long generate(long amount) {
		long generated = Math.min(this.remaining(), amount);
		this.data.setAmount(this.data.getAmount() + generated);
		return generated;
	}

	@Override
	public long consume(long amount) {
		long consumed = Math.min(this.amount(), amount);
		this.data.setAmount(this.data.getAmount() - consumed);
		return consumed;
	}

	@Override
	public boolean generateFixed(long amount) {
		if (amount <= this.remaining()) {
			this.data.setAmount(this.data.getAmount() + amount);
			return true;
		}
		else {
			return false;
		}
	}

	@Override
	public boolean consumeFixed(long amount) {
		if (amount <= this.amount()) {
			this.data.setAmount(this.data.getAmount() - amount);
			return true;
		}
		else {
			return false;
		}
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
		return this.data.getAmount();
	}

	@Override
	public long remaining() {
		return this.capacity() - this.amount();
	}

	@Override
	public long capacity() {
		return this.capacitySupplier.get();
	}
}
