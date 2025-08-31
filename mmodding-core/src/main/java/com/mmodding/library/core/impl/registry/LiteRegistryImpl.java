package com.mmodding.library.core.impl.registry;

import com.google.common.collect.Iterators;
import com.mmodding.library.core.api.registry.LiteRegistry;
import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.NotNull;

import java.util.Iterator;
import java.util.Map;
import java.util.function.BiFunction;
import java.util.function.Consumer;

public class LiteRegistryImpl<T> implements LiteRegistry<T> {

	private final Map<Identifier, T> content = new Object2ObjectOpenHashMap<>();

	private final Map<T, Identifier> reversed = new Object2ObjectOpenHashMap<>();

	@Override
	public boolean contains(Identifier identifier) {
		return this.content.containsKey(identifier);
	}

	@Override
	public T get(Identifier identifier) {
		return this.content.get(identifier);
	}

	@Override
	public Identifier getId(T entry) {
		return this.reversed.get(entry);
	}

	@Override
	public T register(Identifier identifier, T entry) {
		if (!this.content.containsKey(identifier)) {
			this.content.put(identifier, entry);
			this.reversed.put(entry, identifier);
			return entry;
		}
		else {
			throw new IllegalStateException("Object with identifier " + identifier + " already exists in the lite registry");
		}
	}

	@Override
	public void register(String namespace, Consumer<RegistrationFactory<T>> consumer) {
		consumer.accept(new RegistrationFactoryImpl<>(namespace, this::register));
	}

	@NotNull
	@Override
	public Iterator<Entry<T>> iterator() {
		return Iterators.transform(this.content.entrySet().iterator(), entry -> new EntryImpl<>(entry.getKey(), entry.getValue()));
	}

	private record EntryImpl<T>(Identifier identifier, T element) implements Entry<T> {}

	private static class RegistrationFactoryImpl<T> implements RegistrationFactory<T> {

		private final String namespace;
		private final BiFunction<Identifier, T, T> biConsumer;

		private RegistrationFactoryImpl(String namespace, BiFunction<Identifier, T, T> biConsumer) {
			this.namespace = namespace;
			this.biConsumer = biConsumer;
		}

		@Override
		public T register(String path, T entry) {
			return this.register(Identifier.of(this.namespace, path), entry);
		}

		@Override
		public T register(Identifier identifier, T entry) {
			return this.biConsumer.apply(identifier, entry);
		}
	}
}
