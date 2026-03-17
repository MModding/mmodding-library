package com.mmodding.library.datagen.api.management.resolver;

import com.mmodding.library.datagen.impl.management.DataContentResolverImpl;

import java.util.List;

@FunctionalInterface
public interface DataContentResolver<I, O> {

	/**
	 * Registers a {@link DataContentResolver}.
	 * @param from the class to resolve from
	 * @param to the class to resolve to
	 * @param resolver the resolver
	 * @param <I> the type of the class to resolve from
	 * @param <O> the type of the class to resolve to
	 */
	static <I, O> void register(Class<?> from, Class<?> to, DataContentResolver<I, O> resolver) {
		DataContentResolverImpl.register(from, to, resolver);
	}

	/**
	 * Resolves multiple objects from another of a specified type.
	 * @param input the input object
	 * @return the collected outputs
	 */
	List<O> resolve(I input);
}
