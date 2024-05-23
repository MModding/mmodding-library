package com.mmodding.library.worldgen.api.seed;

import com.mmodding.library.worldgen.impl.seed.DifferedSeedImpl;
import net.minecraft.registry.RegistryKey;
import net.minecraft.world.World;

public interface DifferedSeed {

	static void configureFor(RegistryKey<World> world) {
		DifferedSeedImpl.ATTACHMENT.put(world, true);
	}
}
