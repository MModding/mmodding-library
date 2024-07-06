package com.mmodding.library.datagen.api.lang;

import com.mmodding.library.datagen.impl.lang.TranslationSupportImpl;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import org.jetbrains.annotations.ApiStatus;

import java.util.function.BiConsumer;

public class TranslationSupport {

	public static <R extends Registry<T>, T extends LangContainer> void addTranslationSupport(RegistryKey<R> registry, BiConsumer<TranslationCallback, T> maker) {
		TranslationSupportImpl.addTranslationSupport(registry, maker);
	}

	@ApiStatus.NonExtendable
	public interface TranslationCallback {

		void apply(String key);
	}
}
