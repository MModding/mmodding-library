package com.mmodding.mmodding_lib.library.utils;

import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;
import org.apache.logging.log4j.util.TriConsumer;

import java.util.Map;
import java.util.function.BiConsumer;

public interface BiMap<K, V1, V2> extends Map<K, Pair<V1, V2>> {

	static <K, V> boolean contains(BiMap<K, V, V> biMap, V firstValue, V secondValue) {
		return biMap.containsValue(firstValue, secondValue) || biMap.containsValue(secondValue, firstValue);
	}

	static <V1, V2> Pair<V1, V2> emptyValue() {
		return new ImmutablePair<>(null, null);
	}

	boolean containsValue(V1 firstValue, V2 secondValue);

	V1 getFirstValue(K key);

	V2 getSecondValue(K key);

	Pair<V1, V2> put(K key, V1 firstValue, V2 secondValue);

	void forEachFirst(BiConsumer<? super K, ? super V1> action);

	void forEachSecond(BiConsumer<? super K, ? super V2> action);

	void forEach(TriConsumer<? super K, ? super V1, ? super V2> action);
}
