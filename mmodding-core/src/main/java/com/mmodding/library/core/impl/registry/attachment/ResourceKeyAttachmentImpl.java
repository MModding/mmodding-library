package com.mmodding.library.core.impl.registry.attachment;

import com.mmodding.library.core.api.registry.attachment.ResourceKeyAttachment;
import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap;
import java.util.Map;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;

public class ResourceKeyAttachmentImpl<T, E> implements ResourceKeyAttachment<T, E> {

	private final Registry<T> registry;
	private final Map<ResourceKey<T>, E> map;

	public ResourceKeyAttachmentImpl(Registry<T> registry) {
		this.registry = registry;
		this.map = new Object2ObjectOpenHashMap<>();
	}

	@Override
	public void put(T object, E value) {
		this.put(this.registry.getResourceKey(object).orElseThrow(), value);
	}

	@Override
	public void put(ResourceKey<T> key, E value) {
		this.map.put(key, value);
	}

	@Override
	public boolean contains(T object) {
		return this.contains(this.registry.getResourceKey(object).orElseThrow());
	}

	@Override
	public boolean contains(ResourceKey<T> key) {
		return this.map.containsKey(key);
	}

	@Override
	public E get(T object) {
		return this.get(this.registry.getResourceKey(object).orElseThrow());
	}

	@Override
	public E get(ResourceKey<T> key) {
		return this.map.get(key);
	}
}
