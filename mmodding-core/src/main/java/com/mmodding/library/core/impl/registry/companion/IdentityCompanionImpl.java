package com.mmodding.library.core.impl.registry.companion;

import com.mmodding.library.core.api.registry.LiteRegistry;
import com.mmodding.library.core.api.registry.companion.IdentityCompanion;

import java.util.IdentityHashMap;
import java.util.Map;

public class IdentityCompanionImpl<T, E> implements IdentityCompanion<T, E> {

	private final Map<T, LiteRegistry<E>> map;

	public IdentityCompanionImpl() {
		this.map = new IdentityHashMap<>();
	}

	@Override
	public LiteRegistry<E> getOrCreateCompanion(T object) {
		if (this.getCompanion(object) == null) {
			this.map.put(object, LiteRegistry.create());
		}
		return this.getCompanion(object);
	}

	@Override
	public boolean hasCompanion(T object) {
		return this.map.containsKey(object);
	}

	@Override
	public LiteRegistry<E> getCompanion(T object) {
		return this.map.get(object);
	}
}
