package com.mmodding.library.energy.api.block.entity;

import com.mmodding.library.energy.api.EnergyComponent;
import com.mmodding.library.energy.api.block.BlockEnergy;
import com.mmodding.library.energy.api.access.EnergyAccess;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

import java.util.Objects;

/**
 * A {@link BlockEntity} with predefined fields for accessing its internal {@link EnergyComponent}
 * and an {@link EnergyAccess} on it.
 */
public abstract class EnergyBlockEntity extends BlockEntity {

	protected final EnergyComponent energy;
	protected final EnergyAccess energyAccess;

	protected EnergyBlockEntity(BlockEntityType<?> type, BlockPos worldPosition, BlockState blockState) {
		super(type, worldPosition, blockState);
		this.energy = Objects.requireNonNull(BlockEnergy.retrieveFrom(this));
		this.energyAccess = EnergyAccess.from(this.energy);
	}
}
