package com.mmodding.library.energy.test.block;

import com.mmodding.library.energy.test.block.entity.InfinitePowerSourceBlockEntity;
import com.mmodding.library.energy.test.init.EnergyTestBlockEntities;
import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import org.jspecify.annotations.Nullable;

public class InfinitePowerSourceBlock extends BaseEntityBlock {

	private static final MapCodec<InfinitePowerSourceBlock> CODEC = simpleCodec(InfinitePowerSourceBlock::new);

	public InfinitePowerSourceBlock(Properties properties) {
		super(properties);
	}

	@Override
	protected MapCodec<? extends BaseEntityBlock> codec() {
		return CODEC;
	}

	@Override
	public @Nullable BlockEntity newBlockEntity(BlockPos worldPosition, BlockState blockState) {
		return new InfinitePowerSourceBlockEntity(worldPosition, blockState);
	}

	@Nullable
	@Override
	public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState blockState, BlockEntityType<T> type) {
		return createTickerHelper(type, EnergyTestBlockEntities.INFINITE_POWER_SOURCE, InfinitePowerSourceBlockEntity::tick);
	}
}
