package com.mmodding.mmodding_lib.library.utils;

import org.apache.commons.lang3.tuple.ImmutableTriple;
import org.apache.commons.lang3.tuple.Triple;

import java.util.HashMap;
import java.util.function.BiConsumer;

public class TriHashMap<K, V1, V2, V3> extends HashMap<K, Triple<V1, V2, V3>> implements TriMap<K, V1, V2, V3> {

	public TriHashMap(int initialCapacity, float loadFactor) {
		super(initialCapacity, loadFactor);
	}

	public TriHashMap(int initialCapacity) {
		super(initialCapacity);
	}

	public TriHashMap() {
		super();
	}

	public TriHashMap(TriMap<? extends K, V1, V2, V3> m) {
		super(m);
	}


	@Override
	public boolean containsValue(V1 firstValue, V2 secondValue, V3 thirdValue) {
		return this.containsValue(new ImmutableTriple<>(firstValue, secondValue, thirdValue));
	}

	public V1 getFirstValue(K key) {
        return this.getOrDefault(key, TriMap.emptyValue()).getLeft();
    }

    public V2 getSecondValue(K key) {
        return this.getOrDefault(key, TriMap.emptyValue()).getMiddle();
    }

	public V3 getThirdValue(K key) {
		return this.getOrDefault(key, TriMap.emptyValue()).getRight();
	}

    public Triple<V1, V2, V3> put(K key, V1 firstValue, V2 secondValue, V3 thirdValue) {
        return this.put(key, new ImmutableTriple<>(firstValue, secondValue, thirdValue));
    }

    public void forEachFirst(BiConsumer<? super K, ? super V1> action) {
        this.forEach((key, value) -> action.accept(key, value.getLeft()));
    }

    public void forEachSecond(BiConsumer<? super K, ? super V2> action) {
        this.forEach((key, value) -> action.accept(key, value.getMiddle()));
    }

	public void forEachThird(BiConsumer<? super K, ? super V3> action) {
		this.forEach((key, value) -> action.accept(key, value.getRight()));
	}

    public void forEach(QuartConsumer<? super K, ? super V1, ? super V2, ? super V3> action) {
        this.forEach((key, value) -> action.accept(key, value.getLeft(), value.getMiddle(), value.getRight()));
    }
}
