package com.mmodding.mmodding_lib.library.utils;

/**
 * A utility interface that allows fast access to the real object for mixin classes.
 * @param <T> the real object type
 */
public interface Self<T> {

	@SuppressWarnings("unchecked")
	default T getObject() {
		return (T) this;
	}
}
