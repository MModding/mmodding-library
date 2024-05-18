package com.mmodding.library.java.api.map;

import com.mmodding.library.java.api.container.Triple;
import com.mmodding.library.java.api.function.consumer.QuartConsumer;
import com.mmodding.library.java.impl.map.TriMapImpl;

import java.util.Map;
import java.util.function.BiConsumer;

public interface TriMap<K, V1, V2, V3> extends Map<K, Triple<V1, V2, V3>> {

	static <K, V1, V2, V3> TriMap<K, V1, V2, V3> create() {
		return new TriMapImpl<>();
	}

	static <K, V> boolean contains(TriMap<K, V, V, V> triMap, V firstValue, V secondValue, V thirdValue) {
		return (triMap.containsValue(firstValue, secondValue, thirdValue) || triMap.containsValue(firstValue, thirdValue, secondValue))
			|| (triMap.containsValue(secondValue, firstValue, thirdValue) || triMap.containsValue(secondValue, thirdValue, firstValue))
			|| (triMap.containsValue(thirdValue, firstValue, secondValue) || triMap.containsValue(thirdValue, secondValue, firstValue));
	}

	static <V1, V2, V3> Triple<V1, V2, V3> emptyValue() {
		return Triple.create(null, null, null);
	}

	boolean containsValue(V1 firstValue, V2 secondValue, V3 thirdValue);

	V1 getFirstValue(K key);

	V2 getSecondValue(K key);

	V3 getThirdValue(K key);

	Triple<V1, V2, V3> put(K key, V1 firstValue, V2 secondValue, V3 thirdValue);

	void forEachFirst(BiConsumer<? super K, ? super V1> action);

	void forEachSecond(BiConsumer<? super K, ? super V2> action);

	void forEachThird(BiConsumer<? super K, ? super V3> action);

	void forEach(QuartConsumer<? super K, ? super V1, ? super V2, ? super V3> action);
}
