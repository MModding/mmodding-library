package com.mmodding.library.core.impl.registry.factory;

import com.mmodding.library.core.api.registry.factory.RegistrationFactory;
import net.minecraft.registry.Registerable;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.util.Identifier;

import java.util.function.BiFunction;

public class RegistrationFactoryImpl<T> implements RegistrationFactory<T> {

	private final RegistryKey<? extends Registry<T>> registry;
	private final BiFunction<RegistryKey<T>, T, T> biFunction;
	private final String namespace;

	public RegistrationFactoryImpl(Registry<T> registry, String namespace) {
		this.registry = registry.getKey();
		this.biFunction = (key, value) -> Registry.register(registry, key, value);
		this.namespace = namespace;
	}

	public RegistrationFactoryImpl(RegistryKey<? extends Registry<T>> registry, Registerable<T> registerable, String namespace) {
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
		return this.biFunction.apply(RegistryKey.of(this.registry, Identifier.of(namespace, path)), entry);
	}
}
