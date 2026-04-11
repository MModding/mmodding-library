package com.mmodding.library.core.impl.registry.factory;

import com.mmodding.library.core.api.registry.factory.RegistryKeyFactory;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;

public class RegistryKeyFactoryImpl<T> implements RegistryKeyFactory<T> {

	private final ResourceKey<? extends Registry<T>> registry;
	private final String namespace;

	public RegistryKeyFactoryImpl(ResourceKey<? extends Registry<T>> registry, String namespace) {
		this.registry = registry;
		this.namespace = namespace;
	}

	@Override
	public ResourceKey<T> createKey(String path) {
		return this.createKey(this.namespace, path);
	}

	@Override
	public ResourceKey<T> createKey(String namespace, String path) {
		return ResourceKey.create(this.registry, new ResourceLocation(namespace, path));
	}
}
