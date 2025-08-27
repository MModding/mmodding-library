package com.mmodding.library.java.impl.object;

import com.mmodding.library.java.api.object.Lazy;

import java.util.function.Consumer;
import java.util.function.Supplier;

public class LazyImpl<T> implements Lazy<T> {

	private final Supplier<T> initializer;

	private T object = null;
	private boolean sealed = false;

	public LazyImpl(Supplier<T> initializer) {
		this.initializer = initializer;
	}

	@Override
	public void initialize() {
		if (!this.sealed) {
			this.object = this.initializer.get();
			this.sealed = true;
		}
		else {
			throw new IllegalStateException("Tried to assign a value to sealed Lazy");
		}
	}

	@Override
	public boolean isInitialized() {
		return this.object != null;
	}

	@Override
	public T get() {
		return this.object;
	}

	@Override
	public T orElse(T object) {
		return this.isInitialized() ? this.object : object;
	}

	@Override
	public void ifInitialized(Consumer<T> consumer) {
		if (this.isInitialized()) {
			consumer.accept(this.object);
		}
	}
}
