package com.mmodding.library.worldgen.impl.feature.duck;

import com.mmodding.library.java.api.function.AutoMapper;
import com.mmodding.library.worldgen.api.feature.PlacementModifiers;
import net.minecraft.core.Holder;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import org.jetbrains.annotations.ApiStatus;

@ApiStatus.Internal
public interface PlacedFeatureReplicator {

	void mmodding$replicate(Holder<PlacedFeature> target, AutoMapper<PlacementModifiers> patch);
}
