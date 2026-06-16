package com.mmodding.library.levelgen.impl.seed;

import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.Level;

import java.util.HashSet;
import java.util.Set;

public class LevelSeedsImpl {

	public static final Set<ResourceKey<Level>> HAVE_INDEPENDENT_SEEDS = new HashSet<>();

	public static void markAsSeedIndependent(ResourceKey<Level> level) {
		HAVE_INDEPENDENT_SEEDS.add(level);
	}
}
