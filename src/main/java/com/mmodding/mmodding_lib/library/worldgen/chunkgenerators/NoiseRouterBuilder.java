package com.mmodding.mmodding_lib.library.worldgen.chunkgenerators;

import net.minecraft.world.gen.DensityFunction;
import net.minecraft.world.gen.noise.NoiseRouter;

public class NoiseRouterBuilder {

	public DensityFunction barrierNoise;
	public DensityFunction fluidLevelFloodNoise;
	public DensityFunction fluidLevelSpreadNoise;
	public DensityFunction lavaNoise;
	public DensityFunction temperature;
	public DensityFunction vegetation;
	public DensityFunction continentalness;
	public DensityFunction erosion;
	public DensityFunction depth;
	public DensityFunction weirdness;
	public DensityFunction initialNonJaggedDensity;
	public DensityFunction fullNoise;
	public DensityFunction veinToggle;
	public DensityFunction veinRidged;
	public DensityFunction veinGap;

	public NoiseRouterBuilder(
		DensityFunction barrierNoise,
		DensityFunction fluidLevelFloodNoise,
		DensityFunction fluidLevelSpreadNoise,
		DensityFunction lavaNoise,
		DensityFunction temperature,
		DensityFunction vegetation,
		DensityFunction continentalness,
		DensityFunction erosion,
		DensityFunction depth,
		DensityFunction weirdness,
		DensityFunction initialNonJaggedDensity,
		DensityFunction fullNoise,
		DensityFunction veinToggle,
		DensityFunction veinRidged,
		DensityFunction veinGap
	) {
		this.barrierNoise = barrierNoise;
		this.fluidLevelFloodNoise = fluidLevelFloodNoise;
		this.fluidLevelSpreadNoise = fluidLevelSpreadNoise;
		this.lavaNoise = lavaNoise;
		this.temperature = temperature;
		this.vegetation = vegetation;
		this.continentalness = continentalness;
		this.erosion = erosion;
		this.depth = depth;
		this.weirdness = weirdness;
		this.initialNonJaggedDensity = initialNonJaggedDensity;
		this.fullNoise = fullNoise;
		this.veinToggle = veinToggle;
		this.veinRidged = veinRidged;
		this.veinGap = veinGap;
	}

	public static NoiseRouterBuilder of(NoiseRouter data) {
		return new NoiseRouterBuilder(
			data.barrierNoise(),
			data.fluidLevelFloodNoise(),
			data.fluidLevelSpreadNoise(),
			data.lavaNoise(),
			data.temperature(),
			data.vegetation(),
			data.continentalness(),
			data.erosion(),
			data.depth(),
			data.weirdness(),
			data.initialNonJaggedDensity(),
			data.fullNoise(),
			data.veinToggle(),
			data.veinRidged(),
			data.veinGap()
		);
	}

	public NoiseRouterBuilder barrierNoise(DensityFunction barrierNoise) {
		this.barrierNoise = barrierNoise;
		return this;
	}

	public NoiseRouterBuilder fluidLevelFloodNoise(DensityFunction fluidLevelFloodNoise) {
		this.fluidLevelFloodNoise = fluidLevelFloodNoise;
		return this;
	}

	public NoiseRouterBuilder fluidLevelSpreadNoise(DensityFunction fluidLevelSpreadNoise) {
		this.fluidLevelSpreadNoise = fluidLevelSpreadNoise;
		return this;
	}

	public NoiseRouterBuilder lavaNoise(DensityFunction lavaNoise) {
		this.lavaNoise = lavaNoise;
		return this;
	}

	public NoiseRouterBuilder temperature(DensityFunction temperature) {
		this.temperature = temperature;
		return this;
	}

	public NoiseRouterBuilder vegetation(DensityFunction vegetation) {
		this.vegetation = vegetation;
		return this;
	}

	public NoiseRouterBuilder continentalness(DensityFunction continentalness) {
		this.continentalness = continentalness;
		return this;
	}

	public NoiseRouterBuilder erosion(DensityFunction erosion) {
		this.erosion = erosion;
		return this;
	}

	public NoiseRouterBuilder depth(DensityFunction depth) {
		this.depth = depth;
		return this;
	}

	public NoiseRouterBuilder weirdness(DensityFunction weirdness) {
		this.weirdness = weirdness;
		return this;
	}

	public NoiseRouterBuilder initialNonJaggedDensity(DensityFunction initialNonJaggedDensity) {
		this.initialNonJaggedDensity = initialNonJaggedDensity;
		return this;
	}

	public NoiseRouterBuilder fullNoise(DensityFunction fullNoise) {
		this.fullNoise = fullNoise;
		return this;
	}

	public NoiseRouterBuilder veinToggle(DensityFunction veinToggle) {
		this.veinToggle = veinToggle;
		return this;
	}

	public NoiseRouterBuilder veinRidged(DensityFunction veinRidged) {
		this.veinRidged = veinRidged;
		return this;
	}

	public NoiseRouterBuilder veinGap(DensityFunction veinGap) {
		this.veinGap = veinGap;
		return this;
	}

	public NoiseRouter build() {
		return new NoiseRouter(
			this.barrierNoise,
			this.fluidLevelFloodNoise,
			this.fluidLevelSpreadNoise,
			this.lavaNoise,
			this.temperature,
			this.vegetation,
			this.continentalness,
			this.erosion,
			this.depth,
			this.weirdness,
			this.initialNonJaggedDensity,
			this.fullNoise,
			this.veinToggle,
			this.veinRidged,
			this.veinGap
		);
	}
}
