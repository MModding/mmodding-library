package com.mmodding.library.core.impl.registry.attachment;

import com.mmodding.library.core.api.registry.attachment.DynamicRegistryKeyAttachment;
import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap;
import net.minecraft.registry.DynamicRegistryManager;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;

import java.util.Map;

public class DynamicRegistryKeyAttachmentImpl<T, E> implements DynamicRegistryKeyAttachment<T, E> {

	private final RegistryKey<? extends Registry<T>> registry;
	private final Map<RegistryKey<T>, E> map;

	public DynamicRegistryKeyAttachmentImpl(RegistryKey<? extends Registry<T>> registry) {
		this.registry = registry;
		this.map = new Object2ObjectOpenHashMap<>();
	}

	@Override
	public void put(DynamicRegistryManager manager, T object, E value) {
		Registry<T> dynamic = manager.get(this.registry);
		if (dynamic != null) {
			this.map.put(manager.get(this.registry).getKey(object).orElseThrow(), value);
		}
		else {
			throw new IllegalStateException("Registry " + this.registry + " was not found in the dynamic registry manager");
		}
	}

	@Override
	public void put(RegistryKey<T> key, E value) {
		this.map.put(key, value);
	}

	@Override
	public E get(DynamicRegistryManager manager, T object) {
		Registry<T> dynamic = manager.get(this.registry);
		if (dynamic != null) {
			return this.map.get(manager.get(this.registry).getKey(object).orElseThrow());
		}
		else {
			throw new IllegalStateException("Registry " + this.registry + " was not found in the dynamic registry manager");
		}
	}

	@Override
	public E get(RegistryKey<T> key) {
		return this.map.get(key);
	}
}
