package com.mmodding.library.registry.api;

import com.mmodding.library.registry.impl.LiteRegistryImpl;
import net.minecraft.util.Identifier;

public interface LiteRegistry<T> extends Iterable<LiteRegistry.Entry<T>> {

	static <T> LiteRegistry<T> create() {
		return new LiteRegistryImpl<>();
	}

	T getEntry(Identifier identifier);

	Identifier getIdentifier(T entry);

	T register(Identifier identifier, T entry);

	interface Entry<T> {

		Identifier identifier();

		T entry();
	}
}
