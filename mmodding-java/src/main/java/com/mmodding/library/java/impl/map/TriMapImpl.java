package com.mmodding.library.java.impl.map;

import com.mmodding.library.java.api.container.Triple;
import com.mmodding.library.java.api.function.consumer.QuartConsumer;
import com.mmodding.library.java.api.map.TriMap;
import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap;

import java.util.function.BiConsumer;

public class TriMapImpl<K, V1, V2, V3> extends Object2ObjectOpenHashMap<K, Triple<V1, V2, V3>> implements TriMap<K, V1, V2, V3> {

	public TriMapImpl(int initialCapacity, float loadFactor) {
		super(initialCapacity, loadFactor);
	}

	public TriMapImpl(int initialCapacity) {
		super(initialCapacity);
	}

	public TriMapImpl() {
		super();
	}

	public TriMapImpl(TriMap<? extends K, V1, V2, V3> m) {
		super(m);
	}

	@Override
	public boolean containsValue(V1 firstValue, V2 secondValue, V3 thirdValue) {
		return this.containsValue(Triple.create(firstValue, secondValue, thirdValue));
	}

	public V1 getFirstValue(K key) {
		return this.getOrDefault(key, TriMap.emptyValue()).first();
	}

	public V2 getSecondValue(K key) {
		return this.getOrDefault(key, TriMap.emptyValue()).second();
	}

	public V3 getThirdValue(K key) {
		return this.getOrDefault(key, TriMap.emptyValue()).third();
	}

	public Triple<V1, V2, V3> put(K key, V1 firstValue, V2 secondValue, V3 thirdValue) {
		return this.put(key, Triple.create(firstValue, secondValue, thirdValue));
	}

	public void forEachFirst(BiConsumer<? super K, ? super V1> action) {
		this.forEach((key, value) -> action.accept(key, value.first()));
	}

	public void forEachSecond(BiConsumer<? super K, ? super V2> action) {
		this.forEach((key, value) -> action.accept(key, value.second()));
	}

	public void forEachThird(BiConsumer<? super K, ? super V3> action) {
		this.forEach((key, value) -> action.accept(key, value.third()));
	}

	public void forEach(QuartConsumer<? super K, ? super V1, ? super V2, ? super V3> action) {
		this.forEach((key, value) -> action.accept(key, value.first(), value.second(), value.third()));
	}
}
