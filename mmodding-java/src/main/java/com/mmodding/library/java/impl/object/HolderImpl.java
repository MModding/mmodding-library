package com.mmodding.library.java.impl.object;

import com.mmodding.library.java.api.object.Holder;

import java.util.function.Consumer;
import java.util.function.Supplier;

public class HolderImpl<T> implements Holder<T> {

	private T object = null;
	private boolean sealed = false;

	@Override
	public void assign(Supplier<T> supplier) {
		if (!this.sealed) {
			this.object = supplier.get();
			this.sealed = true;
		}
		else {
			throw new IllegalStateException("Tried to assign a value to sealed Holder");
		}
	}

	@Override
	public boolean isPresent() {
		return this.object != null;
	}

	@Override
	public boolean isEmpty() {
		return this.object == null;
	}

	@Override
	public T get() {
		return this.object;
	}

	@Override
	public T orElse(T object) {
		return this.object != null ? this.object : object;
	}

	@Override
	public void ifPresent(Consumer<T> consumer) {
		if (this.isPresent()) {
			consumer.accept(this.object);
		}
	}

	@Override
	public void ifEmpty(Runnable runnable) {
		if (this.isEmpty()) {
			runnable.run();
		}
	}
}
