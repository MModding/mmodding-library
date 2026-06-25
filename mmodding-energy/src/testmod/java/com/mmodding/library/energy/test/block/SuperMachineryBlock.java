package com.mmodding.library.energy.test.block;

import com.mmodding.library.energy.api.block.BlockEnergy;
import com.mmodding.library.energy.api.convention.FabricEnergy;
import com.mmodding.library.energy.api.access.EnergyAccess;
import com.mmodding.library.energy.test.block.entity.SuperMachineryBlockEntity;
import com.mmodding.library.energy.test.init.EnergyTestBlockEntities;
import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import org.jspecify.annotations.Nullable;

public class SuperMachineryBlock extends BaseEntityBlock {

	public static final MapCodec<SuperMachineryBlock> CODEC = simpleCodec(SuperMachineryBlock::new);

	public SuperMachineryBlock(Properties properties) {
		super(properties);
		BlockEnergy.defineEnergy(
			this, 10000L, FabricEnergy.UNIT,
			(_, _, side, internalStorage) -> Direction.UP.equals(side) ? EnergyAccess.from(internalStorage) : null
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

	@Override
	protected InteractionResult useWithoutItem(BlockState state, Level level, BlockPos pos, Player player, BlockHitResult hitResult) {
		if (level.isClientSide()) {
			return InteractionResult.SUCCESS;
		}
		else {
			if (level.getBlockEntity(pos) instanceof SuperMachineryBlockEntity blockEntity) {
				player.openMenu(blockEntity);
			}
			return InteractionResult.SUCCESS_SERVER;
		}
	}

	@Nullable
	@Override
	public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState blockState, BlockEntityType<T> type) {
		return createTickerHelper(type, EnergyTestBlockEntities.SUPER_MACHINERY, SuperMachineryBlockEntity::tick);
	}
}
