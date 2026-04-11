package com.mmodding.library.api;

import com.mmodding.library.core.api.registry.companion.DynamicRegistryCompanion;
import com.mmodding.library.worldgen.api.vein.VeinType;
import net.minecraft.world.level.levelgen.NoiseGeneratorSettings;

public class MModdingRegistryCompanions {

	public static final DynamicRegistryCompanion<NoiseGeneratorSettings, VeinType> VEIN_TYPE = VeinType.REGISTRY;
}
