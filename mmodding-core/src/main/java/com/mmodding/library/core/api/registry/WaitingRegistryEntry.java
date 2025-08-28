package com.mmodding.library.core.api.registry;

import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;

import java.util.ArrayList;
import java.util.List;

public class WaitingRegistryEntry<T> {

	private final RegistryKey<T> key;
	private final T element;

	public WaitingRegistryEntry(RegistryKey<T> key, T element) {
		this.key = key;
		this.element = element;
	}

	public static <T> List<T> retrieveElements(List<WaitingRegistryEntry<T>> entries) {
		List<T> elements = new ArrayList<>();
		entries.forEach(entry -> elements.add(entry.element));
		return elements;
	}

	public T register(Registry<T> registry) {
		return Registry.register(registry, this.key.provideKey(registry), this.element);
	}
}
