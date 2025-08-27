package com.mmodding.library.java.api.object;

import java.util.function.Consumer;
import java.util.function.Supplier;

public interface Lazy<T> {

	static <T> Lazy<T> prepare(Supplier<T> initializer) {
		return null;
	}

	/**
	 * Assigns the returned value from the initializer before sealing the lazy.
	 * @exception IllegalStateException when trying to assign a value to a sealed lazy
	 */
	void initialize();

	boolean isInitialized();

	T get();

	T orElse(T object);

	void ifInitialized(Consumer<T> consumer);
}
