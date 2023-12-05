package com.mmodding.mmodding_lib.ducks;

import com.mmodding.mmodding_lib.library.utils.InternalOf;
import com.mmodding.mmodding_lib.library.worldgen.veins.CustomVeinType;
import net.minecraft.util.Holder;
import net.minecraft.world.gen.chunk.ChunkGeneratorSettings;

@InternalOf(targets = CustomVeinType.class)
public interface ChunkNoiseSamplerDuckInterface {

	Holder<ChunkGeneratorSettings> mmodding_lib$getSettingsHolder();

	void mmodding_lib$setSettingsHolder(Holder<ChunkGeneratorSettings> holder);

	void mmodding_lib$reloadBlockStateSampler();
}
