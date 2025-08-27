package com.mmodding.library.core.api.registry.factory;

import net.minecraft.registry.RegistryKey;

public interface RegistryKeyFactory<T> {

	/**
	 * Creates a registry key with the factory registry's registry key and the factory mod namespace.
	 * @param path the path to provide to create the key
	 * @return the newly created registry key
	 */
	RegistryKey<T> createKey(String path);

	/**
	 * Creates a registry key with the factory registry's registry key.
	 * @param namespace the mod namespace to provide to create the key
	 * @param path the path to provide to create the key
	 * @return the newly created registry key
	 */
	RegistryKey<T> createKey(String namespace, String path);
}
