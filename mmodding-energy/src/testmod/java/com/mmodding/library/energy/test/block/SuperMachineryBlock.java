package com.mmodding.library.energy.test.block;

import com.mmodding.library.energy.api.block.BlockEnergy;
import com.mmodding.library.energy.api.convention.FabricEnergy;
import com.mmodding.library.energy.test.block.entity.SuperMachineryBlockEntity;
import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.jspecify.annotations.Nullable;

public class SuperMachineryBlock extends BaseEntityBlock {

	public static final MapCodec<SuperMachineryBlock> CODEC = simpleCodec(SuperMachineryBlock::new);

	public SuperMachineryBlock(Properties properties) {
		super(properties);
		BlockEnergy.defineEnergyStorage(
			this, 10000L, FabricEnergy.UNIT,
			(_, _, side, internalStorage) -> side.equals(Direction.UP) ? internalStorage : null
		);
	}

	@Override
	protected MapCodec<? extends BaseEntityBlock> codec() {
		return CODEC;
	}

	@Nullable
	@Override
	public BlockEntity newBlockEntity(BlockPos worldPosition, BlockState blockState) {
		return new SuperMachineryBlockEntity(worldPosition, blockState);
	}
}
