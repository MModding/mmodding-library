package com.mmodding.library.worldgen.api.seed;

import com.mmodding.library.worldgen.impl.seed.IndependentSeedImpl;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.Level;

public interface IndependentSeed {

	static void configureFor(ResourceKey<Level> world) {
		IndependentSeedImpl.ATTACHMENT.put(world, true);
	}
}
