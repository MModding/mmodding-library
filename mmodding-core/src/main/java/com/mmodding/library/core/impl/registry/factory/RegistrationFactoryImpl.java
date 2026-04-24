package com.mmodding.library.core.impl.registry.factory;

import com.mmodding.library.core.api.registry.factory.RegistrationFactory;

import java.util.Objects;
import java.util.function.BiFunction;

import net.minecraft.core.Registry;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;

public class RegistrationFactoryImpl<T> implements RegistrationFactory<T> {

	private final ResourceKey<? extends Registry<T>> registry;
	private final BiFunction<ResourceKey<T>, T, T> biFunction;
	private final String namespace;

	public RegistrationFactoryImpl(Registry<T> registry, String namespace) {
		this.registry = registry.key();
		this.biFunction = (key, value) -> Registry.register(registry, key, value);
		this.namespace = namespace;
	}

	public RegistrationFactoryImpl(ResourceKey<? extends Registry<T>> registry, BootstrapContext<T> context, String namespace) {
		this.registry = registry;
		this.biFunction = (key, value) -> context.register(key, value).value();
		this.namespace = namespace;
	}

	@Override
	public T register(String path, T entry) {
		return this.register(this.namespace, path, entry);
	}

	@Override
	public T register(String namespace, String path, T entry) {
		return this.biFunction.apply(ResourceKey.create(this.registry, Objects.requireNonNull(Identifier.tryBuild(namespace, path))), entry);
	}
}
