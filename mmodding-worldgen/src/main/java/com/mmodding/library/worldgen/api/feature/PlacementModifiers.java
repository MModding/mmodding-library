package com.mmodding.library.worldgen.api.feature;

import java.util.List;
import java.util.function.Function;
import net.minecraft.world.level.levelgen.placement.PlacementModifier;
import net.minecraft.world.level.levelgen.placement.PlacementModifierType;

/**
 * An interface to access replicated feature placements modifiers.
 */
public interface PlacementModifiers {

	/**
	 * Fetches placement modifiers of a specified type.
	 * @param type the type
	 * @return the placement modifiers
	 */
	List<PlacementModifier> fetch(PlacementModifierType<?> type);

	/**
	 * Fetches the first placement modifier of a specified type.
	 * @param type the type
	 * @return the placement modifier
	 * @throws IndexOutOfBoundsException if it did not find any placement modifier of this type
	 */
	PlacementModifier fetchFirst(PlacementModifierType<?> type);

	/**
	 * Adds specified placements modifiers.
	 * @param placementModifiers the placement modifiers
	 * @return the placement modifiers access object
	 */
	PlacementModifiers add(PlacementModifier... placementModifiers);

	/**
	 * Adds specified placements modifiers.
	 * @param placementModifiers the placement modifiers
	 * @return the placement modifiers access object
	 */
	PlacementModifiers add(List<PlacementModifier> placementModifiers);

	/**
	 * Remove all placement modifiers of a specified type.
	 * @param type the type
	 * @return the placement modifiers access object
	 */
	PlacementModifiers remove(PlacementModifierType<?> type);

	/**
	 * Finds the first placement modifiers of the type, mutates it, and replaces every placement modifiers of this type by the mutated one.
	 * @param type the type
	 * @param mutator the mutator
	 * @return the placement modifiers access object
	 */
	<P extends PlacementModifier> PlacementModifiers mutateTypeTo(PlacementModifierType<P> type, Function<P, PlacementModifier> mutator);
}
