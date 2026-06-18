package com.mmodding.library.energy.api.block;

import com.mmodding.library.energy.api.EnergyUnit;
import com.mmodding.library.energy.api.storage.EnergyStorage;
import net.minecraft.world.level.block.Block;

/**
 * A simple {@link Block} holding a {@link EnergyStorage}, always accessible from any context.
 */
public class SimpleEnergyBlock extends Block {

	public SimpleEnergyBlock(int capacity, EnergyUnit unit, Properties properties) {
		super(properties);
		BlockEnergy.defineEnergyStorage(this, capacity, unit, (_, _, _, internalStorage) -> internalStorage);
	}
}
