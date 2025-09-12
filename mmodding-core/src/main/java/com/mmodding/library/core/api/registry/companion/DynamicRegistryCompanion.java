package com.mmodding.library.core.api.registry.companion;

import com.mmodding.library.core.api.registry.LiteRegistry;
import com.mmodding.library.core.api.registry.extension.DynamicRegistryKeyAttachment;
import com.mmodding.library.core.api.registry.extension.RegistryKeyAttachment;
import com.mmodding.library.core.impl.registry.companion.DynamicRegistryCompanionImpl;
import net.minecraft.registry.DynamicRegistryManager;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import org.jetbrains.annotations.Nullable;

/**
 * Associates a {@link LiteRegistry} for each entry of a dynamic {@link Registry} thanks to a {@link DynamicRegistryKeyAttachment}.
 * @param <T> the registry type
 * @param <E> the companion type
 */
public interface DynamicRegistryCompanion<T, E> {

	/**
	 * Creates a new dynamic registry companion.
	 * @param registry the registry key of the registry to assign the companion to
	 * @return the new dynamic registry companion
	 * @param <T> the type of the registry object
	 * @param <E> the type of the attached value
	 */
	static <T, E> DynamicRegistryCompanion<T, E> create(RegistryKey<? extends Registry<T>> registry) {
		return new DynamicRegistryCompanionImpl<>(registry);
	}

	/**
	 * Retrieves (or creates if not present) the registry associated to an object by retrieving its registry key.
	 * @param manager the dynamic registry manager
	 * @param object the object
	 * @return the attached registry
	 */
	LiteRegistry<E> getOrCreateCompanion(@Nullable DynamicRegistryManager manager, T object);

	/**
	 * Retrieves (or creates if not present) the registry associated to a registry key.
	 * @param key the registry key
	 * @return the attached registry
	 */
	LiteRegistry<E> getOrCreateCompanion(RegistryKey<T> key);

	/**
	 * Retrieves the registry associated to an object by retrieving its registry key.
	 * @param manager the dynamic registry manager
	 * @param object the object
	 * @return the attached registry
	 */
	LiteRegistry<E> getCompanion(@Nullable DynamicRegistryManager manager, T object);

	/**
	 * Retrieves the registry associated to a registry key.
	 * @param key the registry key
	 * @return the attached registry
	 */
	LiteRegistry<E> getCompanion(RegistryKey<T> key);
}
