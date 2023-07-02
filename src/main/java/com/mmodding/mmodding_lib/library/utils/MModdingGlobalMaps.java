package com.mmodding.mmodding_lib.library.utils;

import com.mmodding.mmodding_lib.library.portals.CustomSquaredPortalBlock;
import com.mmodding.mmodding_lib.library.worldgen.veins.CustomVeinType;
import net.minecraft.block.Block;
import net.minecraft.util.Identifier;
import net.minecraft.util.Pair;
import net.minecraft.util.registry.RegistryKey;
import net.minecraft.world.World;

import java.util.*;

public class MModdingGlobalMaps {

	static final Map<Identifier, Pair<? extends Block, ? extends CustomSquaredPortalBlock>> customSquaredPortals = new HashMap<>();
	static final Map<Identifier, List<CustomVeinType>> customVeinTypes = new HashMap<>();
	static final List<RegistryKey<World>> differedDimensionSeeds = new ArrayList<>();

	public static Set<Identifier> getCustomSquaredPortalKeys() {
		return customSquaredPortals.keySet();
	}

	public static Pair<? extends Block, ? extends CustomSquaredPortalBlock> getCustomSquaredPortal(Identifier identifier) {
		return customSquaredPortals.getOrDefault(identifier, null);
	}

	public static boolean hasCustomVeinTypes(Identifier chunkGeneratorSettingsIdentifier) {
		return customVeinTypes.containsKey(chunkGeneratorSettingsIdentifier);
	}

	public static Set<CustomVeinType> getCustomVeinTypes(Identifier identifier) {
		return new HashSet<>(customVeinTypes.get(identifier));
	}

	public static Set<RegistryKey<World>> getDifferedDimensionSeeds() {
		return new HashSet<>(differedDimensionSeeds);
	}
}
