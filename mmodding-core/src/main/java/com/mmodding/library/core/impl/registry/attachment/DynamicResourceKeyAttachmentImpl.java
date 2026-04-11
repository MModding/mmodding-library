package com.mmodding.library.core.impl.registry.attachment;

import com.mmodding.library.core.api.registry.attachment.DynamicResourceKeyAttachment;
import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap;
import java.util.Map;
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
	public void put(RegistryAccess manager, T object, E value) {
		Registry<T> dynamic = manager.registryOrThrow(this.registry);
		if (dynamic != null) {
			this.map.put(manager.registryOrThrow(this.registry).getResourceKey(object).orElseThrow(), value);
		}
		else {
			throw new IllegalStateException("Registry " + this.registry + " was not found in the dynamic registry manager");
		}
	}

	@Override
	public void put(ResourceKey<T> key, E value) {
		this.map.put(key, value);
	}

	@Override
	public E get(RegistryAccess manager, T object) {
		Registry<T> dynamic = manager.registryOrThrow(this.registry);
		if (dynamic != null) {
			return this.map.get(manager.registryOrThrow(this.registry).getResourceKey(object).orElseThrow());
		}
		else {
			throw new IllegalStateException("Registry " + this.registry + " was not found in the dynamic registry manager");
		}
	}

	@Override
	public E get(ResourceKey<T> key) {
		return this.map.get(key);
	}
}
