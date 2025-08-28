package com.mmodding.library.core.api.registry.factory;

import com.mmodding.library.core.impl.registry.factory.RegistryKeyFactoryImpl;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;

public interface RegistryKeyFactory<T> {

	/**
	 * A way to manually create a registry key factory, i.e. for a bunch of static key instantiations.
	 * @param registry the registry's registry key
	 * @param namespace the mod namespace
	 * @return the newly created registry key factory
	 */
	static <T> RegistryKeyFactory<T> create(RegistryKey<? extends Registry<T>> registry, String namespace) {
		return new RegistryKeyFactoryImpl<>(registry, namespace);
	}

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
