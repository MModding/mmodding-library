package com.mmodding.library.core.api.registry;

import com.mmodding.library.core.impl.registry.LiteRegistryImpl;
import net.minecraft.util.Identifier;

public interface LiteRegistry<T> extends Iterable<LiteRegistry.Entry<T>> {

	static <T> LiteRegistry<T> create() {
		return new LiteRegistryImpl<>();
	}

	boolean contains(Identifier identifier);

	T getEntry(Identifier identifier);

	Identifier getIdentifier(T entry);

	T register(Identifier identifier, T entry);

	interface Entry<T> {

		Identifier identifier();

		T entry();
	}
}
