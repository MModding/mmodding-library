package com.mmodding.library.core.api.registry.companion;

import com.mmodding.library.core.api.registry.LiteRegistry;
import com.mmodding.library.core.api.registry.attachment.ResourceKeyAttachment;
import com.mmodding.library.core.impl.registry.companion.RegistryCompanionImpl;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;

/**
 * Associates a {@link LiteRegistry} for each entry of a static {@link Registry} thanks to a {@link ResourceKeyAttachment}.
 * @param <T> the registry type
 * @param <E> the companion type
 */
public interface RegistryCompanion<T, E> {

	/**
	 * Creates a new registry companion.
	 * @param registry the registry to assign the companion to
	 * @return the new registry companion
	 * @param <T> the type of the registry object
	 * @param <E> the type of the attached value
	 */
	static <T, E> RegistryCompanion<T, E> create(Registry<T> registry) {
		return new RegistryCompanionImpl<>(registry);
	}

	/**
	 * Retrieves (or creates if not present) the registry associated to an object by retrieving its resource key.
	 * @param object the object
	 * @return the attached registry
	 */
	LiteRegistry<E> getOrCreateCompanion(T object);

	/**
	 * Retrieves (or creates if not present) the registry associated to a resource key.
	 * @param key the resource key
	 * @return the attached registry
	 */
	LiteRegistry<E> getOrCreateCompanion(ResourceKey<T> key);

	/**
	 * Checks if an object has an associated companion by retrieving its resource key.
	 * @param object the object
	 * @return the check value
	 */
	boolean hasCompanion(T object);

	/**
	 * Checks if a resource key has an associated companion.
	 * @param key the resource key
	 * @return the check value
	 */
	boolean hasCompanion(ResourceKey<T> key);

	/**
	 * Retrieves the registry associated to an object by retrieving its resource key.
	 * @param object the object
	 * @return the attached registry
	 */
	LiteRegistry<E> getCompanion(T object);

	/**
	 * Retrieves the registry associated to a resource key.
	 * @param key the resource key
	 * @return the attached registry
	 */
	LiteRegistry<E> getCompanion(ResourceKey<T> key);
}
