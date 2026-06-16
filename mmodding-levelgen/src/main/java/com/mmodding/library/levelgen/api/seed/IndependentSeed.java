package com.mmodding.library.levelgen.api.seed;

import com.mmodding.library.levelgen.impl.seed.IndependentSeedImpl;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.Level;

public interface IndependentSeed {

	static void configureFor(ResourceKey<Level> level) {
		IndependentSeedImpl.ATTACHMENT.put(level, true);
	}
}
