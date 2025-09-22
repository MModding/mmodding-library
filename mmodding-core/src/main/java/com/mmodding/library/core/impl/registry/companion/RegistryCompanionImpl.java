package com.mmodding.library.core.impl.registry.companion;

import com.mmodding.library.core.api.registry.LiteRegistry;
import com.mmodding.library.core.api.registry.companion.RegistryCompanion;
import com.mmodding.library.core.api.registry.attachment.RegistryKeyAttachment;
import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;

import java.util.Map;
import java.util.UUID;

public class RegistryCompanionImpl<T, E> implements RegistryCompanion<T, E> {

	private final RegistryKeyAttachment<T, UUID> rka;
	private final Map<UUID, LiteRegistry<E>> map;

	public RegistryCompanionImpl(Registry<T> registry) {
		this.rka = RegistryKeyAttachment.create(registry);
		this.map = new Object2ObjectOpenHashMap<>();
	}

	@Override
	public LiteRegistry<E> getOrCreateCompanion(T object) {
		if (this.getCompanion(object) == null) {
			UUID uuid = UUID.randomUUID();
			this.rka.put(object, uuid);
			this.map.put(uuid, LiteRegistry.create());
		}
		return this.getCompanion(object);
	}

	@Override
	public LiteRegistry<E> getOrCreateCompanion(RegistryKey<T> key) {
		if (this.getCompanion(key) == null) {
			UUID uuid = UUID.randomUUID();
			this.rka.put(key, uuid);
			this.map.put(uuid, LiteRegistry.create());
		}
		return this.getCompanion(key);
	}

	@Override
	public LiteRegistry<E> getCompanion(T object) {
		return this.map.get(this.rka.get(object));
	}

	@Override
	public LiteRegistry<E> getCompanion(RegistryKey<T> key) {
		return this.map.get(this.rka.get(key));
	}
}
