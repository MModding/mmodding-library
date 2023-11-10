package com.mmodding.mmodding_lib.library.utils;

import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;
import org.apache.logging.log4j.util.TriConsumer;

import java.util.HashMap;
import java.util.function.BiConsumer;

public class BiHashMap<K, V1, V2> extends HashMap<K, Pair<V1, V2>> implements BiMap<K, V1, V2> {

	public BiHashMap(int initialCapacity, float loadFactor) {
		super(initialCapacity, loadFactor);
	}

	public BiHashMap(int initialCapacity) {
		super(initialCapacity);
	}

	public BiHashMap() {
		super();
	}

	public BiHashMap(BiMap<? extends K, V1, V2> m) {
		super(m);
	}


	@Override
	public boolean containsValue(V1 firstValue, V2 secondValue) {
		return this.containsValue(new ImmutablePair<>(firstValue, secondValue));
	}

	public V1 getFirstValue(K key) {
        return this.getOrDefault(key, BiMap.emptyValue()).getLeft();
    }

    public V2 getSecondValue(K key) {
        return this.getOrDefault(key, BiMap.emptyValue()).getRight();
    }

    public Pair<V1, V2> put(K key, V1 firstValue, V2 secondValue) {
        return this.put(key, new ImmutablePair<>(firstValue, secondValue));
    }

    public void forEachFirst(BiConsumer<? super K, ? super V1> action) {
        this.forEach((key, value) -> action.accept(key, value.getLeft()));
    }

    public void forEachSecond(BiConsumer<? super K, ? super V2> action) {
        this.forEach((key, value) -> action.accept(key, value.getRight()));
    }

    public void forEach(TriConsumer<? super K, ? super V1, ? super V2> action) {
        this.forEach((key, value) -> action.accept(key, value.getLeft(), value.getRight()));
    }
}
