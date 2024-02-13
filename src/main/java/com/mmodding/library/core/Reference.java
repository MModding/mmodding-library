package com.mmodding.library.core;

import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.util.Identifier;
import org.quiltmc.qsl.base.api.util.InjectedInterface;

@InjectedInterface({RegistryKey.class, Identifier.class})
public interface Reference<T> {

	@SuppressWarnings("unchecked")
	static <T> Reference<T> cast(Identifier identifier) {
		return (Reference<T>) identifier;
	}

	@SuppressWarnings("unchecked")
	static <T> Reference<T> cast(RegistryKey<T> registryKey) {
		return (Reference<T>) registryKey;
	}

	default RegistryKey<T> provideKey(Registry<T> registry) {
		throw new IllegalStateException();
	}
}
