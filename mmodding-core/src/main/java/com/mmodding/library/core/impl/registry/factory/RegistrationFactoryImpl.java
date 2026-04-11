package com.mmodding.library.core.impl.registry.factory;

import com.mmodding.library.core.api.registry.factory.RegistrationFactory;
import java.util.function.BiFunction;
import net.minecraft.core.Registry;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;

public class RegistrationFactoryImpl<T> implements RegistrationFactory<T> {

	private final ResourceKey<? extends Registry<T>> registry;
	private final BiFunction<ResourceKey<T>, T, T> biFunction;
	private final String namespace;

	public RegistrationFactoryImpl(Registry<T> registry, String namespace) {
		this.registry = registry.key();
		this.biFunction = (key, value) -> Registry.register(registry, key, value);
		this.namespace = namespace;
	}

	public RegistrationFactoryImpl(ResourceKey<? extends Registry<T>> registry, BootstapContext<T> registerable, String namespace) {
		this.registry = registry;
		this.biFunction = (key, value) -> { registerable.register(key, value); return value; };
		this.namespace = namespace;
	}

	@Override
	public T register(String path, T entry) {
		return this.register(this.namespace, path, entry);
	}

	@Override
	public T register(String namespace, String path, T entry) {
		return this.biFunction.apply(ResourceKey.create(this.registry, ResourceLocation.tryBuild(namespace, path)), entry);
	}
}
