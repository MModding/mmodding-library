package com.mmodding.library.core.api.registry.attachment;

import com.mmodding.library.core.impl.registry.attachment.RegistryKeyAttachmentImpl;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;

/**
 * Associates a static {@link Registry} entries to external elements.
 * @param <T> the registry type
 * @param <E> the attachment type
 */
public interface RegistryKeyAttachment<T, E> {

	/**
	 * Creates a new registry key attachment.
	 * @param registry the registry to attach to
	 * @return the new registry key attachment
	 * @param <T> the type of the registry object
	 * @param <E> the type of the attached value
	 */
	static <T, E> RegistryKeyAttachment<T, E> create(Registry<T> registry) {
		return new RegistryKeyAttachmentImpl<>(registry);
	}

	/**
	 * Attaches a value to an object by retrieving its registry key.
	 * @param object the object
	 * @param value the attached value
	 */
	void put(T object, E value);

	/**
	 * Attaches a value to a registry key.
	 * @param key the registry key
	 * @param value the attached value
	 */
	void put(RegistryKey<T> key, E value);

	/**
	 * Retrieves an attached value of an object using object's registry key.
	 * @param object the object
	 * @return the attached value
	 */
	E get(T object);

	/**
	 * Retrieves a value from a registry key.
	 * @param key the registry key
	 * @return the attached value
	 */
	E get(RegistryKey<T> key);
}
