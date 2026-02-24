package com.mmodding.library.core.api.registry.factory;

import com.mmodding.library.core.impl.registry.factory.RegistrationFactoryImpl;
import net.minecraft.registry.Registerable;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.util.Identifier;

public interface RegistrationFactory<T> {

	/**
	 * Creates a {@link RegistrationFactory}.
	 * @param registry the registry
	 * @param namespace the mod namespace
	 * @return the newly created registration factory
	 */
	static <T> RegistrationFactory<T> create(Registry<T> registry, String namespace) {
		return new RegistrationFactoryImpl<>(registry, namespace);
	}

	/**
	 * Creates a {@link RegistrationFactory}.
	 * @param registry the registry's registry key
	 * @param registerable the registrable
	 * @param namespace the mod namespace
	 * @return the newly created registration factory
	 */
	static <T> RegistrationFactory<T> create(RegistryKey<? extends Registry<T>> registry, Registerable<T> registerable, String namespace) {
		return new RegistrationFactoryImpl<>(registry, registerable, namespace);
	}

	/**
	 * A registration method filling the registry and the mod namespace automatically with the provided ones.
	 * @see Registry#register(Registry, Identifier, Object)
	 * @see Registerable#register(RegistryKey, Object)
	 */
	T register(String path, T entry);

	/**
	 * A registration method filling the registry automatically with the provided one.
	 * @see Registry#register(Registry, Identifier, Object)
	 * @see Registerable#register(RegistryKey, Object)
	 */
	T register(String namespace, String path, T entry);
}
