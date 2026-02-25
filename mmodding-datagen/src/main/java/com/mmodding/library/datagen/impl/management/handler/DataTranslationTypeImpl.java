package com.mmodding.library.datagen.impl.management.handler;

import com.mmodding.library.datagen.api.lang.TranslationSupport;
import com.mmodding.library.datagen.api.management.handler.DataContentType;
import com.mmodding.library.datagen.api.management.processor.ContentProcessor;
import com.mmodding.library.datagen.impl.lang.TranslationSupportImpl;
import com.mmodding.library.java.api.list.BiList;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricLanguageProvider;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;

import java.util.List;
import java.util.Optional;

public class DataTranslationTypeImpl<T> implements DataContentType<T, String> {

	private final RegistryKey<? extends Registry<T>> registry;

	public DataTranslationTypeImpl(RegistryKey<? extends Registry<T>> registry) {
		this.registry = registry;
	}

	@Override
	public void handleContent(BiList<List<T>, ContentProcessor<T, String>> contentToProcess, FabricDataGenerator.Pack pack) {
		pack.addProvider((output, future) -> new AutomatedLanguageProvider<>(this.registry, contentToProcess, output));
	}

	private static class AutomatedLanguageProvider<T> extends FabricLanguageProvider {

		private final RegistryKey<? extends Registry<T>> registry;
		private final BiList<List<T>, ContentProcessor<T, String>> contentToProcess;

		protected AutomatedLanguageProvider(RegistryKey<? extends Registry<T>> registry, BiList<List<T>, ContentProcessor<T, String>> contentToProcess, FabricDataOutput output) {
			super(output);
			this.registry = registry;
			this.contentToProcess = contentToProcess;
		}

		@Override
		@SuppressWarnings({"unchecked", "rawtypes"})
		public void generateTranslations(TranslationBuilder translationBuilder) {
			this.contentToProcess.forEach((elements, processor) -> {
				for (T element : elements) {
					if (TranslationSupportImpl.REGISTRY.containsKey(this.registry)) {
						Registry<T> registry = (Registry<T>) Registries.REGISTRIES.get((RegistryKey) this.registry);
						assert registry != null;
						Optional<RegistryKey<T>> optional = registry.getKey(element);
						optional.ifPresentOrElse(
							key -> {
								TranslationSupport.TranslationCallback callback = translation -> translationBuilder.add(
									translation,
									processor.process(key)
								);
								TranslationSupportImpl.REGISTRY.get(this.registry).accept(callback, element);
							},
							() -> { throw new IllegalStateException(element + " does not exist in " + this.registry + "!"); }
						);

					}
					else {
						throw new IllegalStateException(this.registry + " is not a valid translation support type!");
					}
				}
			});
		}
	}

	static {
		TranslationSupport.addTranslationSupport(RegistryKeys.ITEM, (callback, object) -> callback.apply(object.getTranslationKey()));
		TranslationSupport.addTranslationSupport(RegistryKeys.BLOCK, (callback, object) -> callback.apply(object.getTranslationKey()));
		TranslationSupport.addTranslationSupport(RegistryKeys.ENTITY_TYPE, (callback, object) -> callback.apply(object.getTranslationKey()));
		TranslationSupport.addTranslationSupport(RegistryKeys.ENCHANTMENT, (callback, object) -> callback.apply(object.getTranslationKey()));
		TranslationSupport.addTranslationSupport(RegistryKeys.ATTRIBUTE, (callback, object) -> callback.apply(object.getTranslationKey()));
		TranslationSupport.addTranslationSupport(RegistryKeys.STAT_TYPE, (callback, object) -> callback.apply(object.getTranslationKey()));
	}
}
