package com.mmodding.library.worldgen.impl.feature;

import com.mmodding.library.java.api.function.AutoMapper;
import com.mmodding.library.java.api.list.BiList;
import com.mmodding.library.worldgen.api.feature.ConfiguredFeaturePack;
import com.mmodding.library.worldgen.api.feature.FeaturePack;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.data.worldgen.features.FeatureUtils;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import java.util.function.Consumer;
import java.util.function.Function;

public class FeaturePackImpl<FC extends FeatureConfiguration> implements FeaturePack<FC> {

	private final Feature<FC> feature;

	private final BiList<ConfiguredFeaturePack<FC>, Function<BootstrapContext<ConfiguredFeature<?, ?>>, FC>> configuredFeaturePacks;

	public FeaturePackImpl(Feature<FC> feature) {
		this.feature = feature;
		this.configuredFeaturePacks = BiList.create();
	}

	@Override
	public FeaturePack<FC> appendConfiguredFeature(ResourceKey<ConfiguredFeature<?, ?>> key, FC featureConfig, Consumer<ConfiguredFeaturePack<FC>> action) {
		ConfiguredFeaturePack<FC> configuredFeaturePack = ConfiguredFeaturePack.of(key);
		this.configuredFeaturePacks.add(configuredFeaturePack, ignored -> featureConfig);
		action.accept(configuredFeaturePack);
		return this;
	}

	@Override
	public FeaturePack<FC> appendConfiguredFeature(ResourceKey<ConfiguredFeature<?, ?>> key, Function<FeaturePack.ConfiguredFeatureLookup<FC>, FC> featureConfigFactory, Consumer<ConfiguredFeaturePack<FC>> action) {
		ConfiguredFeaturePack<FC> configuredFeaturePack = new ConfiguredFeaturePackImpl<>(key);
		this.configuredFeaturePacks.add(configuredFeaturePack, configuredFeatures -> {
			HolderGetter<ConfiguredFeature<?, ?>> lookup = configuredFeatures.lookup(Registries.CONFIGURED_FEATURE);
			return featureConfigFactory.apply(lookup::getOrThrow);
		});
		action.accept(configuredFeaturePack);
		return this;
	}

	@Override
	@SuppressWarnings("unchecked")
	public FeaturePack<FC> replicateConfiguredFeature(ResourceKey<ConfiguredFeature<?, ?>> source, ResourceKey<ConfiguredFeature<?, ?>> key, AutoMapper<FC> patcher, Consumer<ConfiguredFeaturePack<FC>> action) {
		ConfiguredFeaturePack<FC> configuredFeaturePack = new ConfiguredFeaturePackImpl<>(key);
		this.configuredFeaturePacks.add(configuredFeaturePack, configuredFeatures -> {
			ConfiguredFeature<?, ?> sourceConfiguredFeature = configuredFeatures.lookup(Registries.CONFIGURED_FEATURE).getOrThrow(source).value();
			return patcher.map((FC) sourceConfiguredFeature.config());
		});
		action.accept(configuredFeaturePack);
		return this;
	}

	@Override
	public void registerConfiguredFeatures(BootstrapContext<ConfiguredFeature<?, ?>> configuredFeatures) {
		this.configuredFeaturePacks.forEach((pack, featureConfig) -> {
			ConfiguredFeaturePackImpl<FC> impl = (ConfiguredFeaturePackImpl<FC>) pack;
			FeatureUtils.register(configuredFeatures, impl.configuredFeatureKey, this.feature, featureConfig.apply(configuredFeatures));
		});
	}

	@Override
	public void registerPlacedFeatures(BootstrapContext<PlacedFeature> placedFeatures) {
		this.configuredFeaturePacks.forEach((pack, featureConfig) -> pack.registerPlacedFeatures(placedFeatures));
	}
}
