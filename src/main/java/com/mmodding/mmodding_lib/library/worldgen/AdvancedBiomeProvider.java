package com.mmodding.mmodding_lib.library.worldgen;

import com.mmodding.mmodding_lib.library.utils.BiomeSourceUtils;
import com.mojang.datafixers.util.Pair;
import net.minecraft.util.registry.RegistryKey;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.source.util.MultiNoiseUtil;

import java.util.function.Consumer;

public interface AdvancedBiomeProvider {

	RegistryKey<Biome>[][] offCoastBiomes();

	RegistryKey<Biome>[][] oceanBiomes();

	RegistryKey<Biome>[][] middleBiomes();

	RegistryKey<Biome>[] hotBiomes();

	RegistryKey<Biome>[] hotBiomesVariant();

	RegistryKey<Biome>[][] middleBiomesVariant();

	RegistryKey<Biome> [][] peakBiomes();

	RegistryKey<Biome> [][] peakBiomesVariant();

	RegistryKey<Biome>[][] plateauBiomes();

	RegistryKey<Biome>[][] plateauBiomesVariant();

	RegistryKey<Biome>[][] slopeBiomes();

	RegistryKey<Biome>[][] windsweptBiomes();

	RegistryKey<Biome>[] beachBiomes();

	default MultiNoiseUtil.ParameterRange fullRange() {
		return BiomeSourceUtils.DefaultRanges.fullRange();
	}

	default MultiNoiseUtil.ParameterRange[] temperatures() {
		return BiomeSourceUtils.DefaultRanges.temperatures();
	}

	default MultiNoiseUtil.ParameterRange[] humidities() {
		return BiomeSourceUtils.DefaultRanges.humidities();
	}

	default MultiNoiseUtil.ParameterRange[] erosions() {
		return BiomeSourceUtils.DefaultRanges.erosions();
	}

	default MultiNoiseUtil.ParameterRange frozenTemperature() {
		return BiomeSourceUtils.DefaultRanges.frozenTemperature();
	}

	default MultiNoiseUtil.ParameterRange unfrozenTemperature() {
		return BiomeSourceUtils.DefaultRanges.unfrozenTemperature();
	}

	default MultiNoiseUtil.ParameterRange offCoastContinentalness() {
		return BiomeSourceUtils.DefaultRanges.offCoastContinentalness();
	}

	default MultiNoiseUtil.ParameterRange deepOceanContinentalness() {
		return BiomeSourceUtils.DefaultRanges.deepOceanContinentalness();
	}

	default MultiNoiseUtil.ParameterRange oceanContinentalness() {
		return BiomeSourceUtils.DefaultRanges.oceanContinentalness();
	}

	default MultiNoiseUtil.ParameterRange coastContinentalness() {
		return BiomeSourceUtils.DefaultRanges.coastContinentalness();
	}

	default MultiNoiseUtil.ParameterRange inlandContinentalness() {
		return BiomeSourceUtils.DefaultRanges.inlandContinentalness();
	}

	default MultiNoiseUtil.ParameterRange nearInlandContinentalness() {
		return BiomeSourceUtils.DefaultRanges.nearInlandContinentalness();
	}

	default MultiNoiseUtil.ParameterRange midInlandContinentalness() {
		return BiomeSourceUtils.DefaultRanges.midInlandContinentalness();
	}

	default MultiNoiseUtil.ParameterRange farInlandContinentalness() {
		return BiomeSourceUtils.DefaultRanges.farInlandContinentalness();
	}

	default RegistryKey<Biome> pickMiddle(int temperature, int humidity, MultiNoiseUtil.ParameterRange weirdness) {
		if (weirdness.max() < 0L) {
			return this.middleBiomes()[temperature][humidity];
		} else {
			RegistryKey<Biome> registryKey = this.middleBiomesVariant()[temperature][humidity];
			return registryKey == null ? this.middleBiomes()[temperature][humidity] : registryKey;
		}
	}

	default RegistryKey<Biome> pickMiddleOrHot(int temperature, int humidity, MultiNoiseUtil.ParameterRange weirdness) {
		return temperature == 4 ? this.pickHot(humidity, weirdness) : this.pickMiddle(temperature, humidity, weirdness);
	}

	default RegistryKey<Biome> pickMiddleOrHotOrCold(int temperature, int humidity, MultiNoiseUtil.ParameterRange weirdness) {
		return temperature == 0 ? this.pickSlope(temperature, humidity, weirdness) : this.pickMiddleOrHot(temperature, humidity, weirdness);
	}

	default RegistryKey<Biome> pickWindsweptCoast(int temperature, int humidity, MultiNoiseUtil.ParameterRange weirdness) {
		return weirdness.max() >= 0L ? this.pickMiddle(temperature, humidity, weirdness) : this.pickBeach(temperature);
	}

	default RegistryKey<Biome> pickBeach(int temperature) {
		return this.beachBiomes()[temperature];
	}

	default RegistryKey<Biome> pickHot(int humidity, MultiNoiseUtil.ParameterRange weirdness) {
		if (weirdness.max() < 2L) {
			return this.hotBiomes()[humidity];
		} else {
			RegistryKey<Biome> registryKey = this.hotBiomesVariant()[humidity];
			return registryKey == null ? this.hotBiomes()[humidity] : registryKey;
		}
	}

	default RegistryKey<Biome> pickPlateau(int temperature, int humidity, MultiNoiseUtil.ParameterRange weirdness) {
		if (weirdness.max() < 0L) {
			return this.plateauBiomes()[temperature][humidity];
		} else {
			RegistryKey<Biome> registryKey = this.plateauBiomesVariant()[temperature][humidity];
			return registryKey == null ? this.plateauBiomes()[temperature][humidity] : registryKey;
		}
	}

	default RegistryKey<Biome> pickPeak(int temperature, int humidity, MultiNoiseUtil.ParameterRange weirdness) {
		if (weirdness.max() < 0L) {
			return this.peakBiomes()[temperature][humidity];
		} else {
			RegistryKey<Biome> registryKey = this.peakBiomesVariant()[temperature][humidity];
			return registryKey == null ? this.peakBiomes()[temperature][humidity] : registryKey;
		}
	}

	default RegistryKey<Biome> pickSlope(int temperature, int humidity, MultiNoiseUtil.ParameterRange weirdness) {
		if (temperature >= 3) {
			return this.pickPlateau(temperature, humidity, weirdness);
		} else {
			return this.slopeBiomes()[temperature][humidity];
		}
	}

	default RegistryKey<Biome> pickWindswept(int temperature, int humidity, MultiNoiseUtil.ParameterRange weirdness) {
		RegistryKey<Biome> registryKey = this.windsweptBiomes()[temperature][humidity];
		return registryKey == null ? this.pickMiddle(temperature, humidity, weirdness) : registryKey;
	}

	default void provideBiomes(Consumer<Pair<MultiNoiseUtil.NoiseHypercube, RegistryKey<Biome>>> biomeEntryConsumer) {
		this.provideOffCoast(biomeEntryConsumer);
		this.provideInland(biomeEntryConsumer);
		this.provideUnderground(biomeEntryConsumer);
	}

	default void provideOffCoast(Consumer<Pair<MultiNoiseUtil.NoiseHypercube, RegistryKey<Biome>>> parameters) {
		for(int i = 0; i < BiomeSourceUtils.DefaultRanges.temperatures().length; ++i) {
			MultiNoiseUtil.ParameterRange parameterRange = BiomeSourceUtils.DefaultRanges.temperatures()[i];
			BiomeSourceUtils.addSurfaceBiomeTo(
				parameters,
				parameterRange,
				this.fullRange(),
				this.offCoastContinentalness(),
				this.fullRange(),
				this.fullRange(),
				0.0F,
				this.offCoastBiomes()[0][i]
			);
			BiomeSourceUtils.addSurfaceBiomeTo(
				parameters,
				parameterRange,
				this.fullRange(),
				this.deepOceanContinentalness(),
				this.fullRange(),
				this.fullRange(),
				0.0F,
				this.oceanBiomes()[0][i]
			);
			BiomeSourceUtils.addSurfaceBiomeTo(
				parameters,
				parameterRange,
				this.fullRange(),
				this.oceanContinentalness(),
				this.fullRange(),
				this.fullRange(),
				0.0F,
				this.oceanBiomes()[1][i]
			);
		}
	}

	default void provideInland(Consumer<Pair<MultiNoiseUtil.NoiseHypercube, RegistryKey<Biome>>> parameters) {
		this.provideMid(parameters, MultiNoiseUtil.ParameterRange.of(-1.0F, -0.93333334F));
		this.provideHigh(parameters, MultiNoiseUtil.ParameterRange.of(-0.93333334F, -0.7666667F));
		this.providePeaks(parameters, MultiNoiseUtil.ParameterRange.of(-0.7666667F, -0.56666666F));
		this.provideHigh(parameters, MultiNoiseUtil.ParameterRange.of(-0.56666666F, -0.4F));
		this.provideMid(parameters, MultiNoiseUtil.ParameterRange.of(-0.4F, -0.26666668F));
		this.provideLow(parameters, MultiNoiseUtil.ParameterRange.of(-0.26666668F, -0.05F));
		this.provideValleys(parameters, MultiNoiseUtil.ParameterRange.of(-0.05F, 0.05F));
		this.provideLow(parameters, MultiNoiseUtil.ParameterRange.of(0.05F, 0.26666668F));
		this.provideMid(parameters, MultiNoiseUtil.ParameterRange.of(0.26666668F, 0.4F));
		this.provideHigh(parameters, MultiNoiseUtil.ParameterRange.of(0.4F, 0.56666666F));
		this.providePeaks(parameters, MultiNoiseUtil.ParameterRange.of(0.56666666F, 0.7666667F));
		this.provideHigh(parameters, MultiNoiseUtil.ParameterRange.of(0.7666667F, 0.93333334F));
		this.provideMid(parameters, MultiNoiseUtil.ParameterRange.of(0.93333334F, 1.0F));
	}

	default void providePeaks(Consumer<Pair<MultiNoiseUtil.NoiseHypercube, RegistryKey<Biome>>> parameters, MultiNoiseUtil.ParameterRange weirdness) {
		for(int temperatureIndex = 0; temperatureIndex < this.temperatures().length; temperatureIndex++) {

			MultiNoiseUtil.ParameterRange temperature = this.temperatures()[temperatureIndex];

			for(int humidityIndex = 0; humidityIndex < this.humidities().length; humidityIndex++) {

				MultiNoiseUtil.ParameterRange humidity = this.humidities()[humidityIndex];
				RegistryKey<Biome> registryKey = this.pickMiddle(temperatureIndex, humidityIndex, weirdness);
				RegistryKey<Biome> registryKey2 = this.pickMiddleOrHot(temperatureIndex, humidityIndex, weirdness);
				RegistryKey<Biome> registryKey3 = this.pickMiddleOrHotOrCold(temperatureIndex, humidityIndex, weirdness);
				RegistryKey<Biome> registryKey4 = this.pickPlateau(temperatureIndex, humidityIndex, weirdness);
				RegistryKey<Biome> registryKey5 = this.pickWindswept(temperatureIndex, humidityIndex, weirdness);
				RegistryKey<Biome> registryKey6 = this.pickPeak(temperatureIndex, humidityIndex, weirdness);

				BiomeSourceUtils.addSurfaceBiomeTo(
					parameters, temperature, humidity,
					MultiNoiseUtil.ParameterRange.combine(this.coastContinentalness(), this.farInlandContinentalness()),
					this.erosions()[0], weirdness, 0.0F, registryKey6
				);
				BiomeSourceUtils.addSurfaceBiomeTo(
					parameters, temperature, humidity,
					MultiNoiseUtil.ParameterRange.combine(this.coastContinentalness(), this.nearInlandContinentalness()),
					this.erosions()[1], weirdness, 0.0F, registryKey3
				);
				BiomeSourceUtils.addSurfaceBiomeTo(
					parameters, temperature, humidity,
					MultiNoiseUtil.ParameterRange.combine(this.midInlandContinentalness(), this.farInlandContinentalness()),
					this.erosions()[1], weirdness, 0.0F, registryKey6
				);
				BiomeSourceUtils.addSurfaceBiomeTo(
					parameters, temperature, humidity,
					MultiNoiseUtil.ParameterRange.combine(this.coastContinentalness(), this.nearInlandContinentalness()),
					MultiNoiseUtil.ParameterRange.combine(this.erosions()[2], this.erosions()[3]),
					weirdness, 0.0F, registryKey
				);
				BiomeSourceUtils.addSurfaceBiomeTo(
					parameters, temperature, humidity,
					MultiNoiseUtil.ParameterRange.combine(this.midInlandContinentalness(), this.farInlandContinentalness()),
					this.erosions()[2], weirdness, 0.0F, registryKey4
				);
				BiomeSourceUtils.addSurfaceBiomeTo(
					parameters, temperature, humidity,
					this.midInlandContinentalness(), this.erosions()[3],
					weirdness, 0.0F, registryKey2
				);
				BiomeSourceUtils.addSurfaceBiomeTo(
					parameters, temperature, humidity,
					this.farInlandContinentalness(), this.erosions()[3],
					weirdness, 0.0F, registryKey4
				);
				BiomeSourceUtils.addSurfaceBiomeTo(
					parameters, temperature, humidity,
					MultiNoiseUtil.ParameterRange.combine(this.coastContinentalness(), this.farInlandContinentalness()),
					this.erosions()[4], weirdness, 0.0F, registryKey
				);
				BiomeSourceUtils.addSurfaceBiomeTo(
					parameters, temperature, humidity,
					MultiNoiseUtil.ParameterRange.combine(this.coastContinentalness(), this.nearInlandContinentalness()),
					this.erosions()[5], weirdness, 0.0F, registryKey5
				);
				BiomeSourceUtils.addSurfaceBiomeTo(
					parameters, temperature, humidity,
					MultiNoiseUtil.ParameterRange.combine(this.midInlandContinentalness(), this.farInlandContinentalness()),
					this.erosions()[5], weirdness, 0.0F, registryKey5
				);
				BiomeSourceUtils.addSurfaceBiomeTo(
					parameters, temperature, humidity,
					MultiNoiseUtil.ParameterRange.combine(this.coastContinentalness(), this.farInlandContinentalness()),
					this.erosions()[6], weirdness, 0.0F, registryKey
				);
			}
		}
	}

	default void provideHigh(Consumer<Pair<MultiNoiseUtil.NoiseHypercube, RegistryKey<Biome>>> parameters, MultiNoiseUtil.ParameterRange weirdness) {
		for(int temperatureIndex = 0; temperatureIndex < this.temperatures().length; temperatureIndex++) {

			MultiNoiseUtil.ParameterRange temperature = this.temperatures()[temperatureIndex];

			for(int humidityIndex = 0; humidityIndex < this.humidities().length; humidityIndex++) {

				MultiNoiseUtil.ParameterRange humidity = this.humidities()[humidityIndex];
				RegistryKey<Biome> registryKey = this.pickMiddle(temperatureIndex, humidityIndex, weirdness);
				RegistryKey<Biome> registryKey2 = this.pickMiddleOrHot(temperatureIndex, humidityIndex, weirdness);
				RegistryKey<Biome> registryKey3 = this.pickMiddleOrHotOrCold(temperatureIndex, humidityIndex, weirdness);
				RegistryKey<Biome> registryKey4 = this.pickPlateau(temperatureIndex, humidityIndex, weirdness);
				RegistryKey<Biome> registryKey5 = this.pickWindswept(temperatureIndex, humidityIndex, weirdness);
				RegistryKey<Biome> registryKey6 = this.pickSlope(temperatureIndex, humidityIndex, weirdness);
				RegistryKey<Biome> registryKey7 = this.pickPeak(temperatureIndex, humidityIndex, weirdness);

				BiomeSourceUtils.addSurfaceBiomeTo(
					parameters, temperature, humidity, this.coastContinentalness(),
					MultiNoiseUtil.ParameterRange.combine(this.erosions()[0], this.erosions()[1]),
					weirdness, 0.0F, registryKey
				);
				BiomeSourceUtils.addSurfaceBiomeTo(
					parameters, temperature, humidity,
					this.nearInlandContinentalness(), this.erosions()[0],
					weirdness, 0.0F, registryKey6
				);
				BiomeSourceUtils.addSurfaceBiomeTo(
					parameters, temperature, humidity,
					MultiNoiseUtil.ParameterRange.combine(this.midInlandContinentalness(), this.farInlandContinentalness()),
					this.erosions()[0], weirdness, 0.0F, registryKey7
				);
				BiomeSourceUtils.addSurfaceBiomeTo(
					parameters, temperature, humidity,
					this.nearInlandContinentalness(), this.erosions()[1],
					weirdness, 0.0F, registryKey3
				);
				BiomeSourceUtils.addSurfaceBiomeTo(
					parameters, temperature, humidity,
					MultiNoiseUtil.ParameterRange.combine(this.midInlandContinentalness(), this.farInlandContinentalness()),
					this.erosions()[1], weirdness, 0.0F, registryKey6
				);
				BiomeSourceUtils.addSurfaceBiomeTo(
					parameters, temperature, humidity,
					MultiNoiseUtil.ParameterRange.combine(this.coastContinentalness(), this.nearInlandContinentalness()),
					MultiNoiseUtil.ParameterRange.combine(this.erosions()[2], this.erosions()[3]),
					weirdness, 0.0F, registryKey
				);
				BiomeSourceUtils.addSurfaceBiomeTo(
					parameters, temperature, humidity,
					MultiNoiseUtil.ParameterRange.combine(this.midInlandContinentalness(), this.farInlandContinentalness()),
					this.erosions()[2], weirdness, 0.0F, registryKey4
				);
				BiomeSourceUtils.addSurfaceBiomeTo(
					parameters, temperature, humidity,
					this.midInlandContinentalness(), this.erosions()[3],
					weirdness, 0.0F, registryKey2
				);
				BiomeSourceUtils.addSurfaceBiomeTo(
					parameters, temperature, humidity,
					this.farInlandContinentalness(), this.erosions()[3],
					weirdness, 0.0F, registryKey4
				);
				BiomeSourceUtils.addSurfaceBiomeTo(
					parameters, temperature, humidity,
					MultiNoiseUtil.ParameterRange.combine(this.coastContinentalness(), this.farInlandContinentalness()),
					this.erosions()[4], weirdness, 0.0F, registryKey
				);
				BiomeSourceUtils.addSurfaceBiomeTo(
					parameters, temperature, humidity,
					MultiNoiseUtil.ParameterRange.combine(this.coastContinentalness(), this.nearInlandContinentalness()),
					this.erosions()[5], weirdness, 0.0F, registryKey
				);
				BiomeSourceUtils.addSurfaceBiomeTo(
					parameters, temperature, humidity,
					MultiNoiseUtil.ParameterRange.combine(this.midInlandContinentalness(), this.farInlandContinentalness()),
					this.erosions()[5], weirdness, 0.0F, registryKey5
				);
				BiomeSourceUtils.addSurfaceBiomeTo(
					parameters, temperature, humidity,
					MultiNoiseUtil.ParameterRange.combine(this.coastContinentalness(), this.farInlandContinentalness()),
					this.erosions()[6], weirdness, 0.0F, registryKey
				);
			}
		}
	}

	default void provideMid(Consumer<Pair<MultiNoiseUtil.NoiseHypercube, RegistryKey<Biome>>> parameters, MultiNoiseUtil.ParameterRange weirdness) {
		for(int temperatureIndex = 0; temperatureIndex < this.temperatures().length; temperatureIndex++) {

			MultiNoiseUtil.ParameterRange temperature = this.temperatures()[temperatureIndex];

			for(int humidityIndex = 0; humidityIndex < this.humidities().length; humidityIndex++) {

				MultiNoiseUtil.ParameterRange humidity = this.humidities()[humidityIndex];
				RegistryKey<Biome> registryKey = this.pickMiddle(temperatureIndex, humidityIndex, weirdness);
				RegistryKey<Biome> registryKey2 = this.pickMiddleOrHot(temperatureIndex, humidityIndex, weirdness);
				RegistryKey<Biome> registryKey3 = this.pickMiddleOrHotOrCold(temperatureIndex, humidityIndex, weirdness);
				RegistryKey<Biome> registryKey4 = this.pickWindswept(temperatureIndex, humidityIndex, weirdness);
				RegistryKey<Biome> registryKey5 = this.pickPlateau(temperatureIndex, humidityIndex, weirdness);
				RegistryKey<Biome> registryKey6 = this.pickBeach(temperatureIndex);
				RegistryKey<Biome> registryKey7 = this.pickWindsweptCoast(temperatureIndex, humidityIndex, weirdness);
				RegistryKey<Biome> registryKey8 = this.pickSlope(temperatureIndex, humidityIndex, weirdness);

				BiomeSourceUtils.addSurfaceBiomeTo(
					parameters, temperature, humidity,
					MultiNoiseUtil.ParameterRange.combine(this.nearInlandContinentalness(), this.farInlandContinentalness()),
					this.erosions()[0], weirdness, 0.0F, registryKey8
				);
				BiomeSourceUtils.addSurfaceBiomeTo(
					parameters, temperature, humidity,
					MultiNoiseUtil.ParameterRange.combine(this.nearInlandContinentalness(), this.midInlandContinentalness()),
					this.erosions()[1], weirdness, 0.0F, registryKey3
				);
				BiomeSourceUtils.addSurfaceBiomeTo(
					parameters, temperature, humidity,
					this.farInlandContinentalness(), this.erosions()[1],
					weirdness, 0.0F, temperatureIndex == 0 ? registryKey8 : registryKey5
				);
				BiomeSourceUtils.addSurfaceBiomeTo(
					parameters, temperature, humidity,
					this.nearInlandContinentalness(), this.erosions()[2],
					weirdness, 0.0F, registryKey
				);
				BiomeSourceUtils.addSurfaceBiomeTo(
					parameters, temperature, humidity,
					this.midInlandContinentalness(), this.erosions()[2],
					weirdness, 0.0F, registryKey2
				);
				BiomeSourceUtils.addSurfaceBiomeTo(
					parameters, temperature, humidity,
					this.farInlandContinentalness(), this.erosions()[2],
					weirdness, 0.0F, registryKey5
				);
				BiomeSourceUtils.addSurfaceBiomeTo(
					parameters, temperature, humidity,
					MultiNoiseUtil.ParameterRange.combine(this.coastContinentalness(), this.nearInlandContinentalness()),
					this.erosions()[3], weirdness, 0.0F, registryKey
				);
				BiomeSourceUtils.addSurfaceBiomeTo(
					parameters, temperature, humidity,
					MultiNoiseUtil.ParameterRange.combine(this.midInlandContinentalness(), this.farInlandContinentalness()),
					this.erosions()[3], weirdness, 0.0F, registryKey2
				);

				if (weirdness.max() < 0L) {
					BiomeSourceUtils.addSurfaceBiomeTo(
						parameters, temperature, humidity,
						this.coastContinentalness(), this.erosions()[4],
						weirdness, 0.0F, registryKey6
					);
					BiomeSourceUtils.addSurfaceBiomeTo(
						parameters, temperature, humidity,
						MultiNoiseUtil.ParameterRange.combine(this.nearInlandContinentalness(), this.farInlandContinentalness()),
						this.erosions()[4], weirdness, 0.0F, registryKey
					);
				} else {
					BiomeSourceUtils.addSurfaceBiomeTo(
						parameters, temperature, humidity,
						MultiNoiseUtil.ParameterRange.combine(this.coastContinentalness(), this.farInlandContinentalness()),
						this.erosions()[4], weirdness, 0.0F, registryKey
					);
				}

				BiomeSourceUtils.addSurfaceBiomeTo(
					parameters, temperature, humidity,
					this.coastContinentalness(), this.erosions()[5],
					weirdness, 0.0F, registryKey7
				);
				BiomeSourceUtils.addSurfaceBiomeTo(
					parameters, temperature, humidity,
					this.nearInlandContinentalness(), this.erosions()[5],
					weirdness, 0.0F, registryKey
				);
				BiomeSourceUtils.addSurfaceBiomeTo(
					parameters, temperature, humidity,
					MultiNoiseUtil.ParameterRange.combine(this.midInlandContinentalness(), this.farInlandContinentalness()),
					this.erosions()[5], weirdness, 0.0F, registryKey4
				);

				if (weirdness.max() < 0L) {
					BiomeSourceUtils.addSurfaceBiomeTo(
						parameters, temperature, humidity,
						this.coastContinentalness(), this.erosions()[6],
						weirdness, 0.0F, registryKey6
					);
				} else {
					BiomeSourceUtils.addSurfaceBiomeTo(
						parameters, temperature, humidity,
						this.coastContinentalness(), this.erosions()[6],
						weirdness, 0.0F, registryKey
					);
				}

				if (temperatureIndex == 0) {
					BiomeSourceUtils.addSurfaceBiomeTo(
						parameters, temperature, humidity,
						MultiNoiseUtil.ParameterRange.combine(this.nearInlandContinentalness(), this.farInlandContinentalness()),
						this.erosions()[6], weirdness, 0.0F, registryKey
					);
				}
			}
		}
	}

	default void provideLow(Consumer<Pair<MultiNoiseUtil.NoiseHypercube, RegistryKey<Biome>>> parameters, MultiNoiseUtil.ParameterRange weirdness) {
		for(int temperatureIndex = 0; temperatureIndex < this.temperatures().length; temperatureIndex++) {

			MultiNoiseUtil.ParameterRange temperature = this.temperatures()[temperatureIndex];

			for(int humidityIndex = 0; humidityIndex < this.humidities().length; humidityIndex++) {

				MultiNoiseUtil.ParameterRange humidity = this.humidities()[humidityIndex];
				RegistryKey<Biome> registryKey = this.pickMiddle(temperatureIndex, humidityIndex, weirdness);
				RegistryKey<Biome> registryKey2 = this.pickMiddleOrHot(temperatureIndex, humidityIndex, weirdness);
				RegistryKey<Biome> registryKey3 = this.pickMiddleOrHotOrCold(temperatureIndex, humidityIndex, weirdness);
				RegistryKey<Biome> registryKey4 = this.pickBeach(temperatureIndex);
				RegistryKey<Biome> registryKey5 = this.pickWindsweptCoast(temperatureIndex, humidityIndex, weirdness);

				BiomeSourceUtils.addSurfaceBiomeTo(
					parameters, temperature, humidity, this.nearInlandContinentalness(),
					MultiNoiseUtil.ParameterRange.combine(this.erosions()[0], this.erosions()[1]),
					weirdness, 0.0F, registryKey2
				);
				BiomeSourceUtils.addSurfaceBiomeTo(
					parameters, temperature, humidity,
					MultiNoiseUtil.ParameterRange.combine(this.midInlandContinentalness(), this.farInlandContinentalness()),
					MultiNoiseUtil.ParameterRange.combine(this.erosions()[0], this.erosions()[1]),
					weirdness, 0.0F, registryKey3
				);
				BiomeSourceUtils.addSurfaceBiomeTo(
					parameters, temperature, humidity, this.nearInlandContinentalness(),
					MultiNoiseUtil.ParameterRange.combine(this.erosions()[2], this.erosions()[3]),
					weirdness, 0.0F, registryKey
				);
				BiomeSourceUtils.addSurfaceBiomeTo(
					parameters, temperature, humidity,
					MultiNoiseUtil.ParameterRange.combine(this.midInlandContinentalness(), this.farInlandContinentalness()),
					MultiNoiseUtil.ParameterRange.combine(this.erosions()[2], this.erosions()[3]),
					weirdness, 0.0F, registryKey2
				);
				BiomeSourceUtils.addSurfaceBiomeTo(
					parameters, temperature, humidity, this.coastContinentalness(),
					MultiNoiseUtil.ParameterRange.combine(this.erosions()[3], this.erosions()[4]),
					weirdness, 0.0F, registryKey4
				);
				BiomeSourceUtils.addSurfaceBiomeTo(
					parameters, temperature, humidity,
					MultiNoiseUtil.ParameterRange.combine(this.nearInlandContinentalness(), this.farInlandContinentalness()),
					this.erosions()[4], weirdness, 0.0F, registryKey
				);
				BiomeSourceUtils.addSurfaceBiomeTo(
					parameters, temperature, humidity,
					this.coastContinentalness(), this.erosions()[5],
					weirdness, 0.0F, registryKey5
				);
				BiomeSourceUtils.addSurfaceBiomeTo(
					parameters, temperature, humidity,
					this.nearInlandContinentalness(), this.erosions()[5],
					weirdness, 0.0F, registryKey
				);
				BiomeSourceUtils.addSurfaceBiomeTo(
					parameters, temperature, humidity,
					MultiNoiseUtil.ParameterRange.combine(this.midInlandContinentalness(), this.farInlandContinentalness()),
					this.erosions()[5], weirdness, 0.0F, registryKey
				);
				BiomeSourceUtils.addSurfaceBiomeTo(
					parameters, temperature, humidity,
					this.coastContinentalness(), this.erosions()[6],
					weirdness, 0.0F, registryKey4
				);

				if (temperatureIndex == 0) {
					BiomeSourceUtils.addSurfaceBiomeTo(
						parameters, temperature, humidity,
						MultiNoiseUtil.ParameterRange.combine(this.nearInlandContinentalness(), this.farInlandContinentalness()),
						this.erosions()[6], weirdness, 0.0F, registryKey
					);
				}
			}
		}
	}

	default void provideValleys(Consumer<Pair<MultiNoiseUtil.NoiseHypercube, RegistryKey<Biome>>> parameters, MultiNoiseUtil.ParameterRange weirdness) {
		for(int temperatureIndex = 0; temperatureIndex < this.temperatures().length; temperatureIndex++) {

			MultiNoiseUtil.ParameterRange temperature = this.temperatures()[temperatureIndex];

			for(int humidityIndex = 0; humidityIndex < this.humidities().length; humidityIndex++) {

				MultiNoiseUtil.ParameterRange parameterRange2 = this.humidities()[humidityIndex];
				RegistryKey<Biome> registryKey = this.pickMiddleOrHot(temperatureIndex, humidityIndex, weirdness);

				BiomeSourceUtils.addSurfaceBiomeTo(
					parameters, temperature, parameterRange2,
					MultiNoiseUtil.ParameterRange.combine(this.midInlandContinentalness(), this.farInlandContinentalness()),
					MultiNoiseUtil.ParameterRange.combine(this.erosions()[0], this.erosions()[1]),
					weirdness, 0.0F, registryKey
				);
			}
		}
	}

	default void provideUnderground(Consumer<Pair<MultiNoiseUtil.NoiseHypercube, RegistryKey<Biome>>> parameters) {
	}
}
