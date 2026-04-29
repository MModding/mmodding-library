package com.mmodding.library.java.impl.map;

import com.mmodding.library.java.api.container.Typed;
import com.mmodding.library.java.api.function.consumer.TriConsumer;
import com.mmodding.library.java.api.map.MixedMap;
import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap;

@SuppressWarnings("unchecked")
public class MixedMapImpl<K> extends Object2ObjectOpenHashMap<K, Typed<?>> implements MixedMap<K> {

	public MixedMapImpl(int initialCapacity, float loadFactor) {
		super(initialCapacity, loadFactor);
	}

	public MixedMapImpl(int initialCapacity) {
		super(initialCapacity);
	}

	public MixedMapImpl() {
		super();
	}

	public MixedMapImpl(MixedMap<? extends K> m) {
		super(m);
	}

	@Override
	public <V> boolean containsValue(Class<?> type, V value) {
		return super.containsValue(Typed.of(type, value));
	}

	@Override
	public <V> V get(K key, Class<?> type) {
		Typed<?> typed = super.get(key);
		if (typed == null) {
			typed = MixedMap.emptyValue(type);
		}
		if (type.equals(typed.getType())) {
			return (V) typed.getValue();
		}
		else {
			throw new IllegalArgumentException("Given type does not match the targeted type!");
		}
	}

	@Override
	public <V> V getOrDefault(K key, Class<?> type, V defaultValue) {
		Typed<?> typed = super.getOrDefault(key, Typed.of(type, defaultValue));
		if (typed == null) {
			typed = MixedMap.emptyValue(type);
		}
		if (type.equals(typed.getType())) {
			return (V) typed.getValue();
		}
		else {
			throw new IllegalArgumentException("Given type does not match the targeted type!");
		}
	}

	@Override
	public <V> V put(K key, Class<?> type, V value) {
		Typed<V> typed = (Typed<V>) super.put(key, Typed.of(type, value));
		return typed != null ? typed.getValue() : null;
	}

	@Override
	public <V> void forEach(TriConsumer<? super K, ? super Class<?>, ? super V> action) {
		this.forEach((key, value) -> action.accept(key, value.getType(), (V) value.getValue()));
	}
}
