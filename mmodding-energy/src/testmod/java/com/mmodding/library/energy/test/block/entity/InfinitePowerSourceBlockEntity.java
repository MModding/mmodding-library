package com.mmodding.library.energy.test.block.entity;

import com.mmodding.library.energy.api.block.BlockEnergy;
import com.mmodding.library.energy.api.convention.FabricEnergy;
import com.mmodding.library.energy.api.access.EnergyAccess;
import com.mmodding.library.energy.test.init.EnergyTestBlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

public class InfinitePowerSourceBlockEntity extends BlockEntity {

	public InfinitePowerSourceBlockEntity(BlockPos worldPosition, BlockState blockState) {
		super(EnergyTestBlockEntities.INFINITE_POWER_SOURCE, worldPosition, blockState);
	}

	public static void tick(Level level, BlockPos pos, BlockState blockState, InfinitePowerSourceBlockEntity blockEntity) {
		if (level instanceof ServerLevel serverLevel) {
			EnergyAccess operator = EnergyAccess.infinite(FabricEnergy.UNIT);
			Direction.stream().forEach(direction -> {
				EnergyAccess target = BlockEnergy.query(serverLevel, pos.relative(direction), direction.getOpposite());
				if (target != null) operator.transferTo(target, 20, null);
			});
		}
	}
}
