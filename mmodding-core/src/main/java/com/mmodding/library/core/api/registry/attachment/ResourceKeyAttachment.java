package com.mmodding.library.core.api.registry.attachment;

import com.mmodding.library.core.impl.registry.attachment.ResourceKeyAttachmentImpl;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;

/**
 * Associates static {@link Registry} entries to external elements.
 * @param <T> the registry type
 * @param <E> the attachment type
 */
public interface ResourceKeyAttachment<T, E> {

	/**
	 * Creates a new resource key attachment.
	 * @param registry the registry to attach to
	 * @return the new resource key attachment
	 * @param <T> the type of the registry object
	 * @param <E> the type of the attached value
	 */
	static <T, E> ResourceKeyAttachment<T, E> create(Registry<T> registry) {
		return new ResourceKeyAttachmentImpl<>(registry);
	}

	/**
	 * Attaches a value to an object by retrieving its resource key.
	 * @param object the object
	 * @param value the attached value
	 */
	void put(T object, E value);

	/**
	 * Attaches a value to a resource key.
	 * @param key the resource key
	 * @param value the attached value
	 */
	void put(ResourceKey<T> key, E value);

	/**
	 * Checks if an object has an attached value through its resource key.
	 * @param object the object
	 */
	boolean contains(T object);

	/**
	 * Checks if a resource key has an associated value.
	 * @param key the resource key
	 */
	boolean contains(ResourceKey<T> key);

	/**
	 * Retrieves an attached value of an object using object's resource key.
	 * @param object the object
	 * @return the attached value
	 */
	E get(T object);

	/**
	 * Retrieves a value from a resource key.
	 * @param key the resource key
	 * @return the attached value
	 */
	E get(ResourceKey<T> key);
}
