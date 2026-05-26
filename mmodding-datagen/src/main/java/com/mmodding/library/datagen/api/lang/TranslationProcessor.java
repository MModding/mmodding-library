package com.mmodding.library.datagen.api.lang;

import net.minecraft.resources.Identifier;

@FunctionalInterface
public interface TranslationProcessor {

	String process(Identifier element);
}
