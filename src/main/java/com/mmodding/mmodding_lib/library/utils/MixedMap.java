package com.mmodding.mmodding_lib.library.utils;

import org.apache.logging.log4j.util.TriConsumer;

import java.util.Map;

public interface MixedMap<K> extends Map<K, TypedObject<?>> {

	static <V> TypedObject<V> emptyValue(Class<V> type) {
		return TypedObject.of(type, null);
	}

	<V> boolean containsValue(Class<V> type, V value);

	<V> V get(K key, Class<V> type);

	<V> V put(K key, Class<V> type, V value);

	<V> void forEach(TriConsumer<? super K, ? super Class<V>, ? super V> action);
}
