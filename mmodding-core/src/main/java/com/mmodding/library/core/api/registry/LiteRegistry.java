package com.mmodding.library.core.api.registry;

import com.mmodding.library.core.impl.registry.LiteRegistryImpl;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

/**
 * A lighter version of the {@link Registry} object.
 * @param <T> the element type of the registry
 */
public interface LiteRegistry<T> extends Iterable<LiteRegistry.Entry<T>> {

	/**
	 * Creates a new {@link LiteRegistry}.
	 * @return the newly created {@link LiteRegistry}
	 * @param <T> the element type
	 */
	static <T> LiteRegistry<T> create() {
		return new LiteRegistryImpl<>();
	}

	/**
	 * Checks if an entry using a specific identifier exists in the registry.
	 * @param identifier the identifier
	 * @return a boolean representing if it was found or not
	 */
	boolean contains(Identifier identifier);

	/**
	 * Retrieves an object from the registry with its associated identifier.
	 * @param identifier the identifier of the object
	 * @return the object
	 */
	T get(Identifier identifier);

	/**
	 * Retrieves an identifier from the registry with its associated object.
	 * @param entry the object for the identifier
	 * @return the identifier
	 */
	Identifier getId(T entry);

	/**
	 * Registers an object to the registry for a provided identifier.
	 * @param identifier the identifier for the object
	 * @param entry the object to register
	 * @return the newly registered object
	 * @exception IllegalStateException an entry with the same identifier is already present in the registry
	 */
	T register(Identifier identifier, T entry);

	/**
	 * An {@link Entry} object for the {@link LiteRegistry}.
	 * @param <T> the element type
	 */
	interface Entry<T> {

		/**
		 * Returns the {@link Identifier} used by the {@link Entry}.
		 * @return the identifier
		 */
		Identifier identifier();

		/**
		 * Returns the object held by the {@link Entry}.
		 * @return the entry object
		 */
		T element();
	}
}
