package com.mmodding.library.core.api.registry.attachment;

import com.mmodding.library.core.impl.registry.attachment.DynamicResourceKeyAttachmentImpl;
import net.minecraft.core.Registry;
import net.minecraft.core.RegistryAccess;
import net.minecraft.resources.ResourceKey;

/**
 * Associates dynamic {@link Registry} entries to external elements.
 * @param <T> the registry type
 * @param <E> the attachment type
 */
public interface DynamicResourceKeyAttachment<T, E> {

	/**
	 * Creates a new dynamic registry key attachment.
	 * @param registry the registry key of the registry to attach to
	 * @return the new dynamic registry key attachment
	 * @param <T> the type of the registry object
	 * @param <E> the type of the attached value
	 */
	static <T, E> DynamicResourceKeyAttachment<T, E> create(ResourceKey<? extends Registry<T>> registry) {
		return new DynamicResourceKeyAttachmentImpl<>(registry);
	}

	/**
	 * Attaches a value to an object by retrieving its registry key.
	 * @param manager the dynamic registry manager
	 * @param object the object
	 * @param value the attached value
	 */
	void put(RegistryAccess manager, T object, E value);

	/**
	 * Attaches a value to a registry key.
	 * @param key the registry key
	 * @param value the attached value
	 */
	void put(ResourceKey<T> key, E value);

	/**
	 * Retrieves an attached value of an object using object's registry key.
	 * @param manager the dynamic registry manager
	 * @param object the object
	 * @return the attached value
	 */
	E get(RegistryAccess manager, T object);

	/**
	 * Retrieves a value from a registry key.
	 * @param key the registry key
	 * @return the attached value
	 */
	E get(ResourceKey<T> key);
}
