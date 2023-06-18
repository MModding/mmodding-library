package com.mmodding.mmodding_lib.library.worldgen.chunkgenerators;

import net.minecraft.util.registry.Registry;
import net.minecraft.world.gen.DensityFunction;
import net.minecraft.world.gen.noise.NoiseRouter;
import net.minecraft.world.gen.noise.NoiseRouterData;

public class DefaultNoiseRouters extends NoiseRouterData {

	public static NoiseRouter getOverworld(Registry<DensityFunction> registry, boolean largeBiome, boolean amplified) {
		return NoiseRouterData.overworld(registry, largeBiome, amplified);
	}

	public static NoiseRouter getNether(Registry<DensityFunction> registry) {
		return NoiseRouterData.createNether(registry);
	}

	public static NoiseRouter getEnd(Registry<DensityFunction> registry) {
		return NoiseRouterData.createEnd(registry);
	}

	public static NoiseRouter getCaves(Registry<DensityFunction> registry) {
		return NoiseRouterData.m_zifycqqm(registry);
	}

	public static NoiseRouter getFloatingIslands(Registry<DensityFunction> registry) {
		return NoiseRouterData.m_zaglzone(registry);
	}
}
