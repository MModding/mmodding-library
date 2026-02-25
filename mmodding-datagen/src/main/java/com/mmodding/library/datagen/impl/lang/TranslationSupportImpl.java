package com.mmodding.library.datagen.impl.lang;

import com.mmodding.library.datagen.api.lang.TranslationSupport;
import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;

import java.util.Map;
import java.util.function.BiConsumer;

public class TranslationSupportImpl {

	public static final Map<RegistryKey<? extends Registry<?>>, BiConsumer<TranslationSupport.TranslationCallback, Object>> REGISTRY = new Object2ObjectOpenHashMap<>();

	@SuppressWarnings({"unchecked", "rawtypes"})
	public static <T> void addTranslationSupport(RegistryKey<? extends Registry<T>> registry, BiConsumer<TranslationSupport.TranslationCallback, T> maker) {
		TranslationSupportImpl.REGISTRY.put(registry, (BiConsumer) maker);
	}
}
