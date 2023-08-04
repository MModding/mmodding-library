package com.mmodding.mmodding_lib.ducks;

import net.minecraft.util.Holder;
import net.minecraft.world.gen.chunk.ChunkGeneratorSettings;

public interface ChunkNoiseSamplerDuckInterface {

	Holder<ChunkGeneratorSettings> mmodding_lib$getSettingsHolder();

	void mmodding_lib$setSettingsHolder(Holder<ChunkGeneratorSettings> holder);

	void mmodding_lib$reloadBlockStateSampler();
}
