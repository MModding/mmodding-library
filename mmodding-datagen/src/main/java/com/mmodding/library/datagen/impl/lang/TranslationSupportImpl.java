package com.mmodding.library.datagen.impl.lang;

import com.mmodding.library.datagen.api.lang.TranslationSupport;
import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap;
import java.util.Map;
import java.util.function.BiConsumer;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;

public class TranslationSupportImpl {

	public static final Map<ResourceKey<? extends Registry<?>>, BiConsumer<TranslationSupport.TranslationCallback, Object>> REGISTRY = new Object2ObjectOpenHashMap<>();

	@SuppressWarnings({"unchecked", "rawtypes"})
	public static <T> void addTranslationSupport(ResourceKey<? extends Registry<T>> registry, BiConsumer<TranslationSupport.TranslationCallback, T> maker) {
		TranslationSupportImpl.REGISTRY.put(registry, (BiConsumer) maker);
	}
}
