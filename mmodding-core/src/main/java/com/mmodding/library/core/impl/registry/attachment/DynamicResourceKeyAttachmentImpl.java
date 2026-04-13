package com.mmodding.library.core.impl.registry.attachment;

import com.mmodding.library.core.api.registry.attachment.DynamicResourceKeyAttachment;
import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap;
import java.util.Map;
import java.util.Optional;

import net.minecraft.core.Registry;
import net.minecraft.core.RegistryAccess;
import net.minecraft.resources.ResourceKey;

public class DynamicResourceKeyAttachmentImpl<T, E> implements DynamicResourceKeyAttachment<T, E> {

	private final ResourceKey<? extends Registry<T>> registry;
	private final Map<ResourceKey<T>, E> map;

	public DynamicResourceKeyAttachmentImpl(ResourceKey<? extends Registry<T>> registry) {
		this.registry = registry;
		this.map = new Object2ObjectOpenHashMap<>();
	}

	@Override
	public void put(RegistryAccess registries, T object, E value) {
		Optional<Registry<T>> dynamic = registries.lookup(this.registry);
		if (dynamic.isPresent()) {
			this.map.put(dynamic.get().getResourceKey(object).orElseThrow(), value);
		}
		else {
			throw new IllegalStateException("Registry " + this.registry + " was not found in the registry access");
		}
	}

	@Override
	public void put(ResourceKey<T> key, E value) {
		this.map.put(key, value);
	}

	@Override
	public boolean contains(RegistryAccess registries, T object) {
		Optional<Registry<T>> dynamic = registries.lookup(this.registry);
		if (dynamic.isPresent()) {
			return this.map.containsKey(dynamic.get().getResourceKey(object).orElseThrow());
		}
		else {
			throw new IllegalStateException("Registry " + this.registry + " was not found in the registry access");
		}
	}

	@Override
	public boolean contains(ResourceKey<T> key) {
		return this.map.containsKey(key);
	}

	@Override
	public E get(RegistryAccess registries, T object) {
		Optional<Registry<T>> dynamic = registries.lookup(this.registry);
		if (dynamic.isPresent()) {
			return this.map.get(dynamic.get().getResourceKey(object).orElseThrow());
		}
		else {
			throw new IllegalStateException("Registry " + this.registry + " was not found in the registry access");
		}
	}

	@Override
	public E get(ResourceKey<T> key) {
		return this.map.get(key);
	}
}
