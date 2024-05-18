package com.mmodding.library.java.api.object;

/**
 * Represents an object having an opposite, that can be retrieved using Opposable#getOpposite.
 * @param <T> the current instance and opposite class type
 */
public interface Opposable<T> {

	T getOpposite();
}
