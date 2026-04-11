package com.mmodding.library.worldgen.api.feature.catalog;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderSet;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;

public class LayeredFeature extends Feature<LayeredFeature.Config> {

	public LayeredFeature(Codec<Config> codec) {
		super(codec);
	}

	@Override
	public boolean place(FeaturePlaceContext<Config> context) {
		for (Holder<PlacedFeature> placedFeature : context.config().layers()) {
			placedFeature.value().placeWithBiomeCheck(context.level(), context.chunkGenerator(), context.random(), context.origin());
		}
		return true;
	}

	public record Config(HolderSet<PlacedFeature> layers) implements FeatureConfiguration {

		public static final Codec<LayeredFeature.Config> CODEC = RecordCodecBuilder.create(
			instance -> instance.group(
				PlacedFeature.LIST_CODEC.fieldOf("layers").forGetter(Config::layers)
			).apply(instance, LayeredFeature.Config::new)
		);
	}
}
