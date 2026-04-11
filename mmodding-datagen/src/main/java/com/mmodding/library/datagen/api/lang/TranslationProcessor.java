package com.mmodding.library.datagen.api.lang;

import net.minecraft.resources.ResourceKey;

public interface TranslationProcessor<T> {

	String process(ResourceKey<T> element);
}
