package com.mmodding.library.java.api.object;

import com.mmodding.library.java.impl.object.SealImpl;

import java.util.function.Consumer;
import java.util.function.Supplier;

public interface Seal<T> {

	static <T> Seal<T> create() {
		return new SealImpl<>();
	}

	/**
	 * Assigns a value to the holder before sealing it.
	 * @param supplier a supplier retrieving the object to assign to the holder
	 * @exception IllegalStateException when trying to assign a value to a sealed holder
	 */
	void assign(Supplier<T> supplier);

	boolean isPresent();

	boolean isEmpty();

	T get();

	T orElse(T object);

	void ifPresent(Consumer<T> consumer);

	void ifEmpty(Runnable runnable);
}
