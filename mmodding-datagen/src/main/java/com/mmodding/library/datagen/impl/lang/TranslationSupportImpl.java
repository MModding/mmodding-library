package com.mmodding.library.datagen.impl.lang;

import com.mmodding.library.datagen.api.lang.TranslationSupport;
import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;

import java.util.Map;
import java.util.function.BiConsumer;

public class TranslationSupportImpl {

	public static final Map<RegistryKey<? extends Registry<?>>, BiConsumer<TranslationSupport.TranslationCallback, Object>> REGISTRY = new Object2ObjectOpenHashMap<>();

	@SuppressWarnings({"unchecked", "rawtypes"})
	public static <T> void addTranslationSupport(RegistryKey<? extends Registry<T>> registry, BiConsumer<TranslationSupport.TranslationCallback, T> maker) {
		TranslationSupportImpl.REGISTRY.put(registry, (BiConsumer) maker);
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
