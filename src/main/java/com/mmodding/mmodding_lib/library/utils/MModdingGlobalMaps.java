package com.mmodding.mmodding_lib.library.utils;

import com.mmodding.mmodding_lib.library.portals.squared.AbstractSquaredPortal;
import com.mmodding.mmodding_lib.library.portals.squared.CustomSquaredPortal;
import com.mmodding.mmodding_lib.library.portals.squared.UnlinkedCustomSquaredPortal;
import com.mmodding.mmodding_lib.library.stellar.client.StellarCycle;
import com.mmodding.mmodding_lib.library.worldgen.veins.CustomVeinType;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.RegistryKey;
import net.minecraft.world.World;

import java.util.*;

public class MModdingGlobalMaps {

	static final Map<Identifier, StellarCycle> STELLAR_CYCLES = new HashMap<>();

	static final Map<Identifier, CustomSquaredPortal> CUSTOM_SQUARED_PORTALS = new HashMap<>();

	static final Map<Identifier, UnlinkedCustomSquaredPortal> UNLINKED_CUSTOM_SQUARED_PORTALS = new HashMap<>();

	static final Map<Identifier, List<CustomVeinType>> CUSTOM_VEIN_TYPES = new HashMap<>();

	static final List<RegistryKey<World>> DIFFERED_DIMENSION_SEEDS = new ArrayList<>();

	public static Set<Identifier> getStellarCycleKeys() {
		return STELLAR_CYCLES.keySet();
	}

	public static StellarCycle getStellarCycle(Identifier identifier) {
		return STELLAR_CYCLES.get(identifier);
	}

	public static Set<Identifier> getCustomSquaredPortalKeys() {
		return CUSTOM_SQUARED_PORTALS.keySet();
	}

	public static Set<Identifier> getUnlinkedCustomSquaredPortalKeys() {
		return UNLINKED_CUSTOM_SQUARED_PORTALS.keySet();
	}

	public static Set<Identifier> getAllCustomSquaredPortalKeys() {
		Set<Identifier> set = new HashSet<>();
		set.addAll(MModdingGlobalMaps.getCustomSquaredPortalKeys());
		set.addAll(MModdingGlobalMaps.getUnlinkedCustomSquaredPortalKeys());
		return set;
	}

	public static CustomSquaredPortal getCustomSquaredPortal(Identifier identifier) {
		return CUSTOM_SQUARED_PORTALS.get(identifier);
	}

	public static UnlinkedCustomSquaredPortal getCustomUnlinkedPortal(Identifier identifier) {
		return UNLINKED_CUSTOM_SQUARED_PORTALS.get(identifier);
	}

	public static AbstractSquaredPortal getAbstractSquaredPortal(Identifier identifier) {
		return CUSTOM_SQUARED_PORTALS.containsKey(identifier) ? MModdingGlobalMaps.getCustomSquaredPortal(identifier) : MModdingGlobalMaps.getCustomUnlinkedPortal(identifier);
	}

	public static boolean hasCustomVeinTypes(Identifier chunkGeneratorSettingsIdentifier) {
		return CUSTOM_VEIN_TYPES.containsKey(chunkGeneratorSettingsIdentifier);
	}

	public static Set<CustomVeinType> getCustomVeinTypes(Identifier chunkGeneratorSettingsIdentifier) {
		return new HashSet<>(CUSTOM_VEIN_TYPES.get(chunkGeneratorSettingsIdentifier));
	}

	public static List<RegistryKey<World>> getDifferedDimensionSeeds() {
        return new ArrayList<>(DIFFERED_DIMENSION_SEEDS);
	}
}
