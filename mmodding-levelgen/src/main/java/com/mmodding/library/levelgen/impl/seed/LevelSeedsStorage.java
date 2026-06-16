package com.mmodding.library.levelgen.impl.seed;

import com.mmodding.library.core.api.MModdingLibrary;
import com.mojang.serialization.Codec;
import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.levelgen.WorldOptions;
import net.minecraft.world.level.saveddata.SavedData;
import net.minecraft.world.level.saveddata.SavedDataType;

import java.util.Map;

public class LevelSeedsStorage extends SavedData {

	public static final SavedDataType<LevelSeedsStorage> TYPE = new SavedDataType<>(
		MModdingLibrary.createId("level_seeds"),
		LevelSeedsStorage::new,
		Codec.unboundedMap(
			ResourceKey.codec(Registries.DIMENSION),
			Codec.LONG
		).xmap(LevelSeedsStorage::new, LevelSeedsStorage::storage),
		null
	);

	private final Map<ResourceKey<Level>, Long> storage;

	public LevelSeedsStorage() {
		this.storage = new Object2ObjectOpenHashMap<>();
	}

	public LevelSeedsStorage(Map<ResourceKey<Level>, Long> storage) {
		this.storage = storage;
	}

	private Map<ResourceKey<Level>, Long> storage() {
		return this.storage;
	}

	public long getOrGenerateLevelSeed(ResourceKey<Level> level) {
		return this.storage.computeIfAbsent(level, _ -> {
			this.setDirty();
			return WorldOptions.randomSeed();
		});
	}
}
