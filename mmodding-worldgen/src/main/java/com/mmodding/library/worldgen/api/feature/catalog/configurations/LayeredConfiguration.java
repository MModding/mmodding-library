package com.mmodding.library.worldgen.api.feature.catalog.configurations;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.HolderSet;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;

public record LayeredConfiguration(HolderSet<PlacedFeature> layers) implements FeatureConfiguration {

	public static final Codec<LayeredConfiguration> CODEC = RecordCodecBuilder.create(
		instance -> instance.group(
			PlacedFeature.LIST_CODEC.fieldOf("layers").forGetter(LayeredConfiguration::layers)
		).apply(instance, LayeredConfiguration::new)
	);
}
