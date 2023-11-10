package com.mmodding.mmodding_lib.library.utils;

import org.apache.commons.lang3.tuple.ImmutableTriple;
import org.apache.commons.lang3.tuple.Triple;

import java.util.Map;
import java.util.function.BiConsumer;

public interface TriMap<K, V1, V2, V3> extends Map<K, Triple<V1, V2, V3>> {

	static <K, V> boolean contains(TriMap<K, V, V, V> triMap, V firstValue, V secondValue, V thirdValue) {
		return (triMap.containsValue(firstValue, secondValue, thirdValue) || triMap.containsValue(firstValue, thirdValue, secondValue))
			|| (triMap.containsValue(secondValue, firstValue, thirdValue) || triMap.containsValue(secondValue, thirdValue, firstValue))
			|| (triMap.containsValue(thirdValue, firstValue, secondValue) || triMap.containsValue(thirdValue, secondValue, firstValue));
	}

	static <V1, V2, V3> Triple<V1, V2, V3> emptyValue() {
		return new ImmutableTriple<>(null, null, null);
	}

	boolean containsValue(V1 firstValue, V2 secondValue, V3 thirdValue);

	V1 getFirstValue(K key);

	V2 getSecondValue(K key);

	V3 getThirdValue(K key);

	Triple<V1, V2, V3> put(K key, V1 firstValue, V2 secondValue, V3 thirdValue);

	void forEachFirst(BiConsumer<? super K, ? super V1> action);

	void forEachSecond(BiConsumer<? super K, ? super V2> action);

	void forEachThird(BiConsumer<? super K, ? super V3> action);

	void forEach(QuadConsumer<? super K, ? super V1, ? super V2, ? super V3> action);
}
