package com.mmodding.mmodding_lib.mixin.accessors;

import net.minecraft.util.Holder;
import net.minecraft.util.math.noise.DoublePerlinNoiseSampler;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.RegistryKey;
import net.minecraft.world.gen.DensityFunction;
import net.minecraft.world.gen.noise.NoiseRouter;
import net.minecraft.world.gen.noise.NoiseRouterData;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(NoiseRouterData.class)
public interface NoiseRouterDataAccessor {

	@Accessor("ZERO")
	static RegistryKey<DensityFunction> getZeroKey() {
		throw new AssertionError();
	}

	@Accessor("Y")
	static RegistryKey<DensityFunction> getYKey() {
		throw new AssertionError();
	}

	@Accessor("SHIFT_X")
	static RegistryKey<DensityFunction> getShiftXKey() {
		throw new AssertionError();
	}

	@Accessor("SHIFT_Z")
	static RegistryKey<DensityFunction> getShiftZKey() {
		throw new AssertionError();
	}

	@Accessor("CAVES_ENTRANCES_OVERWORLD")
	static RegistryKey<DensityFunction> getCavesEntrances() {
		throw new AssertionError();
	}

	@Accessor("CAVES_NOODLE_OVERWORLD")
	static RegistryKey<DensityFunction> getCavesNoodles() {
		throw new AssertionError();
	}

	@Accessor("CAVES_PILLARS_OVERWORLD")
	static RegistryKey<DensityFunction> getCavesPillars() {
		throw new AssertionError();
	}

	@Invoker("method_41548")
	static void invokeMethod_41548(Registry<DensityFunction> registry, DensityFunction densityFunction, Holder<DensityFunction> holder, Holder<DensityFunction> holder2, RegistryKey<DensityFunction> firstRegistryKey, RegistryKey<DensityFunction> secondRegistryKey, RegistryKey<DensityFunction> thirdRegistryKey, RegistryKey<DensityFunction> fourthRegistryKey, RegistryKey<DensityFunction> fifthRegistryKey, boolean bl) {
		throw new AssertionError();
	}

	@Invoker("method_41551")
	static DensityFunction invokeMethod_41551(Registry<DensityFunction> registry, RegistryKey<DensityFunction> registryKey, DensityFunction densityFunction) {
		throw new AssertionError();
	}

	@Invoker("register")
	static Holder<DensityFunction> invokeRegister(Registry<DensityFunction> registry, RegistryKey<DensityFunction> registryKey, DensityFunction densityFunction) {
		throw new AssertionError();
	}

	@Invoker("getNoise")
	static Holder<DoublePerlinNoiseSampler.NoiseParameters> invokeGetNoise(RegistryKey<DoublePerlinNoiseSampler.NoiseParameters> key) {
		throw new AssertionError();
	}

	@Invoker("getFunction")
	static DensityFunction invokeGetFunction(Registry<DensityFunction> registry, RegistryKey<DensityFunction> key) {
		throw new AssertionError();
	}

	@Invoker("method_41547")
	static DensityFunction invokeMethod_41547(DensityFunction densityFunction) {
		throw new AssertionError();
	}

	@Invoker("underground")
	static DensityFunction invokeUnderground(Registry<DensityFunction> densityFunctionRegistry, DensityFunction densityFunction) {
		throw new AssertionError();
	}

	@Invoker("postProcess")
	static DensityFunction invokePostProcess(DensityFunction densityFunction) {
		throw new AssertionError();
	}

	@Invoker("method_42366")
	static DensityFunction invokeMethod_42366(boolean bl, DensityFunction densityFunction) {
		throw new AssertionError();
	}

	@Invoker("method_42363")
	static DensityFunction invokeMethod_42363(Registry<DensityFunction> registry, int i, int j) {
		throw new AssertionError();
	}

	@Invoker("method_42364")
	static DensityFunction invokeMethod_42364(DensityFunction densityFunction, int i, int j) {
		throw new AssertionError();
	}

	@Invoker("method_42367")
	static DensityFunction invokeMethod_42367(DensityFunction densityFunction) {
		throw new AssertionError();
	}

	@Invoker("method_44324")
	static NoiseRouter invokeMethod_44324() {
		throw new AssertionError();
	}

	@Invoker("splineWithBlending")
	static DensityFunction invokeSplineWithBlending(DensityFunction firstDensityFunction, DensityFunction secondDensityFunction) {
		throw new AssertionError();
	}

	@Invoker("noiseGradientDensity")
	static DensityFunction invokeNoiseGradientDensity(DensityFunction firstDensityFunction, DensityFunction secondDensityFunction) {
		throw new AssertionError();
	}

	@Invoker("yLimitedInterpolatable")
	static DensityFunction invokeYLimitedInterpolatable(DensityFunction input, DensityFunction whenInRange, int minInclusive, int maxInclusive, int maxRange) {
		throw new AssertionError();
	}

	@Invoker("method_42365")
	static DensityFunction invokeMethod_42365(DensityFunction densityFunction, int i, int j, int k, int l, double d, int m, int n, double e) {
		throw new AssertionError();
	}
}
