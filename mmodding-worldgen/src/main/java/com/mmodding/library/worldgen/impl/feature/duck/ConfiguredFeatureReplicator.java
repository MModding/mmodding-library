package com.mmodding.library.worldgen.impl.feature.duck;

import com.mmodding.library.java.api.function.AutoMapper;
import net.minecraft.core.Holder;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;
import org.jetbrains.annotations.ApiStatus;

@ApiStatus.Internal
public interface ConfiguredFeatureReplicator<FC extends FeatureConfiguration> {

	void mmodding$replicate(Holder<ConfiguredFeature<?, ?>> target, AutoMapper<FC> patch);
}
