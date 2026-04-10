package com.mmodding.library.java.api.object;

import com.mmodding.library.java.api.function.AutoMapper;

/**
 * Represents a wrapper of type {@link W}, wrapping an object of type {@link T};
 */
public interface Wrapper<W extends Wrapper<W, T>, T> {

	W configure(AutoMapper<T> configuration);
}
