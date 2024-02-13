package com.mmodding.library.feature.replication;

import net.minecraft.world.gen.feature.PlacedFeature;
import net.minecraft.world.gen.feature.PlacementModifier;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

/* package */ class FeaturePlacementModifiersReplicator {

	private PlacementModifiers placementModifiers;

	/* package */ FeaturePlacementModifiersReplicator(PlacedFeature placedFeature) {
		this.placementModifiers = new PlacementModifiers(placedFeature.placementModifiers());
	}

	/* package */ void mutatePlacementModifiers(Function<PlacementModifiers, PlacementModifiers> mutator) {
		this.placementModifiers = mutator.apply(this.placementModifiers);
	}

	/* package */ List<PlacementModifier> replicate() {
		return new ArrayList<>(this.placementModifiers);
	}
}
