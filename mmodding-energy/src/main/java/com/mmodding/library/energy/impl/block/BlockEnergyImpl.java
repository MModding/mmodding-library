package com.mmodding.library.energy.impl.block;

import com.google.common.collect.Sets;
import com.mmodding.library.core.api.MModdingLibrary;
import com.mmodding.library.energy.api.EnergyUnit;
import com.mmodding.library.energy.api.block.BlockEnergy;
import com.mmodding.library.energy.api.storage.EnergyStorage;
import com.mmodding.library.java.api.map.BiMap;
import net.fabricmc.fabric.api.lookup.v1.block.BlockApiLookup;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.block.Block;

import java.util.IdentityHashMap;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;

public final class BlockEnergyImpl {

	public static final BlockApiLookup<EnergyStorage, Direction> SIDED = BlockApiLookup.get(MModdingLibrary.createId("energy"), EnergyStorage.class, Direction.class);

	public static final Map<Block, BiMap<String, Long, EnergyUnit>> BLOCK_STORAGE_DEFINITIONS = new IdentityHashMap<>();

	public static final Set<Block> SELECTORS_LOCK = Sets.newIdentityHashSet();

	private BlockEnergyImpl() {}

	public static void defineEnergyStorage(Block block, String name, long capacity, EnergyUnit unit) {
		BLOCK_STORAGE_DEFINITIONS.computeIfAbsent(block, _ -> BiMap.create()).put(name, capacity, unit);
	}

	public static void defineStorageSelector(Block block, BlockEnergy.StorageSelector selector) {
		if (SELECTORS_LOCK.contains(block)) {
			throw new IllegalStateException("Block " + block + " already has a defined storage selector!");
		}
		SIDED.registerForBlocks((level, pos, _, _, side) -> {
			ServerLevel serverLevel = (ServerLevel) level;
			Function<String, EnergyStorage> definedStorages;
			if (BLOCK_STORAGE_DEFINITIONS.containsKey(block)) {
				Map<String, EnergyStorage> definedStoragesMap = serverLevel.getDataStorage()
					.computeIfAbsent(BlockEnergySavedData.TYPE)
					.getOrComputeDefinitions(pos, BLOCK_STORAGE_DEFINITIONS.get(block));
				definedStorages = definedStoragesMap::get;
			}
			else {
				definedStorages = _ -> null;
			}
			return selector.select(serverLevel, pos, side, definedStorages);
		}, block);
	}
}
