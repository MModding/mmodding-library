package com.mmodding.library.worldgen.api.feature.catalog;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.registry.entry.RegistryEntryList;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.FeatureConfig;
import net.minecraft.world.gen.feature.PlacedFeature;
import net.minecraft.world.gen.feature.util.FeatureContext;

public class LayeredFeature extends Feature<LayeredFeature.Config> {

	public LayeredFeature(Codec<Config> codec) {
		super(codec);
	}

	@Override
	public boolean generate(FeatureContext<Config> context) {
		for (RegistryEntry<PlacedFeature> placedFeature : context.getConfig().layers()) {
			placedFeature.value().generate(context.getWorld(), context.getGenerator(), context.getRandom(), context.getOrigin());
		}
		return true;
	}

	public record Config(RegistryEntryList<PlacedFeature> layers) implements FeatureConfig {

		public static final Codec<LayeredFeature.Config> CODEC = RecordCodecBuilder.create(
			instance -> instance.group(
				PlacedFeature.LIST_CODEC.fieldOf("layers").forGetter(Config::layers)
			).apply(instance, LayeredFeature.Config::new)
		);
	}
}
