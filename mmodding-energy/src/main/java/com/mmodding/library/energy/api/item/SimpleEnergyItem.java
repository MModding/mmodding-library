package com.mmodding.library.energy.api.item;

import com.mmodding.library.energy.api.EnergyUnit;
import net.minecraft.world.item.Item;

public class SimpleEnergyItem extends Item {

	public SimpleEnergyItem(int capacity, EnergyUnit unit, Properties properties) {
		super(properties);
		ItemEnergy.defineEnergyStorage(this, capacity, unit, (_, internalStorage) -> internalStorage);
	}
}
