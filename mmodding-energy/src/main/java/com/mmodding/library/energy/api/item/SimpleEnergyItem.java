package com.mmodding.library.energy.api.item;

import com.mmodding.library.energy.api.EnergyUnit;
import com.mmodding.library.energy.api.storage.EnergyStorage;
import net.minecraft.world.item.Item;

/**
 * A simple {@link Item} holding a {@link EnergyStorage}, always accessible from any context.
 */
public class SimpleEnergyItem extends Item {

	public SimpleEnergyItem(int capacity, EnergyUnit unit, Properties properties) {
		super(properties);
		ItemEnergy.defineEnergyStorage(this, capacity, unit, (_, internalStorage) -> internalStorage);
	}
}
