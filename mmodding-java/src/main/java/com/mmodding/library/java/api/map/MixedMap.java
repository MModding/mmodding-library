package com.mmodding.library.java.api.map;

import com.mmodding.library.java.api.container.Typed;
import com.mmodding.library.java.api.function.consumer.TriConsumer;
import com.mmodding.library.java.impl.map.MixedMapImpl;
import com.mmodding.library.java.impl.map.linked.LinkedMixedMapImpl;

import java.util.Map;

public interface MixedMap<K> extends Map<K, Typed<?>> {

	static <K> MixedMap<K> create() {
		return new MixedMapImpl<>();
	}

	static <K> MixedMap<K> linked() {
		return new LinkedMixedMapImpl<>();
	}

	static <V> Typed<V> emptyValue(Class<?> type) {
		return Typed.of(type, null);
	}

	<V> boolean containsValue(Class<?> type, V value);

	<V> V get(K key, Class<?> type);

	<V> V getOrDefault(K key, Class<?> type, V defaultValue);

	<V> V put(K key, Class<?> type, V value);

	<V> void forEach(TriConsumer<? super K, ? super Class<?>, ? super V> action);
}
