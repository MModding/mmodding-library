package com.mmodding.mmodding_lib.mixin.accessors;

import net.minecraft.util.math.noise.DoublePerlinNoiseSampler;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.RegistryKey;
import net.minecraft.world.gen.noise.BuiltinNoiseParameters;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(BuiltinNoiseParameters.class)
public interface BuiltinNoiseParametersAccessor {

	@Invoker("registerBiomeNoises")
	static void invokeRegisterBiomeNoises(Registry<DoublePerlinNoiseSampler.NoiseParameters> registry, int i, RegistryKey<DoublePerlinNoiseSampler.NoiseParameters> firstKey, RegistryKey<DoublePerlinNoiseSampler.NoiseParameters> secondKey, RegistryKey<DoublePerlinNoiseSampler.NoiseParameters> thirdKey, RegistryKey<DoublePerlinNoiseSampler.NoiseParameters> fourthKey) {
		throw new AssertionError();
	}
}
