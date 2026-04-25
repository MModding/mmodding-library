package com.mmodding.library.worldgen.impl.feature;

import com.mmodding.library.java.api.container.Pair;
import com.mmodding.library.java.api.either.Either;
import com.mmodding.library.java.api.function.AutoMapper;
import com.mmodding.library.java.api.list.BiList;
import com.mmodding.library.worldgen.api.feature.ConfiguredFeaturePack;
import com.mmodding.library.worldgen.api.feature.FeaturePack;
import com.mmodding.library.worldgen.impl.feature.duck.ConfiguredFeatureReplicator;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;

import java.util.function.Consumer;

public class FeaturePackImpl<FC extends FeatureConfiguration> implements FeaturePack<FC> {

	private final Feature<FC> feature;

	private final BiList<ConfiguredFeaturePack<FC>, Either<FC, Pair<ResourceKey<ConfiguredFeature<?, ?>>, AutoMapper<FC>>>> configuredFeaturePacks;

	public FeaturePackImpl(Feature<FC> feature) {
		this.feature = feature;
		this.configuredFeaturePacks = BiList.create();
	}

	@Override
	public FeaturePack<FC> appendConfiguredFeature(ResourceKey<ConfiguredFeature<?, ?>> key, FC featureConfig, Consumer<ConfiguredFeaturePack<FC>> action) {
		ConfiguredFeaturePack<FC> configuredFeaturePack = new ConfiguredFeaturePackImpl<>(key);
		this.configuredFeaturePacks.add(configuredFeaturePack, Either.ofFirst(featureConfig));
		action.accept(configuredFeaturePack);
		return this;
	}

	@Override
	public FeaturePack<FC> replicateConfiguredFeature(ResourceKey<ConfiguredFeature<?, ?>> source, ResourceKey<ConfiguredFeature<?, ?>> key, AutoMapper<FC> patcher, Consumer<ConfiguredFeaturePack<FC>> action) {
		ConfiguredFeaturePack<FC> configuredFeaturePack = new ConfiguredFeaturePackImpl<>(key);
		this.configuredFeaturePacks.add(configuredFeaturePack, Either.ofSecond(Pair.create(source, patcher)));
		action.accept(configuredFeaturePack);
		return this;
	}

	@Override
	@SuppressWarnings({"DataFlowIssue", "unchecked"}) // remove "null" related warning => the "null" won't last long
	public void registerConfigs(BootstrapContext<ConfiguredFeature<?, ?>> context) {
		HolderGetter<ConfiguredFeature<?, ?>> configuredFeatures = context.lookup(Registries.CONFIGURED_FEATURE);
		this.configuredFeaturePacks.forEach((pack, either) -> either.execute(
			featureConfig -> context.register(((ConfiguredFeaturePackImpl<FC>) pack).configuredFeatureKey, new ConfiguredFeature<>(this.feature, featureConfig)),
			pair -> {
				ConfiguredFeature<?, ?> configuredFeature = new ConfiguredFeature<>(this.feature, null);
				((ConfiguredFeatureReplicator<FC>) (Object) configuredFeature).mmodding$replicate(configuredFeatures.getOrThrow(pair.first()), pair.second());
				context.register(((ConfiguredFeaturePackImpl<FC>) pack).configuredFeatureKey, configuredFeature);
			}
		));
	}

	@Override
	public void registerPlacements(BootstrapContext<PlacedFeature> context) {
		this.configuredFeaturePacks.forEachFirst(pack -> pack.register(context));
	}
}
