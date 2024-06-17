package com.mmodding.library.datagen.impl;

import com.mmodding.library.datagen.api.lang.LangContainer;
import org.jetbrains.annotations.ApiStatus;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.List;

@ApiStatus.Internal
public record DataContainers(List<LangContainer> langContainers) {

	public static DataContainers retrieveFrom(Class<?> clazz) {
		List<LangContainer> langContainers = new ArrayList<>();
		for (Field field : clazz.getDeclaredFields()) {
			if (Modifier.isStatic(field.getModifiers())) {
				try {
					Object object = field.get(null);
					if (object instanceof LangContainer container) {
						langContainers.add(container);
					}
				} catch (IllegalAccessException error) {
					throw new RuntimeException(error);
				}
			}
		}
		return new DataContainers(langContainers);
	}
}
