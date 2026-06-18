package com.mmodding.library.energy.impl.block;

import com.mmodding.library.core.api.MModdingLibrary;
import com.mmodding.library.core.api.serialization.MModdingCodecs;
import com.mmodding.library.energy.api.EnergyUnit;
import com.mmodding.library.energy.api.storage.EnergyStorage;
import com.mmodding.library.energy.impl.storage.EnergyStorageImpl;
import com.mojang.serialization.Codec;
import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.saveddata.SavedData;
import net.minecraft.world.level.saveddata.SavedDataType;

import java.util.Map;

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
		this.loaded = loaded;
		this.storage = new Object2ObjectOpenHashMap<>();
	}

	public EnergyStorage getOrComputeDefinition(BlockPos pos, long capacity, EnergyUnit unit) {
		this.setDirty();
		return this.storage.computeIfAbsent(pos, p -> {
			EnergyStorageImpl impl = new EnergyStorageImpl(capacity, unit);
			if (this.loaded.containsKey(p)) impl.setAmount(this.loaded.get(p));
			return impl;
		});
	}

	public Map<BlockPos, Long> storage() {
		this.storage.forEach((pos, storage) -> this.loaded.put(pos, storage.amount()));
		return this.loaded;
	}
}
