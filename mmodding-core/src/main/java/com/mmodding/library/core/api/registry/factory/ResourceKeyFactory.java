package com.mmodding.library.core.api.registry.factory;

import com.mmodding.library.core.impl.registry.factory.ResourceKeyFactoryImpl;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;

public interface ResourceKeyFactory<T> {

	/**
	 * Creates a {@link ResourceKeyFactory} (when you need to generate a lot of them).
	 * @param registry the registry's resource key
	 * @param namespace the mod namespace
	 * @return the newly created resource key factory
	 */
	static <T> ResourceKeyFactory<T> create(ResourceKey<? extends Registry<T>> registry, String namespace) {
		return new ResourceKeyFactoryImpl<>(registry, namespace);
	}

	/**
	 * Creates a resource key with the factory registry's resource key and the factory mod namespace.
	 * @param path the path to provide to create the key
	 * @return the newly created resource key
	 */
	ResourceKey<T> createKey(String path);

	/**
	 * Creates a resource key with the factory registry's resource key.
	 * @param namespace the mod namespace to provide to create the key
	 * @param path the path to provide to create the key
	 * @return the newly created resource key
	 */
	ResourceKey<T> createKey(String namespace, String path);
}
