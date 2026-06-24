package com.mmodding.library.energy.impl.block;

import com.mmodding.library.core.api.MModdingLibrary;
import com.mmodding.library.core.api.serialization.MModdingCodecs;
import com.mmodding.library.energy.api.EnergyUnit;
import com.mmodding.library.energy.api.storage.EnergyStorage;
import com.mmodding.library.energy.impl.storage.AbstractEnergyStorageImpl;
import com.mmodding.library.energy.impl.storage.DedicatedEnergyStorageImpl;
import com.mmodding.library.java.api.container.Pair;
import com.mojang.serialization.Codec;
import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.saveddata.SavedData;
import net.minecraft.world.level.saveddata.SavedDataType;

import java.util.Map;
import java.util.Objects;

/**
 * @implNote level-specific data.
 */
public class BlockEnergySavedData extends SavedData {

	public static final SavedDataType<BlockEnergySavedData> TYPE = new SavedDataType<>(
		MModdingLibrary.createId("block_energy_storages"),
		BlockEnergySavedData::new,
		Codec.unboundedMap(
			MModdingCodecs.STRING_BLOCKPOS,
			Codec.LONG
		).xmap(BlockEnergySavedData::new, BlockEnergySavedData::storage),
		null
	);

	private final Map<BlockPos, Long> loaded;
	private final Map<BlockPos, EnergyStorage> storage;

	public BlockEnergySavedData() {
		this.loaded = new Object2ObjectOpenHashMap<>();
		this.storage = new Object2ObjectOpenHashMap<>();
	}

	public BlockEnergySavedData(Map<BlockPos, Long> loaded) {
		this.loaded = new Object2ObjectOpenHashMap<>(loaded);
		this.storage = new Object2ObjectOpenHashMap<>();
	}

	public EnergyStorage getOrComputeDefinition(BlockPos pos, Block type) {
		this.setDirty();
		return this.storage.computeIfAbsent(pos, p -> {
			Pair<Long, EnergyUnit> definition = Objects.requireNonNull(BlockEnergyImpl.DEFINITIONS.get(type), "Unregistered Block Energy Definition for block " + type);
			AbstractEnergyStorageImpl impl = new DedicatedEnergyStorageImpl(definition.first(), definition.second());
			if (this.loaded.containsKey(p)) impl.setAmount(this.loaded.get(p));
			return impl;
		});
	}

	public void removeIfPresent(BlockPos pos) {
		this.loaded.remove(pos);
		this.storage.remove(pos);
		this.setDirty();
	}

	public Map<BlockPos, Long> storage() {
		this.storage.forEach((pos, storage) -> this.loaded.put(pos, storage.amount()));
		return this.loaded;
	}
}
