package com.mmodding.mmodding_lib.library.worldgen;

import com.mmodding.mmodding_lib.library.utils.MModdingIdentifier;
import com.mmodding.mmodding_lib.library.utils.NoiseRouterUtils;
import com.mmodding.mmodding_lib.mixin.accessors.NoiseRouterDataAccessor;
import net.minecraft.util.Holder;
import net.minecraft.util.registry.BuiltinRegistries;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.RegistryKey;
import net.minecraft.world.gen.DensityFunction;
import net.minecraft.world.gen.DensityFunctions;
import net.minecraft.world.gen.noise.NoiseParametersKeys;

public class MModdingDensityFunctions {

	public static final RegistryKey<DensityFunction> ZERO = NoiseRouterDataAccessor.getZeroKey();
	public static final RegistryKey<DensityFunction> Y = NoiseRouterDataAccessor.getYKey();
	public static final RegistryKey<DensityFunction> SHIFT_X = NoiseRouterDataAccessor.getShiftXKey();
	public static final RegistryKey<DensityFunction> SHIFT_Z = NoiseRouterDataAccessor.getShiftZKey();

	public static final RegistryKey<DensityFunction> CAVES_ENTRANCES = NoiseRouterDataAccessor.getCavesEntrances();
	public static final RegistryKey<DensityFunction> CAVES_NOODLES = NoiseRouterDataAccessor.getCavesNoodles();
	public static final RegistryKey<DensityFunction> CAVES_PILLARS = NoiseRouterDataAccessor.getCavesPillars();

	public static final RegistryKey<DensityFunction> CONTINENTS_SMALL_BIOMES = MModdingDensityFunctions.createMModdingKey("small_biomes/continents");
	public static final RegistryKey<DensityFunction> EROSION_SMALL_BIOMES = MModdingDensityFunctions.createMModdingKey("small_biomes/erosion");
	public static final RegistryKey<DensityFunction> OFFSET_SMALL_BIOMES = MModdingDensityFunctions.createMModdingKey("small_biomes/offset");
	public static final RegistryKey<DensityFunction> FACTOR_SMALL_BIOMES = MModdingDensityFunctions.createMModdingKey("small_biomes/factor");
	public static final RegistryKey<DensityFunction> JAGGEDNESS_SMALL_BIOMES = MModdingDensityFunctions.createMModdingKey("small_biomes/jaggedness");
	public static final RegistryKey<DensityFunction> DEPTH_SMALL_BIOMES = MModdingDensityFunctions.createMModdingKey("small_biomes/depth");
	public static final RegistryKey<DensityFunction> SLOPED_CHEESE_SMALL_BIOMES = MModdingDensityFunctions.createMModdingKey("small_biomes/sloped_cheese");

	private static RegistryKey<DensityFunction> createMModdingKey(String path) {
		return RegistryKey.of(Registry.DENSITY_FUNCTION_WORLDGEN, new MModdingIdentifier(path));
	}

	static {
		DensityFunction jaggedDensityFunction = DensityFunctions.noise(NoiseRouterUtils.getNoise(NoiseParametersKeys.JAGGED), 1500.0, 0.0);

		DensityFunction shiftX = NoiseRouterUtils.getFunction(BuiltinRegistries.DENSITY_FUNCTION, MModdingDensityFunctions.SHIFT_X);
		DensityFunction shiftZ = NoiseRouterUtils.getFunction(BuiltinRegistries.DENSITY_FUNCTION, MModdingDensityFunctions.SHIFT_Z);

		Holder<DensityFunction> continents = NoiseRouterUtils.register(
			BuiltinRegistries.DENSITY_FUNCTION,
			MModdingDensityFunctions.CONTINENTS_SMALL_BIOMES,
			DensityFunctions.flatCache(DensityFunctions.shiftedNoise2d(shiftX, shiftZ, 0.25, NoiseRouterUtils.getNoise(MModdingNoiseParameters.CONTINENTALNESS_SMALL)))
		);

		Holder<DensityFunction> erosion = NoiseRouterUtils.register(
			BuiltinRegistries.DENSITY_FUNCTION,
			MModdingDensityFunctions.EROSION_SMALL_BIOMES,
			DensityFunctions.flatCache(DensityFunctions.shiftedNoise2d(shiftX, shiftZ, 0.25, NoiseRouterUtils.getNoise(MModdingNoiseParameters.EROSION_SMALL)))
		);

		NoiseRouterUtils.registerSlopedCheeseFunction(
			BuiltinRegistries.DENSITY_FUNCTION,
			jaggedDensityFunction,
			continents,
			erosion,
			MModdingDensityFunctions.OFFSET_SMALL_BIOMES,
			MModdingDensityFunctions.FACTOR_SMALL_BIOMES,
			MModdingDensityFunctions.JAGGEDNESS_SMALL_BIOMES,
			MModdingDensityFunctions.DEPTH_SMALL_BIOMES,
			MModdingDensityFunctions.SLOPED_CHEESE_SMALL_BIOMES,
			false
		);
	}
}
