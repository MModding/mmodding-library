package com.mmodding.library.core.impl.management.content;

import com.mmodding.library.core.api.management.content.ForBeing;

import java.util.function.Consumer;
import java.util.function.Supplier;

public class ForBeingVacantImpl<T> implements ForBeing.Vacant<T> {

	private T object = null;

	@Override
	public void initialize(Supplier<T> initializer) {
		this.object = initializer.get();
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
	public T orElse(T t) {
		return this.isInitialized() ? this.object : t;
	}

	@Override
	public void execute(Consumer<T> action) {
		if (this.isInitialized()) {
			action.accept(this.object);
		}
		else {
			throw new IllegalStateException("Tried to execute an action without the object being initialized");
		}
	}
}
