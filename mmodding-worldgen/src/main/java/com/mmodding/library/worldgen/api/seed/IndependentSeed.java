package com.mmodding.library.worldgen.api.seed;

import com.mmodding.library.worldgen.impl.seed.IndependentSeedImpl;
import net.minecraft.registry.RegistryKey;
import net.minecraft.world.World;

public interface IndependentSeed {

	static void configureFor(RegistryKey<World> world) {
		IndependentSeedImpl.ATTACHMENT.put(world, true);
	}
}
