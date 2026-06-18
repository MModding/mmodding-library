package com.mmodding.library.energy.impl.storage;

import com.mmodding.library.energy.api.EnergyUnit;
import com.mmodding.library.energy.impl.item.ItemEnergyImpl;
import net.minecraft.world.item.ItemStack;

public class ItemEnergyStorageImpl extends AbstractEnergyStorageImpl {

	private final ItemStack stack;

	public ItemEnergyStorageImpl(ItemStack stack, long capacity, EnergyUnit unit) {
		super(capacity, unit);
		this.stack = stack;
	}

	@Override
	public void setAmount(long amount) {
		this.stack.set(ItemEnergyImpl.ENERGY_AMOUNT, amount);
	}

	@Override
	public long amount() {
		return this.stack.getOrDefault(ItemEnergyImpl.ENERGY_AMOUNT, 0L);
	}
}
