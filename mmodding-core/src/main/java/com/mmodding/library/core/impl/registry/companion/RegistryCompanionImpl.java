package com.mmodding.library.core.impl.registry.companion;

import com.mmodding.library.core.api.registry.LiteRegistry;
import com.mmodding.library.core.api.registry.companion.RegistryCompanion;
import com.mmodding.library.core.api.registry.attachment.ResourceKeyAttachment;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;

public class RegistryCompanionImpl<T, E> implements RegistryCompanion<T, E> {

	private final ResourceKeyAttachment<T, LiteRegistry<E>> rka;

	public RegistryCompanionImpl(Registry<T> registry) {
		this.rka = ResourceKeyAttachment.create(registry);
	}

	@Override
	public LiteRegistry<E> getOrCreateCompanion(T object) {
		if (this.getCompanion(object) == null) {
			this.rka.put(object, LiteRegistry.create());
		}
		return this.getCompanion(object);
	}

	@Override
	public LiteRegistry<E> getOrCreateCompanion(ResourceKey<T> key) {
		if (this.getCompanion(key) == null) {
			this.rka.put(key, LiteRegistry.create());
		}
		return this.getCompanion(key);
	}

	@Override
	public boolean hasCompanion(T object) {
		return this.rka.contains(object);
	}

	@Override
	public boolean hasCompanion(ResourceKey<T> key) {
		return this.rka.contains(key);
	}

	@Override
	public LiteRegistry<E> getCompanion(T object) {
		return this.rka.get(object);
	}

	@Override
	public LiteRegistry<E> getCompanion(ResourceKey<T> key) {
		return this.rka.get(key);
	}
}
