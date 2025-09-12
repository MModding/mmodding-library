package com.mmodding.library.core.api.registry.extension;

import com.mmodding.library.core.impl.registry.extension.DynamicRegistryKeyAttachmentImpl;
import net.minecraft.registry.DynamicRegistryManager;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;

/**
 * Associates a dynamic {@link Registry} entries to external elements.
 * @param <T> the registry type
 * @param <E> the attachment type
 */
public interface DynamicRegistryKeyAttachment<T, E> {

	/**
	 * Creates a new dynamic registry key attachment.
	 * @param registry the registry key of the registry to attach to
	 * @return the new dynamic registry key attachment
	 * @param <T> the type of the registry object
	 * @param <E> the type of the attached value
	 */
	static <T, E> DynamicRegistryKeyAttachment<T, E> create(RegistryKey<? extends Registry<T>> registry) {
		return new DynamicRegistryKeyAttachmentImpl<>(registry);
	}

	/**
	 * Attaches a value to an object by retrieving its registry key.
	 * @param manager the dynamic registry manager
	 * @param object the object
	 * @param value the attached value
	 */
	void put(DynamicRegistryManager manager, T object, E value);

	/**
	 * Attaches a value to a registry key.
	 * @param key the registry key
	 * @param value the attached value
	 */
	void put(RegistryKey<T> key, E value);

	/**
	 * Retrieves an attached value of an object using object's registry key.
	 * @param manager the dynamic registry manager
	 * @param object the object
	 * @return the attached value
	 */
	E get(DynamicRegistryManager manager, T object);

	/**
	 * Retrieves a value from a registry key.
	 * @param key the registry key
	 * @return the attached value
	 */
	E get(RegistryKey<T> key);
}
