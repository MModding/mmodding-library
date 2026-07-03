package com.mmodding.library.energy.api.block.entity;

import com.mmodding.library.energy.api.EnergyComponent;
import com.mmodding.library.energy.api.access.EnergyAccess;
import com.mmodding.library.energy.api.block.BlockEnergy;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BaseContainerBlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import org.jspecify.annotations.Nullable;

/**
 * A {@link BaseContainerBlockEntity} with predefined fields for accessing its internal {@link EnergyComponent}
 * and an {@link EnergyAccess} on it.
 */
public abstract class BaseContainerEnergyBlockEntity extends BaseContainerBlockEntity {

	private @Nullable EnergyComponent energy;
	private @Nullable EnergyAccess energyAccess;

	protected BaseContainerEnergyBlockEntity(BlockEntityType<?> type, BlockPos worldPosition, BlockState blockState) {
		super(type, worldPosition, blockState);
	}

	protected EnergyComponent getEnergy() {
		if (this.energy == null) {
			this.energy = BlockEnergy.retrieveFrom(this);
		}
		return this.energy;
	}

	protected EnergyAccess getEnergyAccess() {
		if (this.energyAccess == null) {
			this.energyAccess = EnergyAccess.from(this.getEnergy());
		}
		return this.energyAccess;
	}
}
