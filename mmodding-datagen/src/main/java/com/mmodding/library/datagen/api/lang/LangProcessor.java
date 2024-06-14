package com.mmodding.library.datagen.api.lang;

import com.mmodding.library.datagen.impl.lang.LangProcessorImpl;
import net.minecraft.registry.RegistryKey;

@FunctionalInterface
public interface LangProcessor<T> {

	static <T> LangProcessor<T> standard() {
		return new LangProcessorImpl<>();
	}

	String process(RegistryKey<T> key);
}
