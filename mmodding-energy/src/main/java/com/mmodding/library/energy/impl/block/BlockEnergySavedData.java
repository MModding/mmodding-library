package com.mmodding.library.energy.impl.block;

import com.mmodding.library.core.api.MModdingLibrary;
import com.mmodding.library.core.api.serialization.MModdingCodecs;
import com.mmodding.library.energy.api.EnergyComponent;
import com.mmodding.library.energy.api.EnergyUnit;
import com.mmodding.library.energy.impl.EnergyComponentImpl;
import com.mmodding.library.energy.impl.data.DedicatedEnergyData;
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
			Codec.LONG.xmap(DedicatedEnergyData::new, DedicatedEnergyData::getAmount)
		).xmap(BlockEnergySavedData::new, BlockEnergySavedData::storage),
		null
	);

	private final Map<BlockPos, DedicatedEnergyData> storage;

	public BlockEnergySavedData() {
		this.storage = new Object2ObjectOpenHashMap<>();
	}

	public BlockEnergySavedData(Map<BlockPos, DedicatedEnergyData> storage) {
		this.storage = new Object2ObjectOpenHashMap<>(storage);
	}

	public EnergyComponent getComponent(BlockPos pos, Block type) {
		this.setDirty();
		Pair<Long, EnergyUnit> definition = Objects.requireNonNull(BlockEnergyImpl.DEFINITIONS.get(type), "Unregistered Block Energy Definition for block " + type);
		return new EnergyComponentImpl(definition.first(), definition.second(), this.storage.computeIfAbsent(pos, _ -> new DedicatedEnergyData(0L)));
	}

	public void removeIfPresent(BlockPos pos) {
		this.storage.remove(pos);
		this.setDirty();
	}

	public Map<BlockPos, DedicatedEnergyData> storage() {
		return this.storage;
	}
}
