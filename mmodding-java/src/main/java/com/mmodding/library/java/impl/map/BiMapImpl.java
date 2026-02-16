package com.mmodding.library.java.impl.map;

import com.mmodding.library.java.api.function.consumer.TriConsumer;
import com.mmodding.library.java.api.map.BiMap;
import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;

import java.util.function.BiConsumer;

public class BiMapImpl<K, V1, V2> extends Object2ObjectOpenHashMap<K, Pair<V1, V2>> implements BiMap<K, V1, V2> {

	public BiMapImpl(int initialCapacity, float loadFactor) {
		super(initialCapacity, loadFactor);
	}

	public BiMapImpl(int initialCapacity) {
		super(initialCapacity);
	}

	public BiMapImpl() {
		super();
	}

	public BiMapImpl(BiMap<? extends K, V1, V2> m) {
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
