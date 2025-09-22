package com.mmodding.library.core.impl.registry.attachment;

import com.mmodding.library.core.api.registry.attachment.RegistryKeyAttachment;
import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;

import java.util.Map;

public class RegistryKeyAttachmentImpl<T, E> implements RegistryKeyAttachment<T, E> {

	private final Registry<T> registry;
	private final Map<RegistryKey<T>, E> map;

	public RegistryKeyAttachmentImpl(Registry<T> registry) {
		this.registry = registry;
		this.map = new Object2ObjectOpenHashMap<>();
	}

	@Override
	public void put(T object, E value) {
		this.put(this.registry.getKey(object).orElseThrow(), value);
	}

	@Override
	public void put(RegistryKey<T> key, E value) {
		this.map.put(key, value);
	}

	@Override
	public E get(T object) {
		return this.get(this.registry.getKey(object).orElseThrow());
	}

	@Override
	public E get(RegistryKey<T> key) {
		return this.map.get(key);
	}
}
