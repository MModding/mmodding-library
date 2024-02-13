package com.mmodding.library.feature.impl;

import net.minecraft.world.gen.feature.ConfiguredFeature;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.FeatureConfig;

import java.util.function.Function;

public class FeatureConfigReplicator<FC extends FeatureConfig> {

	private FC featureConfig;

	public FeatureConfigReplicator(ConfiguredFeature<FC, Feature<FC>> configuredFeature) {
		this.featureConfig = configuredFeature.getConfig();
	}

	public void mutateConfig(Function<FC, FC> mutator) {
		this.featureConfig = mutator.apply(this.featureConfig);
	}

	public FC replicate() {
		return this.featureConfig;
	}
}
