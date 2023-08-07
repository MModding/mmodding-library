package com.mmodding.mmodding_lib.library.utils;

import com.mmodding.mmodding_lib.library.portals.CustomSquaredPortalBlock;
import com.mmodding.mmodding_lib.library.worldgen.veins.CustomVeinType;
import net.minecraft.block.Block;
import net.minecraft.util.Identifier;
import net.minecraft.util.Pair;
import net.minecraft.util.registry.RegistryKey;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;

import java.util.*;
import java.util.function.Function;
import java.util.function.IntFunction;

public class MModdingGlobalMaps {

	static final Map<Identifier, IntFunction<RegistryKey<Biome>>> DEFAULT_BIOMES = new HashMap<>();
	static final Map<Identifier, Pair<? extends Block, ? extends CustomSquaredPortalBlock>> CUSTOM_SQUARED_PORTALS = new HashMap<>();
	static final Map<Identifier, List<CustomVeinType>> CUSTOM_VEIN_TYPES = new HashMap<>();
	static final List<RegistryKey<World>> DIFFERED_DIMENSION_SEEDS = new ArrayList<>();

	public static boolean hasDefaultBiomes(Identifier dimensionIdentifier) {
		return DEFAULT_BIOMES.containsKey(dimensionIdentifier);
	}

	public static IntFunction<RegistryKey<Biome>> getDefaultBiomes(Identifier dimensionIdentifier) {
		return DEFAULT_BIOMES.get(dimensionIdentifier);
	}

	public static Set<Identifier> getCustomSquaredPortalKeys() {
		return CUSTOM_SQUARED_PORTALS.keySet();
	}

	public static Pair<? extends Block, ? extends CustomSquaredPortalBlock> getCustomSquaredPortal(Identifier identifier) {
		return CUSTOM_SQUARED_PORTALS.getOrDefault(identifier, null);
	}

	public static boolean hasCustomVeinTypes(Identifier chunkGeneratorSettingsIdentifier) {
		return CUSTOM_VEIN_TYPES.containsKey(chunkGeneratorSettingsIdentifier);
	}

	public static Set<CustomVeinType> getCustomVeinTypes(Identifier chunkGeneratorSettingsIdentifier) {
		return new HashSet<>(CUSTOM_VEIN_TYPES.get(chunkGeneratorSettingsIdentifier));
	}

	public static Set<RegistryKey<World>> getDifferedDimensionSeeds() {
		return new HashSet<>(DIFFERED_DIMENSION_SEEDS);
	}
}
