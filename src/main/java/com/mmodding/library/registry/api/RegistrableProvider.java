package com.mmodding.library.registry.api;

import com.mmodding.library.registry.api.content.DoubleContentHolder;
import com.mmodding.library.registry.api.content.MultipleContentHolder;
import com.mmodding.library.registry.api.content.SimpleContentHolder;

public interface RegistrableProvider {

	static <T> SimpleContentHolder.Provider<T> simple(SimpleContentHolder.Provider<T> provider) {
		return provider;
	}

	static <L, R> DoubleContentHolder.Provider<L, R> bi(DoubleContentHolder.Provider<L, R> provider) {
		return provider;
	}

	static MultipleContentHolder.Provider multiple(MultipleContentHolder.Provider provider) {
		return provider;
	}
}
