package com.mmodding.library.core.impl.registry.factory;

import com.mmodding.library.core.api.registry.factory.RegistryKeyFactory;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.util.Identifier;

public class RegistryKeyFactoryImpl<T> implements RegistryKeyFactory<T> {

	private final RegistryKey<? extends Registry<T>> registry;
	private final String namespace;

	public RegistryKeyFactoryImpl(RegistryKey<? extends Registry<T>> registry, String namespace) {
		this.registry = registry;
		this.namespace = namespace;
	}

	@Override
	public RegistryKey<T> createKey(String path) {
		return this.createKey(this.namespace, path);
	}

	@Override
	public RegistryKey<T> createKey(String namespace, String path) {
		return RegistryKey.of(this.registry, new Identifier(namespace, path));
	}
}
