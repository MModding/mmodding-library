package com.mmodding.library.datagen.api.lang;

import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;

public interface LangContainer {

	default <T> T lang(LangProcessor<T> processor) {
		throw new IllegalStateException();
	}

	default RegistryKey<? extends Registry<?>> registry() {
		throw new IllegalStateException();
	}
}
