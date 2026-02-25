package com.mmodding.library.datagen.impl.management.handler;


import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.List;

public class ContentHandlerImpl {

	@SuppressWarnings("unchecked")
	private static <T> T getObject(Field field) {
		try {
			return (T) field.get(null);
		}
		catch (IllegalAccessException error) {
			throw new RuntimeException(error);
		}
	}

	public static <T> List<T> extractOfType(Class<?> providerClass, Class<? extends T> type) {
		return Arrays.stream(providerClass.getDeclaredFields())
			.filter(field -> Modifier.isStatic(field.getModifiers()) && field.getType().equals(type))
			.map(ContentHandlerImpl::<T>getObject)
			.toList();
	}
}
