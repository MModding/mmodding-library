package com.mmodding.mmodding_lib.library.worldgen.chunkgenerators.routers;

import net.minecraft.util.registry.Registry;
import net.minecraft.world.gen.DensityFunction;
import net.minecraft.world.gen.noise.NoiseRouter;

public class NoiseRouterBuilder {

	private final Registry<DensityFunction> registry;
	private final boolean largeBiomes;
	private final boolean amplified;

	private DensityFunction barrierNoise;
	private DensityFunction fluidLevelFloodNoise;
	private DensityFunction fluidLevelSpreadNoise;
	private DensityFunction lavaNoise;
	private DensityFunction temperature;
	private DensityFunction vegetation;
	private DensityFunction continentalness;
	private DensityFunction erosion;
	private DensityFunction depth;
	private DensityFunction weirdness;
	private DensityFunction initialNonJaggedDensity;
	private DensityFunction fullNoise;
	private DensityFunction veinToggle;
	private DensityFunction veinRidged;
	private DensityFunction veinGap;

	public NoiseRouterBuilder(
		Registry<DensityFunction> registry,
		Boolean largeBiomes,
		Boolean amplified,
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
		this.largeBiomes = largeBiomes;
		this.amplified = amplified;
		this.registry = registry;
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

	public static NoiseRouterBuilder of(Registry<DensityFunction> registry, NoiseRouter data) {
		return NoiseRouterBuilder.of(
			registry,
			false,
			false,
			data
		);
	}

	public static NoiseRouterBuilder of(Registry<DensityFunction> registry, boolean largeBiomes, boolean amplified, NoiseRouter data) {
		return new NoiseRouterBuilder(
			registry,
			largeBiomes,
			amplified,
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

	public NoiseRouterBuilder barrierNoise(DensityFunctionReplacementContext ctx) {
		this.barrierNoise = ctx.apply(this.barrierNoise, this.registry, OverworldParams.getInstance(this));
		return this;
	}

	public NoiseRouterBuilder fluidLevelFloodNoise(DensityFunctionReplacementContext ctx) {
		this.fluidLevelFloodNoise = ctx.apply(this.fluidLevelFloodNoise, this.registry, OverworldParams.getInstance(this));
		return this;
	}

	public NoiseRouterBuilder fluidLevelSpreadNoise(DensityFunctionReplacementContext ctx) {
		this.fluidLevelSpreadNoise = ctx.apply(this.fluidLevelSpreadNoise, this.registry, OverworldParams.getInstance(this));
		return this;
	}

	public NoiseRouterBuilder lavaNoise(DensityFunctionReplacementContext ctx) {
		this.lavaNoise = ctx.apply(this.lavaNoise, this.registry, OverworldParams.getInstance(this));
		return this;
	}

	public NoiseRouterBuilder temperature(DensityFunctionReplacementContext ctx) {
		this.temperature = ctx.apply(this.temperature, this.registry, OverworldParams.getInstance(this));
		return this;
	}

	public NoiseRouterBuilder vegetation(DensityFunctionReplacementContext ctx) {
		this.vegetation = ctx.apply(this.vegetation, this.registry, OverworldParams.getInstance(this));
		return this;
	}

	public NoiseRouterBuilder continentalness(DensityFunctionReplacementContext ctx) {
		this.continentalness = ctx.apply(this.continentalness, this.registry, OverworldParams.getInstance(this));
		return this;
	}

	public NoiseRouterBuilder erosion(DensityFunctionReplacementContext ctx) {
		this.erosion = ctx.apply(this.erosion, this.registry, OverworldParams.getInstance(this));
		return this;
	}

	public NoiseRouterBuilder depth(DensityFunctionReplacementContext ctx) {
		this.depth = ctx.apply(this.depth, this.registry, OverworldParams.getInstance(this));
		return this;
	}

	public NoiseRouterBuilder weirdness(DensityFunctionReplacementContext ctx) {
		this.weirdness = ctx.apply(this.weirdness, this.registry, OverworldParams.getInstance(this));
		return this;
	}

	public NoiseRouterBuilder initialNonJaggedDensity(DensityFunctionReplacementContext ctx) {
		this.initialNonJaggedDensity = ctx.apply(this.initialNonJaggedDensity, this.registry, OverworldParams.getInstance(this));
		return this;
	}

	public NoiseRouterBuilder fullNoise(DensityFunctionReplacementContext ctx) {
		this.fullNoise = ctx.apply(this.fullNoise, this.registry, OverworldParams.getInstance(this));
		return this;
	}

	public NoiseRouterBuilder veinToggle(DensityFunctionReplacementContext ctx) {
		this.veinToggle = ctx.apply(this.veinToggle, this.registry, OverworldParams.getInstance(this));
		return this;
	}

	public NoiseRouterBuilder veinRidged(DensityFunctionReplacementContext ctx) {
		this.veinRidged = ctx.apply(this.veinRidged, this.registry, OverworldParams.getInstance(this));
		return this;
	}

	public NoiseRouterBuilder veinGap(DensityFunctionReplacementContext ctx) {
		this.veinGap = ctx.apply(this.veinGap, this.registry, OverworldParams.getInstance(this));
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

	public record OverworldParams(boolean largeBiomes, boolean amplified) {

		public static OverworldParams getInstance(NoiseRouterBuilder builder) {
			return new OverworldParams(builder.largeBiomes, builder.amplified);
		}
	}

	public interface DensityFunctionReplacementContext {

		DensityFunction apply(DensityFunction old, Registry<DensityFunction> functions, OverworldParams params);
	}
}
