package com.mmodding.library.feature.replication;

import net.minecraft.world.gen.feature.ConfiguredFeature;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.FeatureConfig;

import java.util.function.Function;

/* package */ class FeatureConfigReplicator<FC extends FeatureConfig> {

	private FC featureConfig;

	/* package */ FeatureConfigReplicator(ConfiguredFeature<FC, Feature<FC>> configuredFeature) {
		this.featureConfig = configuredFeature.getConfig();
	}

	/* package */ void mutateConfig(Function<FC, FC> mutator) {
		this.featureConfig = mutator.apply(this.featureConfig);
	}

	/* package */ FC replicate() {
		return this.featureConfig;
	}
}
