package com.mmodding.library.datagen.impl.management;

import com.mmodding.library.datagen.api.management.resolver.DataContentResolver;
import com.mmodding.library.java.api.list.BiList;
import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap;

import java.util.Map;

public class DataContentResolverImpl {

	public static final Map<Class<?>, BiList<Class<?>, DataContentResolver<?, ?>>> REGISTRY = new Object2ObjectOpenHashMap<>();

	public static boolean linkExists(Class<?> from, Class<?> to) {
		return REGISTRY.containsKey(from) && REGISTRY.get(from).stream().anyMatch(p -> to.isAssignableFrom(p.first()));
	}

	public static DataContentResolver<?, ?> resolver(Class<?> from, Class<?> to) {
		return REGISTRY.get(from).stream().filter(p -> to.isAssignableFrom(p.first())).findFirst().orElseThrow().second();
	}

	public static <I, O> void register(Class<?> from, Class<?> to, DataContentResolver<I, O> resolver) {
		DataContentResolverImpl.REGISTRY.computeIfAbsent(from, ignored -> BiList.create()).add(to, resolver);
	}
}
