package com.mmodding.library.energy.api.item;

import com.mmodding.library.energy.api.EnergyUnit;
import com.mmodding.library.energy.api.access.EnergyAccess;
import net.minecraft.world.item.Item;

/**
 * A simple {@link Item} holding a {@link EnergyAccess}, always accessible from any context.
 */
public class SimpleEnergyItem extends Item {

	public SimpleEnergyItem(int capacity, EnergyUnit unit, Properties properties) {
		super(properties);
		ItemEnergy.defineEnergy(this, capacity, unit, (_, _, internalComponent) -> EnergyAccess.from(internalComponent));
	}
}
