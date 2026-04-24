package com.mmodding.library.worldgen.impl.feature;

import com.mmodding.library.java.api.container.Pair;
import com.mmodding.library.java.api.either.Either;
import com.mmodding.library.java.api.function.AutoMapper;
import com.mmodding.library.java.api.list.BiList;
import com.mmodding.library.worldgen.api.feature.ConfiguredFeaturePack;
import com.mmodding.library.worldgen.api.feature.PlacementModifiers;
import com.mmodding.library.worldgen.impl.feature.duck.PlacedFeatureReplicator;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import net.minecraft.world.level.levelgen.placement.PlacementModifier;

import java.util.List;

public class ConfiguredFeaturePackImpl<FC extends FeatureConfiguration> implements ConfiguredFeaturePack<FC> {

	final ResourceKey<ConfiguredFeature<?, ?>> configuredFeatureKey;
	private final BiList<ResourceKey<PlacedFeature>, Either<List<PlacementModifier>, Pair<ResourceKey<PlacedFeature>, AutoMapper<PlacementModifiers>>>> placedFeatures;

	public ConfiguredFeaturePackImpl(ResourceKey<ConfiguredFeature<?, ?>> key) {
		this.configuredFeatureKey = key;
		this.placedFeatures = BiList.create();
	}

	@Override
	public ConfiguredFeaturePack<FC> appendPlacedFeature(ResourceKey<PlacedFeature> key, PlacementModifier... modifiers) {
		return this.appendPlacedFeature(key, List.of(modifiers));
	}

	@Override
	public ConfiguredFeaturePack<FC> appendPlacedFeature(ResourceKey<PlacedFeature> key, List<PlacementModifier> modifiers) {
		this.placedFeatures.add(key, Either.ofFirst(modifiers));
		return this;
	}

	@Override
	public ConfiguredFeaturePack<FC> replicatePlacedFeature(ResourceKey<PlacedFeature> source, ResourceKey<PlacedFeature> key, AutoMapper<PlacementModifiers> patcher) {
		this.placedFeatures.add(key, Either.ofSecond(Pair.create(source, patcher)));
		return this;
	}

	@Override
	@SuppressWarnings("DataFlowIssue") // remove "null" related warning => the "null" won't last long
	public void register(BootstrapContext<PlacedFeature> context) {
		Holder<ConfiguredFeature<?, ?>> configuredFeatureRef = context.lookup(Registries.CONFIGURED_FEATURE).getOrThrow(this.configuredFeatureKey);
		HolderGetter<PlacedFeature> placedFeatures = context.lookup(Registries.PLACED_FEATURE);
		this.placedFeatures.forEach((key, either) -> either.execute(
			modifiers -> context.register(key, new PlacedFeature(configuredFeatureRef, modifiers)),
			pair -> {
				PlacedFeature placedFeature = new PlacedFeature(configuredFeatureRef, null);
				((PlacedFeatureReplicator) (Object) placedFeature).mmodding$replicate(placedFeatures.getOrThrow(pair.first()), pair.second());
				context.register(key, placedFeature);
			}
		));
	}
}
