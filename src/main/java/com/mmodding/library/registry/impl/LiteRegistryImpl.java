package com.mmodding.library.registry.impl;

import com.google.common.collect.Iterators;
import com.mmodding.library.registry.LiteRegistry;
import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.NotNull;

import java.util.Iterator;
import java.util.Map;

public class LiteRegistryImpl<T> implements LiteRegistry<T> {

	private final Map<Identifier, T> content = new Object2ObjectOpenHashMap<>();

	private final Map<T, Identifier> reversed = new Object2ObjectOpenHashMap<>();

	@Override
	public T getEntry(Identifier identifier) {
		return this.content.get(identifier);
	}

	@Override
	public Identifier getIdentifier(T entry) {
		return this.reversed.get(entry);
	}

	@Override
	public T register(Identifier identifier, T entry) {
		this.content.put(identifier, entry);
		this.reversed.put(entry, identifier);
		return entry;
	}

	@NotNull
	@Override
	public Iterator<Entry<T>> iterator() {
		return Iterators.transform(this.content.entrySet().iterator(), mapEntry -> new EntryImpl<>(mapEntry.getKey(), mapEntry.getValue()));
	}

	private record EntryImpl<T>(Identifier identifier, T entry) implements Entry<T> {}
}
