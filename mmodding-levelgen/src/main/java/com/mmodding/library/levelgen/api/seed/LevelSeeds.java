package com.mmodding.library.levelgen.api.seed;

import com.mmodding.library.levelgen.impl.seed.LevelSeedsImpl;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.Level;

/**
 * Seed manipulation towards specific levels.
 */
public final class LevelSeeds {

	private LevelSeeds() {}

	/**
	 * Marks the specified {@link Level} as using an independent seed generated for it,
	 * instead of the global world seed.
	 * @param level the specified level
	 */
	public static void markAsSeedIndependent(ResourceKey<Level> level) {
		LevelSeedsImpl.markAsSeedIndependent(level);
	}
}
