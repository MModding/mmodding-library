package com.mmodding.mmodding_lib.interface_injections;

import com.mmodding.mmodding_lib.library.utils.ClassExtension;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.registry.RegistryKey;
import net.minecraft.world.gen.feature.StructureFeature;

import java.util.Set;

@ClassExtension(PlayerEntity.class)
public interface PlayerStructureLookup {

	/**
	 * This method returns a set of registry keys of structures the player is in or that are aside from it.
	 * @return the set of registry keys of structures
	 */
	default Set<RegistryKey<StructureFeature>> getClosestStructures() {
		throw new IllegalStateException();
	}

	/**
	 * This method checks if a specific structure is close to the player.
	 * @param structure the structure
	 * @return a boolean specifying if the structure is close or not
	 */
	default boolean isCloseToStructure(RegistryKey<StructureFeature> structure) {
		return this.getClosestStructures().contains(structure);
	}
}
