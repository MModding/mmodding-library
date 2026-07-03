package com.mmodding.library.core.api.registry.companion;

import com.mmodding.library.core.api.registry.LiteRegistry;
import com.mmodding.library.core.impl.registry.companion.IdentityCompanionImpl;

import java.util.IdentityHashMap;

/**
 * Associates a {@link LiteRegistry} for each given object, using identity checks through {@link IdentityHashMap}.
 * @param <T> the object type
 * @param <E> the companion type
 */
public interface IdentityCompanion<T, E> {

	/**
	 * Creates a new identity companion.
	 * @return the new identity companion
	 * @param <T> the type of the registry object
	 * @param <E> the type of the attached value
	 */
	static <T, E> IdentityCompanion<T, E> create() {
		return new IdentityCompanionImpl<>();
	}

	/**
	 * Retrieves (or creates if not present) the registry associated to an object.
	 * @param object the object
	 * @return the attached registry
	 */
	LiteRegistry<E> getOrCreateCompanion(T object);

	/**
	 * Checks if an object has an associated companion.
	 * @param object the object
	 * @return the check value
	 */
	boolean hasCompanion(T object);

	/**
	 * Retrieves the registry associated to an object.
	 * @param object the object
	 * @return the attached registry
	 */
	LiteRegistry<E> getCompanion(T object);
}
