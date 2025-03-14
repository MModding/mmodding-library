package com.mmodding.library.worldgen.api.feature.replication;

import net.minecraft.world.gen.placementmodifier.PlacementModifier;
import net.minecraft.world.gen.placementmodifier.PlacementModifierType;

import java.util.List;
import java.util.function.Function;

public interface PlacementModifiers {

	List<PlacementModifier> fetch(PlacementModifierType<?> type);

	PlacementModifier fetchFirst(PlacementModifierType<?> type);

	void add(PlacementModifier... placementModifiers);

	void add(List<PlacementModifier> placementModifiers);

	void remove(PlacementModifierType<?> type);

	<P extends PlacementModifier> void mutateTypeTo(PlacementModifierType<P> type, Function<P, PlacementModifier> mutator);
}
