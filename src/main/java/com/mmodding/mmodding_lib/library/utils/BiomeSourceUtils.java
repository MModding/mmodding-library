package com.mmodding.mmodding_lib.library.utils;

import com.google.common.collect.ImmutableList;
import com.mmodding.mmodding_lib.library.worldgen.AdvancedBiomeProvider;
import com.mmodding.mmodding_lib.mixin.accessors.OverworldBiomeParametersAccessor;
import com.mojang.datafixers.util.Pair;
import net.minecraft.util.Holder;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.RegistryKey;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.source.MultiNoiseBiomeSource;
import net.minecraft.world.biome.source.util.MultiNoiseUtil;
import com.google.common.collect.ImmutableList.Builder;
import net.minecraft.world.biome.source.util.OverworldBiomeParameters;

import java.util.function.Consumer;

public class BiomeSourceUtils {

	public static MultiNoiseBiomeSource.Preset createMultiNoisePreset(Identifier identifier, AdvancedBiomeProvider provider) {
		return new MultiNoiseBiomeSource.Preset(identifier, registry -> {
			Builder<Pair<MultiNoiseUtil.NoiseHypercube, Holder<Biome>>> builder = ImmutableList.builder();
			provider.provideBiomes(pair -> builder.add(pair.mapSecond(registry::getOrCreateHolderOrThrow)));
			return new MultiNoiseUtil.ParameterRangeList<>(builder.build());
		});
	}

	public static void addSurfaceBiomeTo(Consumer<Pair<MultiNoiseUtil.NoiseHypercube, RegistryKey<Biome>>> parameters,
		MultiNoiseUtil.ParameterRange temperature,
		MultiNoiseUtil.ParameterRange humidity,
		MultiNoiseUtil.ParameterRange continentalness,
		MultiNoiseUtil.ParameterRange erosion,
		MultiNoiseUtil.ParameterRange weirdness,
		float offset, RegistryKey<Biome> biome) {
		parameters.accept(Pair.of(
			MultiNoiseUtil.createNoiseHypercube(temperature, humidity, continentalness, erosion, MultiNoiseUtil.ParameterRange.of(0.0F), weirdness, offset), biome
		));
		parameters.accept(Pair.of(
			MultiNoiseUtil.createNoiseHypercube(temperature, humidity, continentalness, erosion, MultiNoiseUtil.ParameterRange.of(1.0F), weirdness, offset), biome
		));
	}

	public static class DefaultRanges {

		private static final OverworldBiomeParametersAccessor INSTANCE = (OverworldBiomeParametersAccessor) (Object) new OverworldBiomeParameters();

		public static MultiNoiseUtil.ParameterRange fullRange() {
			return INSTANCE.getFullRange();
		}

		public static MultiNoiseUtil.ParameterRange[] temperatures() {
			return INSTANCE.getTemperatures();
		}

		public static MultiNoiseUtil.ParameterRange[] humidities() {
			return INSTANCE.getHumidities();
		}

		public static MultiNoiseUtil.ParameterRange[] erosions() {
			return INSTANCE.getErosions();
		}

		public static MultiNoiseUtil.ParameterRange frozenTemperature() {
			return INSTANCE.getFrozenTemperature();
		}

		public static MultiNoiseUtil.ParameterRange unfrozenTemperature() {
			return INSTANCE.getUnfrozenTemperature();
		}

		public static MultiNoiseUtil.ParameterRange offCoastContinentalness() {
			return INSTANCE.getOffCoastContinentalness();
		}

		public static MultiNoiseUtil.ParameterRange deepOceanContinentalness() {
			return INSTANCE.getDeepOceanContinentalness();
		}

		public static MultiNoiseUtil.ParameterRange oceanContinentalness() {
			return INSTANCE.getOceanContinentalness();
		}

		public static MultiNoiseUtil.ParameterRange coastContinentalness() {
			return INSTANCE.getCoastContinentalness();
		}

		public static MultiNoiseUtil.ParameterRange inlandContinentalness() {
			return INSTANCE.getInlandContinentalness();
		}

		public static MultiNoiseUtil.ParameterRange nearInlandContinentalness() {
			return INSTANCE.getNearInlandContinentalness();
		}

		public static MultiNoiseUtil.ParameterRange midInlandContinentalness() {
			return INSTANCE.getMidInlandContinentalness();
		}

		public static MultiNoiseUtil.ParameterRange farInlandContinentalness() {
			return INSTANCE.getFarInlandContinentalness();
		}
	}
}
