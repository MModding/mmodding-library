package com.mmodding.library.energy.impl.data;

import com.mmodding.library.energy.impl.item.ItemEnergyImpl;
import net.minecraft.world.item.ItemStack;

public class ItemEnergyData extends EnergyData {

	private final ItemStack stack;

	public ItemEnergyData(ItemStack stack) {
		this.stack = stack;
	}

	@Override
	public long getAmount() {
		return this.stack.getOrDefault(ItemEnergyImpl.ENERGY_AMOUNT, 0L);
	}

	@Override
	public void setAmount(long amount) {
		this.stack.set(ItemEnergyImpl.ENERGY_AMOUNT, amount);
	}
}
