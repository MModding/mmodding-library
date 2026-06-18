package com.mmodding.library.energy.impl.block;

import com.google.common.collect.Sets;
import com.mmodding.library.core.api.MModdingLibrary;
import com.mmodding.library.energy.api.EnergyUnit;
import com.mmodding.library.energy.api.block.BlockEnergy;
import com.mmodding.library.energy.api.storage.EnergyStorage;
import net.fabricmc.fabric.api.lookup.v1.block.BlockApiLookup;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.block.Block;

import java.util.Set;

public final class BlockEnergyImpl {

	public static final BlockApiLookup<EnergyStorage, Direction> SIDED = BlockApiLookup.get(MModdingLibrary.createId("energy"), EnergyStorage.class, Direction.class);

	public static final Set<Block> DEFINITIONS_LOCK = Sets.newIdentityHashSet();

	private BlockEnergyImpl() {}

	public static void defineEnergyStorage(Block block, long capacity, EnergyUnit unit, BlockEnergy.StorageQueryHandler handler) {
		if (DEFINITIONS_LOCK.contains(block)) {
			throw new IllegalStateException("Block " + block + " already has a defined storage selector!");
		}
		SIDED.registerForBlocks((level, pos, _, _, side) -> {
			ServerLevel serverLevel = (ServerLevel) level;
			return handler.handle(
				serverLevel, pos, side,
				serverLevel.getDataStorage()
					.computeIfAbsent(BlockEnergySavedData.TYPE)
					.getOrComputeDefinition(pos, capacity, unit)
			);
		}, block);
	}

	public static void defineStorageQueryDelegate(Block block, BlockEnergy.StorageQueryDelegator handler) {
		if (DEFINITIONS_LOCK.contains(block)) {
			throw new IllegalStateException();
		}
		SIDED.registerForBlocks((level, pos, _, _, side) -> {
			ServerLevel serverLevel = (ServerLevel) level;
			return handler.handle(serverLevel, pos, side);
		});
	}
}
