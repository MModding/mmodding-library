package com.mmodding.library.datagen.impl.management.handler;

import com.mmodding.library.datagen.api.lang.TranslationSupport;
import com.mmodding.library.datagen.api.management.DataContentType;
import com.mmodding.library.datagen.api.lang.TranslationProcessor;
import com.mmodding.library.datagen.api.provider.MModdingLanguageProvider;
import com.mmodding.library.datagen.impl.lang.TranslationSupportImpl;
import com.mmodding.library.java.api.list.BiList;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import java.util.List;
import java.util.Optional;

public class TranslationTypeImpl<T> implements DataContentType<T, TranslationProcessor<T>> {

	private final ResourceKey<? extends Registry<T>> registry;

	public TranslationTypeImpl(ResourceKey<? extends Registry<T>> registry) {
		this.registry = registry;
	}

	@Override
	public void handleContent(FabricDataGenerator.Pack pack, BiList<TranslationProcessor<T>, List<T>> contentToProcess) {
		pack.addProvider((output, future) -> new AutomatedLanguageProvider<>(this.registry, contentToProcess, output));
	}

	private static class AutomatedLanguageProvider<T> extends MModdingLanguageProvider {

		private final ResourceKey<? extends Registry<T>> registry;
		private final BiList<TranslationProcessor<T>, List<T>> contentToProcess;

		protected AutomatedLanguageProvider(ResourceKey<? extends Registry<T>> registry, BiList<TranslationProcessor<T>, List<T>> contentToProcess, FabricDataOutput output) {
			super(output);
			this.registry = registry;
			this.contentToProcess = contentToProcess;
		}

		@Override
		@SuppressWarnings({"unchecked", "rawtypes"})
		public void generateTranslations(TranslationBuilder translationBuilder) {
			this.contentToProcess.forEach((processor, elements) -> {
				for (T element : elements) {
					if (TranslationSupportImpl.REGISTRY.containsKey(this.registry)) {
						Registry<T> registry = (Registry<T>) BuiltInRegistries.REGISTRY.get((ResourceKey) this.registry);
						assert registry != null;
						Optional<ResourceKey<T>> optional = registry.getResourceKey(element);
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

		@Override
		public String getName() {
			return "Automated " + this.registry.location() + " " + super.getName();
		}
	}

	static {
		TranslationSupport.addTranslationSupport(Registries.ITEM, (callback, object) -> callback.apply(object.getDescriptionId()));
		TranslationSupport.addTranslationSupport(Registries.BLOCK, (callback, object) -> callback.apply(object.getDescriptionId()));
		TranslationSupport.addTranslationSupport(Registries.ENTITY_TYPE, (callback, object) -> callback.apply(object.getDescriptionId()));
		TranslationSupport.addTranslationSupport(Registries.ENCHANTMENT, (callback, object) -> callback.apply(object.getDescriptionId()));
		TranslationSupport.addTranslationSupport(Registries.ATTRIBUTE, (callback, object) -> callback.apply(object.getDescriptionId()));
		TranslationSupport.addTranslationSupport(Registries.STAT_TYPE, (callback, object) -> callback.apply(object.getTranslationKey()));
	}
}
