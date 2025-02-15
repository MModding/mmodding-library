package com.mmodding.library.java.api.object;

/**
 * Represents an object that can be copied using {@link Copyable#copy()}
 */
public interface Copyable<T> {

	T copy();
}
