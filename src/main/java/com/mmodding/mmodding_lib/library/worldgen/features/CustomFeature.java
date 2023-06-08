package com.mmodding.mmodding_lib.library.worldgen.features;

import com.mmodding.mmodding_lib.library.utils.RegistrationUtils;
import com.mojang.serialization.Codec;
import net.minecraft.util.Identifier;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.FeatureConfig;
import net.minecraft.world.gen.feature.PlacedFeature;

import java.util.concurrent.atomic.AtomicBoolean;

public abstract class CustomFeature<FC extends FeatureConfig> extends Feature<FC> {

	private final AtomicBoolean registered = new AtomicBoolean(false);

	public CustomFeature(Codec<FC> configCodec) {
		super(configCodec);
	}

	public abstract <F extends CustomFeature<FC>> ConfiguredFeature<FC, F> getDefaultConfigured();

	public abstract PlacedFeature getDefaultPlaced();

	public void register(Identifier identifier) {
		if (this.isNotRegistered()) {
			RegistrationUtils.registerFeature(identifier, this, this.getDefaultConfigured(), this.getDefaultPlaced());
			this.setRegistered();
		}
	}

	public boolean isNotRegistered() {
		return !registered.get();
	}

	public void setRegistered() {
		registered.set(true);
	}
}
