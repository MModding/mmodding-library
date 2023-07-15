package com.mmodding.mmodding_lib.library.utils;

import com.mmodding.mmodding_lib.mixin.accessors.NoiseRouterDataAccessor;
import net.minecraft.util.Holder;
import net.minecraft.util.math.noise.DoublePerlinNoiseSampler;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.RegistryKey;
import net.minecraft.world.gen.DensityFunction;
import net.minecraft.world.gen.noise.NoiseRouter;

public class NoiseRouterUtils {

	public static Holder<DoublePerlinNoiseSampler.NoiseParameters> getNoise(RegistryKey<DoublePerlinNoiseSampler.NoiseParameters> key) {
		return NoiseRouterDataAccessor.getNoise(key);
	}

	public static DensityFunction getFunction(Registry<DensityFunction> registry, RegistryKey<DensityFunction> key) {
		return NoiseRouterDataAccessor.getFunction(registry, key);
	}

	public static NoiseRouter emptyNoiseRouter() {
		return NoiseRouterDataAccessor.m_wvnhpwvs();
	}
}
