package com.mmodding.library.core.api.management.context;

import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import net.minecraft.world.gen.feature.PlacedFeature;

public class DoubleRegistryContext<L, R> extends RegistryContext {

	public static final DoubleRegistryContext<ConfiguredFeature<?, ?>, PlacedFeature> FEATURE = new DoubleRegistryContext<>(RegistryKeys.CONFIGURED_FEATURE, RegistryKeys.PLACED_FEATURE);

	public DoubleRegistryContext(RegistryKey<Registry<L>> leftRegistryKey, RegistryKey<Registry<R>> rightRegistryKey) {
		super(leftRegistryKey, rightRegistryKey);
	}
}
