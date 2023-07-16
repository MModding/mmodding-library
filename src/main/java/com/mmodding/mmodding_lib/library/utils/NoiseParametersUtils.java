package com.mmodding.mmodding_lib.library.utils;

import com.mmodding.mmodding_lib.mixin.accessors.BuiltinNoiseParametersAccessor;
import net.minecraft.util.math.noise.DoublePerlinNoiseSampler;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.RegistryKey;

public class NoiseParametersUtils {

	public static void registerGroupedBiomeNoises(Registry<DoublePerlinNoiseSampler.NoiseParameters> registry, int octaveOffset, RegistryKey<DoublePerlinNoiseSampler.NoiseParameters> temperatureKey, RegistryKey<DoublePerlinNoiseSampler.NoiseParameters> vegetationKey, RegistryKey<DoublePerlinNoiseSampler.NoiseParameters> continentalnessKey, RegistryKey<DoublePerlinNoiseSampler.NoiseParameters> erosionKey) {
		BuiltinNoiseParametersAccessor.registerBiomeNoises(registry, octaveOffset, temperatureKey, vegetationKey, continentalnessKey, erosionKey);
	}
}
