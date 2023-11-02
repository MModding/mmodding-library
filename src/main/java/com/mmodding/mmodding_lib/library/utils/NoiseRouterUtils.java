package com.mmodding.mmodding_lib.library.utils;

import com.mmodding.mmodding_lib.mixin.accessors.NoiseRouterDataAccessor;
import net.minecraft.util.Holder;
import net.minecraft.util.math.noise.DoublePerlinNoiseSampler;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.RegistryKey;
import net.minecraft.world.gen.DensityFunction;
import net.minecraft.world.gen.noise.NoiseRouter;

// Several Methods Here Use Method Names From Minecraft 1.20.1 Yarn Mappings
public class NoiseRouterUtils {

	public static void registerSlopedCheeseFunction(Registry<DensityFunction> registry, DensityFunction jaggedNoise, Holder<DensityFunction> continents, Holder<DensityFunction> erosion, RegistryKey<DensityFunction> offsetKey, RegistryKey<DensityFunction> factorKey, RegistryKey<DensityFunction> jaggednessKey, RegistryKey<DensityFunction> depthKey, RegistryKey<DensityFunction> slopedCheeseKey, boolean amplified) {
		NoiseRouterDataAccessor.invokeMethod_41548(registry, jaggedNoise, continents, erosion, offsetKey, factorKey, jaggednessKey, depthKey, slopedCheeseKey, amplified);
	}

	public static DensityFunction registerAndGetHolder(Registry<DensityFunction> registry, RegistryKey<DensityFunction> registryKey, DensityFunction densityFunction) {
		return NoiseRouterDataAccessor.invokeMethod_41551(registry, registryKey, densityFunction);
	}

	public static Holder<DensityFunction> register(Registry<DensityFunction> registry, RegistryKey<DensityFunction> registryKey, DensityFunction densityFunction) {
		return NoiseRouterDataAccessor.invokeRegister(registry, registryKey, densityFunction);
	}

	public static Holder<DoublePerlinNoiseSampler.NoiseParameters> getNoise(RegistryKey<DoublePerlinNoiseSampler.NoiseParameters> key) {
		return NoiseRouterDataAccessor.invokeGetNoise(key);
	}

	public static DensityFunction getFunction(Registry<DensityFunction> registry, RegistryKey<DensityFunction> key) {
		return NoiseRouterDataAccessor.invokeGetFunction(registry, key);
	}

	public static DensityFunction createRidgesFoldedOverworldFunction(DensityFunction input) {
		return NoiseRouterDataAccessor.invokeMethod_41547(input);
	}

	public static DensityFunction createUndergroundCaves(Registry<DensityFunction> registry, DensityFunction slopedCheese) {
		return NoiseRouterDataAccessor.invokeUnderground(registry, slopedCheese);
	}

	public static DensityFunction applyBlendDensity(DensityFunction densityFunction) {
		return NoiseRouterDataAccessor.invokePostProcess(densityFunction);
	}

	public static DensityFunction applySurfaceSlides(boolean amplified, DensityFunction densityFunction) {
		return NoiseRouterDataAccessor.invokeMethod_42366(amplified, densityFunction);
	}

	public static DensityFunction applyCavesSlides(Registry<DensityFunction> registry, int minY, int maxY) {
		return NoiseRouterDataAccessor.invokeMethod_42363(registry, minY, maxY);
	}

	public static DensityFunction applyFloatingIslandsSlides(DensityFunction densityFunction, int minY, int maxY) {
		return NoiseRouterDataAccessor.invokeMethod_42364(densityFunction, minY, maxY);
	}

	public static DensityFunction applyEndSlides(DensityFunction densityFunction) {
		return NoiseRouterDataAccessor.invokeMethod_42367(densityFunction);
	}

	public static NoiseRouter emptyNoiseRouter() {
		return NoiseRouterDataAccessor.invokeMethod_44324();
	}

	public static DensityFunction applyBlending(DensityFunction densityFunction, DensityFunction blendOffset) {
		return NoiseRouterDataAccessor.invokeSplineWithBlending(densityFunction, blendOffset);
	}

	public static DensityFunction createInitialDensityFunction(DensityFunction factor, DensityFunction depth) {
		return NoiseRouterDataAccessor.invokeNoiseGradientDensity(factor, depth);
	}

	public static DensityFunction verticalRangeChoice(DensityFunction y, DensityFunction whenInRange, int minInclusive, int maxInclusive, int whenOutOfRange) {
		return NoiseRouterDataAccessor.invokeYLimitedInterpolatable(y, whenInRange, minInclusive, maxInclusive, whenOutOfRange);
	}

	public static DensityFunction applySlides(DensityFunction densityFunction, int minY, int maxY, int topRelativeMinY, int topRelativeMaxY, double topDensity, int bottomRelativeMinY, int bottomRelativeMaxY, double bottomDensity) {
		return NoiseRouterDataAccessor.invokeMethod_42365(densityFunction, minY, maxY, topRelativeMinY, topRelativeMaxY, topDensity, bottomRelativeMinY, bottomRelativeMaxY, bottomDensity);
	}
}
