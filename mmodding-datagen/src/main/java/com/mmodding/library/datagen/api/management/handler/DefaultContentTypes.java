package com.mmodding.library.datagen.api.management.handler;

import com.mmodding.library.datagen.impl.management.handler.DataTranslationTypeImpl;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;

public class DefaultContentTypes {

	public static <T> DataContentType<T, String> getTranslationHandler(RegistryKey<? extends Registry<T>> registry) {
		return new DataTranslationTypeImpl<>(registry);
	}
}
