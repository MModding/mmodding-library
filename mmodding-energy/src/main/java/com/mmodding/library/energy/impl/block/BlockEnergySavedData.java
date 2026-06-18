package com.mmodding.library.energy.impl.block;

import com.mmodding.library.core.api.MModdingLibrary;
import com.mmodding.library.core.api.serialization.MModdingCodecs;
import com.mmodding.library.energy.api.EnergyUnit;
import com.mmodding.library.energy.api.storage.EnergyStorage;
import com.mmodding.library.energy.impl.storage.EnergyStorageImpl;
import com.mmodding.library.java.api.map.BiMap;
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
			Codec.unboundedMap(Codec.STRING, Codec.LONG)
		).xmap(BlockEnergySavedData::new, BlockEnergySavedData::storage),
		null
	);

	private final Map<BlockPos, Map<String, Long>> loaded;
	private final Map<BlockPos, Map<String, EnergyStorage>> storage;

	public BlockEnergySavedData() {
		this.loaded = new Object2ObjectOpenHashMap<>();
		this.storage = new Object2ObjectOpenHashMap<>();
	}

	public BlockEnergySavedData(Map<BlockPos, Map<String, Long>> loaded) {
		this.loaded = loaded;
		this.storage = new Object2ObjectOpenHashMap<>();
	}

	public Map<String, EnergyStorage> getOrComputeDefinitions(BlockPos pos, BiMap<String, Long, EnergyUnit> storageDefinitions) {
		this.setDirty();
		return this.storage.computeIfAbsent(pos, p -> {
			Map<String, EnergyStorage> creating = new Object2ObjectOpenHashMap<>();
			storageDefinitions.forEach((name, capacity, unit) -> {
				creating.put(name, new EnergyStorageImpl(capacity, unit));
			});
			if (this.loaded.containsKey(p)) {
				this.loaded.get(p).forEach((id, data) -> {
					((EnergyStorageImpl) creating.get(id)).setAmount(data);
				});
			}
			return creating;
		});
	}

	public Map<BlockPos, Map<String, Long>> storage() {
		Map<BlockPos, Map<String, Long>> result = new Object2ObjectOpenHashMap<>();
		result.putAll(this.loaded);
		this.storage.forEach((pos, storages) -> {
			Map<String, Long> data = result.computeIfAbsent(pos, _ -> new Object2ObjectOpenHashMap<>());
			storages.forEach((name, storage) -> data.put(name, storage.amount()));
		});
		this.loaded.clear();
		this.loaded.putAll(result);
		return this.loaded;
	}
}
