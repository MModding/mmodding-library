package com.mmodding.mmodding_lib.mixin.accessors;

import net.minecraft.util.Holder;
import net.minecraft.util.math.noise.DoublePerlinNoiseSampler;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.RegistryKey;
import net.minecraft.world.gen.DensityFunction;
import net.minecraft.world.gen.noise.NoiseRouter;
import net.minecraft.world.gen.noise.NoiseRouterData;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(NoiseRouterData.class)
public interface NoiseRouterDataAccessor {

	@Invoker("getNoise")
	static Holder<DoublePerlinNoiseSampler.NoiseParameters> getNoise(RegistryKey<DoublePerlinNoiseSampler.NoiseParameters> key) {
		return null;
	}

	@Invoker("getFunction")
	static DensityFunction getFunction(Registry<DensityFunction> registry, RegistryKey<DensityFunction> key) {
		return null;
	}

	@Invoker("m_wvnhpwvs")
	static NoiseRouter m_wvnhpwvs() {
		return null;
	}
}
