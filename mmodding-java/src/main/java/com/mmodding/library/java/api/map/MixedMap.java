package com.mmodding.library.java.api.map;

import com.mmodding.library.java.api.container.Typed;
import com.mmodding.library.java.impl.map.MixedMapImpl;
import com.mmodding.library.java.impl.map.linked.LinkedMixedMapImpl;
import org.apache.logging.log4j.util.TriConsumer;

import java.util.Map;

public interface MixedMap<K> extends Map<K, Typed<?>> {

	static <K> MixedMap<K> create() {
		return new MixedMapImpl<>();
	}

	static <K> MixedMap<K> linked() {
		return new LinkedMixedMapImpl<>();
	}

	static <V> Typed<V> emptyValue(Class<V> type) {
		return Typed.of(type, null);
	}

	<V> boolean containsValue(Class<V> type, V value);

	<V> V get(K key, Class<V> type);

	<V> V put(K key, Class<V> type, V value);

	<V> void forEach(TriConsumer<? super K, ? super Class<V>, ? super V> action);
}
