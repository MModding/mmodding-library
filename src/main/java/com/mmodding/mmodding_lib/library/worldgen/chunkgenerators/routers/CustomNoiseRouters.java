package com.mmodding.mmodding_lib.library.worldgen.chunkgenerators.routers;

import com.mmodding.mmodding_lib.library.utils.NoiseRouterUtils;
import com.mmodding.mmodding_lib.library.worldgen.MModdingDensityFunctions;
import com.mmodding.mmodding_lib.library.worldgen.MModdingNoiseParameters;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.gen.DensityFunction;
import net.minecraft.world.gen.DensityFunctions;
import net.minecraft.world.gen.noise.NoiseRouter;

public class CustomNoiseRouters {

	public static NoiseRouter getSmallBiomes(Registry<DensityFunction> registry) {
		DensityFunction shiftX = NoiseRouterUtils.getFunction(registry, MModdingDensityFunctions.SHIFT_X);
		DensityFunction shiftZ = NoiseRouterUtils.getFunction(registry, MModdingDensityFunctions.SHIFT_Z);

		DensityFunction factor = NoiseRouterUtils.getFunction(registry, MModdingDensityFunctions.FACTOR_SMALL_BIOMES);
		DensityFunction depths = NoiseRouterUtils.getFunction(registry, MModdingDensityFunctions.DEPTH_SMALL_BIOMES);

		DensityFunction initialDensity = NoiseRouterUtils.createInitialDensityFunction(factor, depths);

		DensityFunction slopedCheese = NoiseRouterUtils.getFunction(registry, MModdingDensityFunctions.SLOPED_CHEESE_SMALL_BIOMES);

		DensityFunction caveEntrances = DensityFunctions.min(
			slopedCheese, DensityFunctions.multiply(DensityFunctions.constant(5.0), NoiseRouterUtils.getFunction(registry, MModdingDensityFunctions.CAVES_ENTRANCES))
		);

		DensityFunction underground = DensityFunctions.rangeChoice(
			slopedCheese, -1000000.0, 1.5625, caveEntrances, NoiseRouterUtils.createUndergroundCaves(registry, slopedCheese)
		);

		DensityFunction finalDensity = DensityFunctions.min(
			NoiseRouterUtils.applyBlendDensity(NoiseRouterUtils.applySurfaceSlides(false, underground)),
			NoiseRouterUtils.getFunction(registry, MModdingDensityFunctions.CAVES_NOODLES)
		);

		return DefaultNoiseRouters.Builders.getOverworld(registry, false, false)
			.temperature((old, functions, params) -> DensityFunctions.shiftedNoise2d(
				shiftX, shiftZ, 0.25, NoiseRouterUtils.getNoise(MModdingNoiseParameters.TEMPERATURE_SMALL)
			))
			.vegetation((old, functions, params) -> DensityFunctions.shiftedNoise2d(
				shiftX, shiftZ, 0.25, NoiseRouterUtils.getNoise(MModdingNoiseParameters.VEGETATION_SMALL)
			))
			.continentalness((old, functions, params) -> NoiseRouterUtils.getFunction(registry, MModdingDensityFunctions.CONTINENTS_SMALL_BIOMES))
			.erosion((old, functions, params) -> NoiseRouterUtils.getFunction(registry, MModdingDensityFunctions.EROSION_SMALL_BIOMES))
			.depth((old, functions, params) -> depths)
			.initialNonJaggedDensity((old, functions, params) -> NoiseRouterUtils.applySurfaceSlides(
				false,
				DensityFunctions
					.add(initialDensity, DensityFunctions.constant(-0.703125))
					.clamp(-64.0, 64.0)
			))
			.fullNoise((old, functions, params) -> finalDensity)
			.build();
	}

	public static class Builders {

		public static NoiseRouterBuilder getSmallBiomes(Registry<DensityFunction> registry) {
			return NoiseRouterBuilder.of(registry, CustomNoiseRouters.getSmallBiomes(registry));
		}
	}
}
