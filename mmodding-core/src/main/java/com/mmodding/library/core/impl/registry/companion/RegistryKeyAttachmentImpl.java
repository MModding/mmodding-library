package com.mmodding.library.core.impl.registry.companion;

import com.mmodding.library.core.api.registry.companion.RegistryKeyAttachment;
import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap;
import net.minecraft.registry.DynamicRegistryManager;
import net.minecraft.registry.RegistryKey;
import org.jetbrains.annotations.Nullable;

import java.util.Map;
import java.util.function.BiFunction;

public class RegistryKeyAttachmentImpl<T, E> implements RegistryKeyAttachment<T, E> {

	private final BiFunction<DynamicRegistryManager, T, RegistryKey<T>> retriever;

	private final Map<RegistryKey<T>, E> map;

	public RegistryKeyAttachmentImpl(BiFunction<DynamicRegistryManager, T, RegistryKey<T>> retriever) {
		this.map = new Object2ObjectOpenHashMap<>();
		this.retriever = retriever;
	}

	@Override
	public void put(@Nullable DynamicRegistryManager manager, T object, E value) {
		this.put(this.retriever.apply(manager, object), value);
	}

	@Override
	public void put(RegistryKey<T> key, E value) {
		this.map.put(key, value);
	}

	@Override
	public E get(@Nullable DynamicRegistryManager manager, T object) {
		return this.get(this.retriever.apply(manager, object));
	}

	@Override
	public E get(RegistryKey<T> key) {
		return this.map.get(key);
	}
}
