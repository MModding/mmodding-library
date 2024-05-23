package com.mmodding.library.api;

import com.mmodding.library.core.api.registry.extension.RegistryCompanion;
import com.mmodding.library.worldgen.api.vein.VeinType;
import net.minecraft.world.gen.chunk.ChunkGeneratorSettings;

public class MModdingRegistryCompanions {

	public static final RegistryCompanion<ChunkGeneratorSettings, VeinType> VEIN_TYPE = VeinType.REGISTRY;
}
