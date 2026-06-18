package com.mmodding.library.energy.api.block;

import com.mmodding.library.energy.api.EnergyUnit;
import com.mmodding.library.energy.api.storage.EnergyStorage;
import net.minecraft.world.level.block.Block;

/**
 * A simple {@link Block} holding a single {@link EnergyStorage} defined by a given energy capacity and energy unit.
 */
public class SimpleEnergyBlock extends Block {

	public SimpleEnergyBlock(int capacity, EnergyUnit unit, Properties properties) {
		super(properties);
		BlockEnergy.defineEnergyStorage(this, "main", capacity, unit);
		BlockEnergy.defineStorageSelector(this, (_, _, _, definedStorages) -> definedStorages.apply("main"));
	}
}
