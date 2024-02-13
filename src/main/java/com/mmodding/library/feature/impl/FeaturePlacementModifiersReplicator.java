package com.mmodding.library.feature.impl;

import com.mmodding.library.feature.api.replication.PlacementModifiers;
import net.minecraft.world.gen.feature.PlacedFeature;
import net.minecraft.world.gen.feature.PlacementModifier;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

public class FeaturePlacementModifiersReplicator {

	private PlacementModifiers placementModifiers;

	public FeaturePlacementModifiersReplicator(PlacedFeature placedFeature) {
		this.placementModifiers = new PlacementModifiers(placedFeature.placementModifiers());
	}

	public void mutatePlacementModifiers(Function<PlacementModifiers, PlacementModifiers> mutator) {
		this.placementModifiers = mutator.apply(this.placementModifiers);
	}

	public List<PlacementModifier> replicate() {
		return new ArrayList<>(this.placementModifiers);
	}
}
