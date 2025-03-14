package com.mmodding.library.datagen.impl.lang;

import com.mmodding.library.datagen.api.lang.LangContainer;
import com.mmodding.library.datagen.api.lang.TranslationSupport;
import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;

import java.util.Map;
import java.util.function.BiConsumer;

public class TranslationSupportImpl {

	public static final Map<RegistryKey<? extends Registry<?>>, BiConsumer<TranslationSupport.TranslationCallback, ? super LangContainer>> REGISTRY = new Object2ObjectOpenHashMap<>();

	@SuppressWarnings("unchecked")
	public static <R extends Registry<T>, T extends LangContainer> void addTranslationSupport(RegistryKey<R> registry, BiConsumer<TranslationSupport.TranslationCallback, T> maker) {
		TranslationSupportImpl.REGISTRY.put(registry, (BiConsumer<TranslationSupport.TranslationCallback, ? super LangContainer>) (BiConsumer<?, ?>) maker);
	}

	public static void defaultTranslationSupports() {
		TranslationSupport.addTranslationSupport(RegistryKeys.ITEM, (callback, object) -> callback.apply(object.getTranslationKey()));
		TranslationSupport.addTranslationSupport(RegistryKeys.BLOCK, (callback, object) -> callback.apply(object.getTranslationKey()));
		TranslationSupport.addTranslationSupport(RegistryKeys.ENTITY_TYPE, (callback, object) -> callback.apply(object.getTranslationKey()));
		TranslationSupport.addTranslationSupport(RegistryKeys.ENCHANTMENT, (callback, object) -> callback.apply(object.getTranslationKey()));
		TranslationSupport.addTranslationSupport(RegistryKeys.ATTRIBUTE, (callback, object) -> callback.apply(object.getTranslationKey()));
		TranslationSupport.addTranslationSupport(RegistryKeys.STAT_TYPE, (callback, object) -> callback.apply(object.getTranslationKey()));
	}
}
