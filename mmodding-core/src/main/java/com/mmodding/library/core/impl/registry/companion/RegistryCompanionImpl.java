package com.mmodding.library.core.impl.registry.companion;

import com.mmodding.library.core.api.registry.LiteRegistry;
import com.mmodding.library.core.api.registry.companion.RegistryCompanion;
import com.mmodding.library.core.api.registry.companion.RegistryKeyAttachment;
import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap;
import net.minecraft.registry.DynamicRegistryManager;
import net.minecraft.registry.RegistryKey;
import org.jetbrains.annotations.Nullable;

import java.util.Map;
import java.util.UUID;
import java.util.function.BiFunction;

public class RegistryCompanionImpl<T, E> implements RegistryCompanion<T, E> {

	private final RegistryKeyAttachment<T, String> rka;

	private final Map<String, LiteRegistry<E>> map;

	public RegistryCompanionImpl(BiFunction<DynamicRegistryManager, T, RegistryKey<T>> retriever) {
		this.rka = RegistryKeyAttachment.create(retriever);
		this.map = new Object2ObjectOpenHashMap<>();
	}

	@Override
	public LiteRegistry<E> getOrCreateCompanion(T object) {
		return this.getOrCreateCompanion(null, object);
	}

	@Override
	public LiteRegistry<E> getOrCreateCompanion(@Nullable DynamicRegistryManager manager, T object) {
		if (this.getCompanion(manager, object) == null) {
			String uuid = UUID.randomUUID().toString();
			this.rka.put(manager, object, uuid);
			this.map.put(uuid, LiteRegistry.create());
		}
		return this.getCompanion(manager, object);
	}

	@Override
	public LiteRegistry<E> getOrCreateCompanion(RegistryKey<T> key) {
		if (this.getCompanion(key) == null) {
			String uuid = UUID.randomUUID().toString();
			this.rka.put(key, uuid);
			this.map.put(uuid, LiteRegistry.create());
		}
		return this.getCompanion(key);
	}

	@Override
	public LiteRegistry<E> getCompanion(T object) {
		return this.getCompanion(null, object);
	}

	@Override
	public LiteRegistry<E> getCompanion(@Nullable DynamicRegistryManager manager, T object) {
		return this.map.get(this.rka.get(manager, object));
	}

	@Override
	public LiteRegistry<E> getCompanion(RegistryKey<T> key) {
		return this.map.get(this.rka.get(key));
	}
}
