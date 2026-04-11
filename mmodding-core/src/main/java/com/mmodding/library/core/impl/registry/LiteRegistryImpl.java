package com.mmodding.library.core.impl.registry;

import com.google.common.collect.Iterators;
import com.mmodding.library.core.api.registry.LiteRegistry;
import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap;
import org.jetbrains.annotations.NotNull;

import java.util.Iterator;
import java.util.Map;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import net.minecraft.resources.ResourceLocation;

public class LiteRegistryImpl<T> implements LiteRegistry<T> {

	private final Map<ResourceLocation, T> content = new Object2ObjectOpenHashMap<>();

	private final Map<T, ResourceLocation> reversed = new Object2ObjectOpenHashMap<>();

	@Override
	public boolean contains(ResourceLocation identifier) {
		return this.content.containsKey(identifier);
	}

	@Override
	public T get(ResourceLocation identifier) {
		return this.content.get(identifier);
	}

	@Override
	public ResourceLocation getId(T entry) {
		return this.reversed.get(entry);
	}

	@Override
	public T register(ResourceLocation identifier, T entry) {
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
	public void register(String namespace, Consumer<LiteRegistrationFactory<T>> consumer) {
		consumer.accept(new LiteRegistrationFactoryImpl<>(namespace, this::register));
	}

	@NotNull
	@Override
	public Iterator<Entry<T>> iterator() {
		return Iterators.transform(this.content.entrySet().iterator(), entry -> new EntryImpl<>(entry.getKey(), entry.getValue()));
	}

	private record EntryImpl<T>(ResourceLocation identifier, T element) implements Entry<T> {}

	private static class LiteRegistrationFactoryImpl<T> implements LiteRegistrationFactory<T> {

		private final String namespace;
		private final BiFunction<ResourceLocation, T, T> biConsumer;

		private LiteRegistrationFactoryImpl(String namespace, BiFunction<ResourceLocation, T, T> biConsumer) {
			this.namespace = namespace;
			this.biConsumer = biConsumer;
		}

		@Override
		public T register(String path, T entry) {
			return this.register(ResourceLocation.tryBuild(this.namespace, path), entry);
		}

		@Override
		public T register(ResourceLocation identifier, T entry) {
			return this.biConsumer.apply(identifier, entry);
		}
	}
}
