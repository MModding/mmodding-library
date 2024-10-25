package com.mmodding.mmodding_lib.library.worldgen.features.builtin;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.RegistryKey;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.FeatureConfig;
import net.minecraft.world.gen.feature.util.FeatureContext;

import java.util.List;

public class LayeredFeature extends Feature<LayeredFeature.Config> {

	public LayeredFeature(Codec<LayeredFeature.Config> codec) {
		super(codec);
	}

	@Override
	public boolean place(FeatureContext<Config> context) {
		context.getConfig().features.forEach(feature -> {
			ConfiguredFeature<?, ?> entry = context.getWorld().getRegistryManager().get(Registry.CONFIGURED_FEATURE_KEY).get(feature);
			if (entry != null) {
				entry.generate(context.getWorld(), context.getGenerator(), context.getRandom(), context.getOrigin());
			}
			else {
				throw new IllegalArgumentException("ConfiguredFeature at " + feature + "does not exist");
			}
		});
		return true;
	}

	public static class Config implements FeatureConfig {

		private final List<RegistryKey<ConfiguredFeature<?, ?>>> features;

		public static final Codec<Config> CODEC = RecordCodecBuilder.create(
			instance -> instance
				.group(Codec.list(RegistryKey.codec(Registry.CONFIGURED_FEATURE_KEY)).fieldOf("features").forGetter(config -> config.features))
				.apply(instance, Config::new)
		);

		public Config(List<RegistryKey<ConfiguredFeature<?, ?>>> features) {
			this.features = features;
		}
	}
}
