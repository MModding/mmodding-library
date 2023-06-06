package com.mmodding.mmodding_lib.mixin.accessors;

import net.minecraft.world.biome.source.util.MultiNoiseUtil;
import net.minecraft.world.biome.source.util.OverworldBiomeParameters;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(OverworldBiomeParameters.class)
public interface OverworldBiomeParametersAccessor {

	@Accessor("FULL_RANGE")
	MultiNoiseUtil.ParameterRange getFullRange();

	@Accessor("TEMPERATURES")
	MultiNoiseUtil.ParameterRange[] getTemperatures();

	@Accessor("HUMIDITIES")
	MultiNoiseUtil.ParameterRange[] getHumidities();

	@Accessor("EROSIONS")
	MultiNoiseUtil.ParameterRange[] getErosions();

	@Accessor("FROZEN_TEMPERATURE")
	MultiNoiseUtil.ParameterRange getFrozenTemperature();

	@Accessor("UNFROZEN_TEMPERATURE")
	MultiNoiseUtil.ParameterRange getUnfrozenTemperature();

	@Accessor("MUSHROOM_FIELDS_CONTINENTALNESS")
	MultiNoiseUtil.ParameterRange getOffCoastContinentalness();

	@Accessor("DEEP_OCEAN_CONTINENTALNESS")
	MultiNoiseUtil.ParameterRange getDeepOceanContinentalness();

	@Accessor("OCEAN_CONTINENTALNESS")
	MultiNoiseUtil.ParameterRange getOceanContinentalness();

	@Accessor("COAST_CONTINENTALNESS")
	MultiNoiseUtil.ParameterRange getCoastContinentalness();

	@Accessor("INLAND_CONTINENTALNESS")
	MultiNoiseUtil.ParameterRange getInlandContinentalness();

	@Accessor("NEAR_INLAND_CONTINENTALNESS")
	MultiNoiseUtil.ParameterRange getNearInlandContinentalness();

	@Accessor("MID_INLAND_CONTINENTALNESS")
	MultiNoiseUtil.ParameterRange getMidInlandContinentalness();

	@Accessor("FAR_INLAND_CONTINENTALNESS")
	MultiNoiseUtil.ParameterRange getFarInlandContinentalness();
}
