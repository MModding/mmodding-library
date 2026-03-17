package com.mmodding.library.datagen.api.lang;

import net.minecraft.registry.RegistryKey;

public interface TranslationProcessor<T> {

	String process(RegistryKey<T> element);
}
