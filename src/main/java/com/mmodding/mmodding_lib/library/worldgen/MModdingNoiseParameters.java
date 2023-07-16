package com.mmodding.mmodding_lib.library.worldgen;

import com.mmodding.mmodding_lib.library.utils.MModdingIdentifier;
import com.mmodding.mmodding_lib.library.utils.NoiseParametersUtils;
import net.minecraft.util.math.noise.DoublePerlinNoiseSampler;
import net.minecraft.util.registry.BuiltinRegistries;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.RegistryKey;

public class MModdingNoiseParameters {

	public static final RegistryKey<DoublePerlinNoiseSampler.NoiseParameters> TEMPERATURE_SMALL = MModdingNoiseParameters.createMModdingKey("temperature_small");
	public static final RegistryKey<DoublePerlinNoiseSampler.NoiseParameters> VEGETATION_SMALL = MModdingNoiseParameters.createMModdingKey("vegetation_small");
	public static final RegistryKey<DoublePerlinNoiseSampler.NoiseParameters> CONTINENTALNESS_SMALL = MModdingNoiseParameters.createMModdingKey("continentalness_small");
	public static final RegistryKey<DoublePerlinNoiseSampler.NoiseParameters> EROSION_SMALL = MModdingNoiseParameters.createMModdingKey("erosion_small");

	private static RegistryKey<DoublePerlinNoiseSampler.NoiseParameters> createMModdingKey(String path) {
		return RegistryKey.of(Registry.NOISE_KEY, new MModdingIdentifier(path));
	}

	public static void initialize() {
		NoiseParametersUtils.registerGroupedBiomeNoises(
			BuiltinRegistries.NOISE_PARAMETERS,
			2,
			MModdingNoiseParameters.TEMPERATURE_SMALL,
			MModdingNoiseParameters.VEGETATION_SMALL,
			MModdingNoiseParameters.CONTINENTALNESS_SMALL,
			MModdingNoiseParameters.EROSION_SMALL
		);
	}
}
