package com.mmodding.library.datagen.impl.management;

import com.mmodding.library.datagen.api.management.resolver.DataContentResolver;
import com.mmodding.library.java.api.map.BiMap;

public class DataContentResolverImpl {

	public static final BiMap<Class<?>, Class<?>, DataContentResolver<?, ?>> REGISTRY = BiMap.create();

	public static <I, O> void register(Class<?> from, Class<?> to, DataContentResolver<I, O> resolver) {
		DataContentResolverImpl.REGISTRY.put(from, to, resolver);
	}
}
