package com.mmodding.library.registry.api.content;

import com.mmodding.library.registry.impl.content.ForBeingImpl;

import java.util.function.Consumer;
import java.util.function.Supplier;

public interface ForBeing<T> {

	static <T> ForBeing<T> create() {
		return new ForBeingImpl<>();
	}

	void initialize(Supplier<T> initializer);

	boolean isInitialized();

	T get();

	T orElse(T t);

	void execute(Consumer<T> t);
}
