package com.mmodding.mmodding_lib.mixin.accessors;

import net.minecraft.world.biome.source.util.MultiNoiseUtil;
import net.minecraft.world.biome.source.util.OverworldBiomeParameters;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(OverworldBiomeParameters.class)
public interface OverworldBiomeParametersAccessor {

	@Accessor("FULL_RANGE")
	static MultiNoiseUtil.ParameterRange getFullRange() {
		throw new AssertionError();
	}

	@Accessor("TEMPERATURES")
	static MultiNoiseUtil.ParameterRange[] getTemperatures() {
		throw new AssertionError();
	}

	@Accessor("HUMIDITIES")
	static MultiNoiseUtil.ParameterRange[] getHumidities() {
		throw new AssertionError();
	}

	@Accessor("EROSIONS")
	static MultiNoiseUtil.ParameterRange[] getErosions() {
		throw new AssertionError();
	}

	@Accessor("FROZEN_TEMPERATURE")
	static MultiNoiseUtil.ParameterRange getFrozenTemperature() {
		throw new AssertionError();
	}

	@Accessor("UNFROZEN_TEMPERATURE")
	static MultiNoiseUtil.ParameterRange getUnfrozenTemperature() {
		throw new AssertionError();
	}

	@Accessor("MUSHROOM_FIELDS_CONTINENTALNESS")
	static MultiNoiseUtil.ParameterRange getOffCoastContinentalness() {
		throw new AssertionError();
	}

	@Accessor("DEEP_OCEAN_CONTINENTALNESS")
	static MultiNoiseUtil.ParameterRange getDeepOceanContinentalness() {
		throw new AssertionError();
	}

	@Accessor("OCEAN_CONTINENTALNESS")
	static MultiNoiseUtil.ParameterRange getOceanContinentalness() {
		throw new AssertionError();
	}

	@Accessor("COAST_CONTINENTALNESS")
	static MultiNoiseUtil.ParameterRange getCoastContinentalness() {
		throw new AssertionError();
	}

	@Accessor("INLAND_CONTINENTALNESS")
	static MultiNoiseUtil.ParameterRange getInlandContinentalness() {
		throw new AssertionError();
	}

	@Accessor("NEAR_INLAND_CONTINENTALNESS")
	static MultiNoiseUtil.ParameterRange getNearInlandContinentalness() {
		throw new AssertionError();
	}

	@Accessor("MID_INLAND_CONTINENTALNESS")
	static MultiNoiseUtil.ParameterRange getMidInlandContinentalness() {
		throw new AssertionError();
	}

	@Accessor("FAR_INLAND_CONTINENTALNESS")
	static MultiNoiseUtil.ParameterRange getFarInlandContinentalness() {
		throw new AssertionError();
	}
}
