package com.mmodding.library.datagen.api.lang;

import com.mmodding.library.datagen.impl.lang.TranslationSupportImpl;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import org.jetbrains.annotations.ApiStatus;

import java.util.function.BiConsumer;

public class TranslationSupport {

	/**
	 * Adds a new {@link TranslationSupport} which allows a custom type of content (stored inside a {@link Registry})
	 * to be mapped to a string translation key.
	 * @param registry the registry
	 * @param maker the {@link BiConsumer} providing the {@link TranslationCallback} that will apply the
	 * {@link LangProcessor} of the current element
	 */
	public static <R extends Registry<T>, T extends LangContainer> void addTranslationSupport(RegistryKey<R> registry, BiConsumer<TranslationCallback, T> maker) {
		TranslationSupportImpl.addTranslationSupport(registry, maker);
	}

	@ApiStatus.NonExtendable
	public interface TranslationCallback {

		void apply(String key);
	}
}
