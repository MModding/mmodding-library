package com.mmodding.library.datagen.impl.management;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.List;

public class ClassContentOperator {

	public static List<?> extract(Class<?> sourceClass) {
		return Arrays.stream(sourceClass.getDeclaredFields())
			.filter(field -> Modifier.isStatic(field.getModifiers()))
			.map(ClassContentOperator::getObject)
			.toList();
	}

	private static Object getObject(Field field) {
		try {
			return field.get(null);
		}
		catch (IllegalAccessException error) {
			throw new RuntimeException(error);
		}
	}
}
