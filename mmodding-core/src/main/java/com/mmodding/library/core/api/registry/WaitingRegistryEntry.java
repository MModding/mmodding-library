package com.mmodding.library.core.api.registry;

import java.util.ArrayList;
import java.util.List;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;

public class WaitingRegistryEntry<T> {

	private final ResourceKey<T> key;
	private final T element;

	public WaitingRegistryEntry(ResourceKey<T> key, T element) {
		this.key = key;
		this.element = element;
	}

	public static <T> List<T> retrieveElements(List<WaitingRegistryEntry<T>> entries) {
		List<T> elements = new ArrayList<>();
		entries.forEach(entry -> elements.add(entry.element));
		return elements;
	}

	public T register(Registry<T> registry) {
		return Registry.register(registry, this.key, this.element);
	}
}
