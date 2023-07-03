package com.mmodding.mmodding_lib.ducks;

import net.minecraft.util.Holder;
import net.minecraft.world.gen.chunk.ChunkGeneratorSettings;

public interface ChunkNoiseSamplerDuckInterface {

	Holder<ChunkGeneratorSettings> getSettingsHolder();

	void setSettingsHolder(Holder<ChunkGeneratorSettings> holder);

	void reloadBlockStateSampler();
}
