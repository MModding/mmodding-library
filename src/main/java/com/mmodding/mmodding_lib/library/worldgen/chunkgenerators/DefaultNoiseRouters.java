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

	public static class Builders {

		public static NoiseRouterBuilder getOverworld(Registry<DensityFunction> registry, boolean largeBiome, boolean amplified) {
			return NoiseRouterBuilder.of(DefaultNoiseRouters.getOverworld(registry, largeBiome, amplified));
		}

		public static NoiseRouterBuilder getNether(Registry<DensityFunction> registry) {
			return NoiseRouterBuilder.of(DefaultNoiseRouters.getNether(registry));
		}

		public static NoiseRouterBuilder getEnd(Registry<DensityFunction> registry) {
			return NoiseRouterBuilder.of(DefaultNoiseRouters.getEnd(registry));
		}

		public static NoiseRouterBuilder getCaves(Registry<DensityFunction> registry) {
			return NoiseRouterBuilder.of(DefaultNoiseRouters.getCaves(registry));
		}

		public static NoiseRouterBuilder getFloatingIslands(Registry<DensityFunction> registry) {
			return NoiseRouterBuilder.of(DefaultNoiseRouters.getFloatingIslands(registry));
		}
	}
}
