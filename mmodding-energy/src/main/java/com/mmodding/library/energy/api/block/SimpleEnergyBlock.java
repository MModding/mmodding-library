package com.mmodding.library.energy.api.block;

import com.mmodding.library.energy.api.EnergyUnit;
import com.mmodding.library.energy.api.access.EnergyAccess;
import net.minecraft.world.level.block.Block;

/**
 * A simple {@link Block} holding a {@link EnergyAccess}, always accessible from any context.
 */
public class SimpleEnergyBlock extends Block {

	public SimpleEnergyBlock(int capacity, EnergyUnit unit, Properties properties) {
		super(properties);
		BlockEnergy.defineEnergy(this, capacity, unit, (_, _, _, internal) -> EnergyAccess.from(internal));
	}
}
