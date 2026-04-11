package com.mmodding.library.core.impl.registry.companion;

import com.mmodding.library.core.api.registry.LiteRegistry;
import com.mmodding.library.core.api.registry.companion.DynamicRegistryCompanion;
import com.mmodding.library.core.api.registry.attachment.DynamicResourceKeyAttachment;
import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap;
import java.util.Map;
import java.util.UUID;
import net.minecraft.core.Registry;
import net.minecraft.core.RegistryAccess;
import net.minecraft.resources.ResourceKey;

public class DynamicRegistryCompanionImpl<T, E> implements DynamicRegistryCompanion<T, E> {

	private final DynamicResourceKeyAttachment<T, UUID> drka;
	private final Map<UUID, LiteRegistry<E>> map;

	public DynamicRegistryCompanionImpl(ResourceKey<? extends Registry<T>> registry) {
		this.drka = DynamicResourceKeyAttachment.create(registry);
		this.map = new Object2ObjectOpenHashMap<>();
	}

	@Override
	public LiteRegistry<E> getOrCreateCompanion(RegistryAccess manager, T object) {
		if (this.getCompanion(manager, object) == null) {
			UUID uuid = UUID.randomUUID();
			this.drka.put(manager, object, uuid);
			this.map.put(uuid, LiteRegistry.create());
		}
		return this.getCompanion(manager, object);
	}

	@Override
	public LiteRegistry<E> getOrCreateCompanion(ResourceKey<T> key) {
		if (this.getCompanion(key) == null) {
			UUID uuid = UUID.randomUUID();
			this.drka.put(key, uuid);
			this.map.put(uuid, LiteRegistry.create());
		}
		return this.getCompanion(key);
	}

	@Override
	public LiteRegistry<E> getCompanion(RegistryAccess manager, T object) {
		return this.map.get(this.drka.get(manager, object));
	}

	@Override
	public LiteRegistry<E> getCompanion(ResourceKey<T> key) {
		return this.map.get(this.drka.get(key));
	}
}
