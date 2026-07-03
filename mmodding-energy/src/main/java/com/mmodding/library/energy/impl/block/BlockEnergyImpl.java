package com.mmodding.library.energy.impl.block;

import com.google.common.collect.Sets;
import com.mmodding.library.core.api.MModdingLibrary;
import com.mmodding.library.energy.api.EnergyComponent;
import com.mmodding.library.energy.api.EnergyUnit;
import com.mmodding.library.energy.api.block.BlockEnergy;
import com.mmodding.library.energy.api.access.EnergyAccess;
import com.mmodding.library.java.api.container.Pair;
import net.fabricmc.fabric.api.lookup.v1.block.BlockApiLookup;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.jspecify.annotations.Nullable;

import java.util.IdentityHashMap;
import java.util.Map;
import java.util.Set;
import java.util.function.BiFunction;

public final class BlockEnergyImpl {

	public static final BlockApiLookup<EnergyAccess, @Nullable Direction> SIDED = BlockApiLookup.get(MModdingLibrary.createId("energy"), EnergyAccess.class, Direction.class);

	private static final Set<Block> DEFINITIONS_LOCK = Sets.newIdentityHashSet();

	public static final Map<Block, Pair<BiFunction<BlockState, @Nullable BlockEntity, Long>, EnergyUnit>> DEFINITIONS = new IdentityHashMap<>();

	private BlockEnergyImpl() {}

	public static EnergyComponent retrieveFrom(BlockEntity blockEntity) {
		if (blockEntity.getLevel() instanceof ServerLevel level) {
			return level.getDataStorage()
				.computeIfAbsent(BlockEnergySavedData.TYPE)
				.getComponent(level, blockEntity.getBlockPos(), blockEntity.getBlockState().getBlock());
		}
		else {
			throw new IllegalStateException("Block entity is not in a level!");
		}
	}

	@SuppressWarnings("unchecked")
	public static <T extends BlockEntity> void defineEnergy(Block block, BiFunction<BlockState, @Nullable T, Long> capacityGetter, EnergyUnit unit, BlockEnergy.AccessQueryHandler handler) {
		if (DEFINITIONS_LOCK.contains(block)) {
			throw new IllegalStateException("Block " + block + " already has a defined access query!");
		}
		DEFINITIONS_LOCK.add(block);
		DEFINITIONS.put(block, Pair.create((BiFunction<BlockState, BlockEntity, Long>) capacityGetter, unit));
		SIDED.registerForBlocks((level, pos, _, _, side) -> {
			ServerLevel serverLevel = (ServerLevel) level;
			return handler.handle(
				serverLevel, pos, side,
				serverLevel.getDataStorage()
					.computeIfAbsent(BlockEnergySavedData.TYPE)
					.getComponent(serverLevel, pos, block)
			);
		}, block);
	}

	public static void defineEnergyDelegate(Block block, BlockEnergy.HeadlessQueryHandler handler) {
		if (DEFINITIONS_LOCK.contains(block)) {
			throw new IllegalStateException("Block " + block + " already has a defined storage query!");
		}
		DEFINITIONS_LOCK.add(block);
		SIDED.registerForBlocks((level, pos, _, _, side) -> {
			ServerLevel serverLevel = (ServerLevel) level;
			return handler.handle(serverLevel, pos, side);
		}, block);
	}
}
