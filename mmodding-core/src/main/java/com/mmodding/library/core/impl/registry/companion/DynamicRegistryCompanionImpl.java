package com.mmodding.library.core.impl.registry.companion;

import com.mmodding.library.core.api.registry.LiteRegistry;
import com.mmodding.library.core.api.registry.companion.DynamicRegistryCompanion;
import com.mmodding.library.core.api.registry.extension.DynamicRegistryKeyAttachment;
import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap;
import net.minecraft.registry.DynamicRegistryManager;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;

import java.util.Map;
import java.util.UUID;

public class DynamicRegistryCompanionImpl<T, E> implements DynamicRegistryCompanion<T, E> {

	private final DynamicRegistryKeyAttachment<T, UUID> drka;
	private final Map<UUID, LiteRegistry<E>> map;

	public DynamicRegistryCompanionImpl(RegistryKey<? extends Registry<T>> registry) {
		this.drka = DynamicRegistryKeyAttachment.create(registry);
		this.map = new Object2ObjectOpenHashMap<>();
	}

	@Override
	public LiteRegistry<E> getOrCreateCompanion(DynamicRegistryManager manager, T object) {
		if (this.getCompanion(manager, object) == null) {
			UUID uuid = UUID.randomUUID();
			this.drka.put(manager, object, uuid);
			this.map.put(uuid, LiteRegistry.create());
		}
		return this.getCompanion(manager, object);
	}

	@Override
	public LiteRegistry<E> getOrCreateCompanion(RegistryKey<T> key) {
		if (this.getCompanion(key) == null) {
			UUID uuid = UUID.randomUUID();
			this.drka.put(key, uuid);
			this.map.put(uuid, LiteRegistry.create());
		}
		return this.getCompanion(key);
	}

	@Override
	public LiteRegistry<E> getCompanion(DynamicRegistryManager manager, T object) {
		return this.map.get(this.drka.get(manager, object));
	}

	@Override
	public LiteRegistry<E> getCompanion(RegistryKey<T> key) {
		return this.map.get(this.drka.get(key));
	}
}
