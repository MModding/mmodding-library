package com.mmodding.library.api;

import com.mmodding.library.core.api.registry.companion.DynamicRegistryCompanion;
import com.mmodding.library.worldgen.api.vein.VeinType;
import net.minecraft.world.gen.chunk.ChunkGeneratorSettings;

public class MModdingRegistryCompanions {

	public static final DynamicRegistryCompanion<ChunkGeneratorSettings, VeinType> VEIN_TYPE = VeinType.REGISTRY;
}
