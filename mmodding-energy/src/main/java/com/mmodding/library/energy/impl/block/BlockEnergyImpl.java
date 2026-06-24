package com.mmodding.library.energy.impl.block;

import com.google.common.collect.Sets;
import com.mmodding.library.core.api.MModdingLibrary;
import com.mmodding.library.energy.api.EnergyUnit;
import com.mmodding.library.energy.api.block.BlockEnergy;
import com.mmodding.library.energy.api.storage.EnergyStorage;
import com.mmodding.library.java.api.container.Pair;
import net.fabricmc.fabric.api.lookup.v1.block.BlockApiLookup;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;

import java.util.IdentityHashMap;
import java.util.Map;
import java.util.Set;

public final class BlockEnergyImpl {

	public static final BlockApiLookup<EnergyStorage, Direction> SIDED = BlockApiLookup.get(MModdingLibrary.createId("energy"), EnergyStorage.class, Direction.class);

	private static final Set<Block> DEFINITIONS_LOCK = Sets.newIdentityHashSet();

	public static final Map<Block, Pair<Long, EnergyUnit>> DEFINITIONS = new IdentityHashMap<>();

	private BlockEnergyImpl() {}

	public static EnergyStorage accessStorage(BlockEntity blockEntity) {
		if (blockEntity.getLevel() instanceof ServerLevel level) {
			return level.getDataStorage()
				.computeIfAbsent(BlockEnergySavedData.TYPE)
				.getOrComputeDefinition(blockEntity.getBlockPos(), blockEntity.getBlockState().getBlock());
		}
		else {
			throw new IllegalStateException("Block entity is not in a level!");
		}
	}

	public static void defineEnergyStorage(Block block, long capacity, EnergyUnit unit, BlockEnergy.StorageQueryHandler handler) {
		if (DEFINITIONS_LOCK.contains(block)) {
			throw new IllegalStateException("Block " + block + " already has a defined storage query!");
		}
		DEFINITIONS_LOCK.add(block);
		DEFINITIONS.put(block, Pair.create(capacity, unit));
		SIDED.registerForBlocks((level, pos, _, _, side) -> {
			ServerLevel serverLevel = (ServerLevel) level;
			return handler.handle(
				serverLevel, pos, side,
				serverLevel.getDataStorage()
					.computeIfAbsent(BlockEnergySavedData.TYPE)
					.getOrComputeDefinition(pos, block)
			);
		}, block);
	}

	public static void defineStorageQueryDelegate(Block block, BlockEnergy.StorageQueryDelegator handler) {
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
