package com.mmodding.library.core.impl.registry.companion;

import com.mmodding.library.core.api.registry.LiteRegistry;
import com.mmodding.library.core.api.registry.companion.DynamicRegistryCompanion;
import com.mmodding.library.core.api.registry.attachment.DynamicResourceKeyAttachment;
import net.minecraft.core.Registry;
import net.minecraft.core.RegistryAccess;
import net.minecraft.resources.ResourceKey;

public class DynamicRegistryCompanionImpl<T, E> implements DynamicRegistryCompanion<T, E> {

	private final DynamicResourceKeyAttachment<T, LiteRegistry<E>> drka;

	public DynamicRegistryCompanionImpl(ResourceKey<? extends Registry<T>> registry) {
		this.drka = DynamicResourceKeyAttachment.create(registry);
	}

	@Override
	public LiteRegistry<E> getOrCreateCompanion(RegistryAccess manager, T object) {
		if (this.getCompanion(manager, object) == null) {
			this.drka.put(manager, object, LiteRegistry.create());
		}
		return this.getCompanion(manager, object);
	}

	@Override
	public LiteRegistry<E> getOrCreateCompanion(ResourceKey<T> key) {
		if (this.getCompanion(key) == null) {
			this.drka.put(key, LiteRegistry.create());
		}
		return this.getCompanion(key);
	}

	@Override
	public LiteRegistry<E> getCompanion(RegistryAccess manager, T object) {
		return this.drka.get(manager, object);
	}

	@Override
	public LiteRegistry<E> getCompanion(ResourceKey<T> key) {
		return this.drka.get(key);
	}
}
