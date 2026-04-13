package com.mmodding.library.worldgen.api.feature.catalog;

import com.mmodding.library.worldgen.api.feature.catalog.configurations.LayeredConfiguration;
import com.mojang.serialization.Codec;
import net.minecraft.core.Holder;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;

public class LayeredFeature extends Feature<LayeredConfiguration> {

	public LayeredFeature(Codec<LayeredConfiguration> codec) {
		super(codec);
	}

	@Override
	public boolean place(FeaturePlaceContext<LayeredConfiguration> context) {
		for (Holder<PlacedFeature> placedFeature : context.config().layers()) {
			placedFeature.value().placeWithBiomeCheck(context.level(), context.chunkGenerator(), context.random(), context.origin());
		}
		return true;
	}
}
