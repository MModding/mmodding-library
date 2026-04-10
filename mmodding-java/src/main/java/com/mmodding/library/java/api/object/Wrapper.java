package com.mmodding.library.java.api.object;

import com.mmodding.library.java.api.function.AutoMapper;

/**
 * Represents a wrapper of a {@link T} object;
 */
public interface Wrapper<T> {

	void configure(AutoMapper<T> configuration);
}
