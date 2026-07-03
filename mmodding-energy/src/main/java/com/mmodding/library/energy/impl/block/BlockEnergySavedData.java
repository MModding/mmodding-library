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
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerBlockEntityEvents;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerChunkEvents;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.saveddata.SavedData;
import net.minecraft.world.level.saveddata.SavedDataType;
import org.jspecify.annotations.Nullable;

import java.util.Map;
import java.util.Objects;
import java.util.function.BiFunction;

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

	// Caching (required context for variable capacity)
	private final Map<BlockPos, BlockState> blockStateCache = new Object2ObjectOpenHashMap<>();
	private final Map<BlockPos, BlockEntity> blockEntityCache = new Object2ObjectOpenHashMap<>();

	public BlockEnergySavedData() {
		this.storage = new Object2ObjectOpenHashMap<>();
	}

	public BlockEnergySavedData(Map<BlockPos, DedicatedEnergyData> storage) {
		this.storage = new Object2ObjectOpenHashMap<>(storage);
	}

	public BlockState cachedBlockState(ServerLevel level, BlockPos pos) {
		return this.blockStateCache.computeIfAbsent(pos, level::getBlockState);
	}

	public BlockEntity cachedBlockEntity(ServerLevel level, BlockPos pos) {
		return this.blockEntityCache.computeIfAbsent(pos, level::getBlockEntity);
	}

	public EnergyComponent getComponent(BlockPos pos, Block type, BlockState state, BlockEntity blockEntity) {
		this.setDirty();
		Pair<BiFunction<BlockState, @Nullable BlockEntity, Long>, EnergyUnit> definition = Objects.requireNonNull(BlockEnergyImpl.DEFINITIONS.get(type), "Unregistered Block Energy Definition for block " + type);
		return new EnergyComponentImpl(
			() -> definition.first().apply(state, blockEntity),
			definition.second(),
			this.storage.computeIfAbsent(pos, _ -> new DedicatedEnergyData(0L))
		);
	}

	public void updateState(BlockPos pos, BlockState state) {
		this.blockStateCache.put(pos, state);
	}

	public void removeIfPresent(BlockPos pos) {
		this.storage.remove(pos);
		this.setDirty();
		this.blockStateCache.remove(pos);
		this.blockEntityCache.remove(pos);
	}

	public Map<BlockPos, DedicatedEnergyData> storage() {
		return this.storage;
	}

	public static void registerCacheInvalidation() {
		ServerChunkEvents.CHUNK_LOAD.register((level, chunk, _) -> {
			BlockEnergySavedData storage = level.getDataStorage().get(BlockEnergySavedData.TYPE);
			if (storage != null) {
				storage.blockStateCache.keySet().removeIf(chunk.getPos()::contains);
				storage.blockEntityCache.keySet().removeIf(chunk.getPos()::contains);
			}
		});

		ServerChunkEvents.CHUNK_UNLOAD.register((level, chunk) -> {
			BlockEnergySavedData storage = level.getDataStorage().get(BlockEnergySavedData.TYPE);
			if (storage != null) {
				storage.blockStateCache.keySet().removeIf(chunk.getPos()::contains);
				storage.blockEntityCache.keySet().removeIf(chunk.getPos()::contains);
			}
		});

		ServerBlockEntityEvents.BLOCK_ENTITY_LOAD.register((blockEntity, level) -> {
			BlockEnergySavedData storage = level.getDataStorage().get(BlockEnergySavedData.TYPE);
			if (storage != null) {
				storage.blockStateCache.remove(blockEntity.getBlockPos());
				storage.blockEntityCache.remove(blockEntity.getBlockPos());
			}
		});

		ServerBlockEntityEvents.BLOCK_ENTITY_UNLOAD.register((blockEntity, level) -> {
			BlockEnergySavedData storage = level.getDataStorage().get(BlockEnergySavedData.TYPE);
			if (storage != null) {
				storage.blockStateCache.remove(blockEntity.getBlockPos());
				storage.blockEntityCache.remove(blockEntity.getBlockPos());
			}
		});
	}
}
