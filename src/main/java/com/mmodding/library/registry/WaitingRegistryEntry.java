package com.mmodding.library.registry;

import com.mmodding.library.core.api.Reference;
import net.minecraft.registry.Registry;

import java.util.ArrayList;
import java.util.List;

public class WaitingRegistryEntry<T> {

	private final Reference<T> reference;
	private final T element;

	public WaitingRegistryEntry(Reference<T> reference, T element) {
		this.reference = reference;
		this.element = element;
	}

	public static <T> List<T> retrieveElements(List<WaitingRegistryEntry<T>> entries) {
		List<T> elements = new ArrayList<>();
		entries.forEach(entry -> elements.add(entry.element));
		return elements;
	}

	public T register(Registry<T> registry) {
		return Registry.register(registry, this.reference.provideKey(registry), this.element);
	}
}
