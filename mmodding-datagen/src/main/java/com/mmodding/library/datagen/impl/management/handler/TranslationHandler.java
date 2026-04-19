package com.mmodding.library.datagen.impl.management.handler;

import com.mmodding.library.datagen.api.lang.TranslationSupport;
import com.mmodding.library.datagen.api.management.handler.DataProcessHandler;
import com.mmodding.library.datagen.api.lang.TranslationProcessor;
import com.mmodding.library.datagen.api.provider.MModdingLanguageProvider;
import com.mmodding.library.datagen.impl.lang.TranslationSupportImpl;
import com.mmodding.library.java.api.list.BiList;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.fabricmc.fabric.api.datagen.v1.FabricPackOutput;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import org.jetbrains.annotations.ApiStatus;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@ApiStatus.Internal
public class TranslationHandler<T> implements DataProcessHandler<T, TranslationProcessor> {

	private final ResourceKey<? extends Registry<T>> registry;
	private final Class<T> type;

	@Override
	public Class<T> getType() {
		return this.type;
	}

	public TranslationHandler(ResourceKey<? extends Registry<T>> registry, Class<T> type) {
		this.registry = registry;
		this.type = type;
	}

	@Override
	public void handleContent(FabricDataGenerator.Pack pack, BiList<TranslationProcessor, List<T>> contentToProcess) {
		pack.addProvider((output, future) -> new AutomatedLanguageProvider<>(this.registry, contentToProcess, output, future));
	}

	private static class AutomatedLanguageProvider<T> extends MModdingLanguageProvider {

		private final ResourceKey<? extends Registry<T>> registry;
		private final BiList<TranslationProcessor, List<T>> contentToProcess;

		protected AutomatedLanguageProvider(ResourceKey<? extends Registry<T>> registry, BiList<TranslationProcessor, List<T>> contentToProcess, FabricPackOutput output, CompletableFuture<HolderLookup.Provider> future) {
			super(output, future);
			this.registry = registry;
			this.contentToProcess = contentToProcess;
		}

		@Override
		@SuppressWarnings({"unchecked", "rawtypes"})
		public void generateTranslations(HolderLookup.Provider registryLookup, TranslationBuilder translationBuilder) {
			this.contentToProcess.forEach((processor, elements) -> {
				for (T element : elements) {
					if (TranslationSupportImpl.REGISTRY.containsKey(this.registry)) {
						Registry<T> registry = (Registry<T>) BuiltInRegistries.REGISTRY.getValueOrThrow((ResourceKey) this.registry);
						Identifier identifier = registry.getKey(element);
						if (identifier != null) {
							TranslationSupport.TranslationCallback callback = translation -> translationBuilder.add(translation, processor.process(identifier));
							TranslationSupportImpl.REGISTRY.get(this.registry).accept(callback, element);
						}
						else {
							throw new IllegalStateException(element + " does not exist in " + this.registry + "!");
						}
					}
					else {
						throw new IllegalStateException(this.registry + " is not a valid translation support type!");
					}
				}
			});
		}

		@Override
		public String getName() {
			return "Automated " + this.registry.identifier() + " " + super.getName();
		}
	}

	static {
		TranslationSupport.addTranslationSupport(Registries.ITEM, (callback, object) -> callback.apply(object.getDescriptionId()));
		TranslationSupport.addTranslationSupport(Registries.BLOCK, (callback, object) -> callback.apply(object.getDescriptionId()));
		TranslationSupport.addTranslationSupport(Registries.ENTITY_TYPE, (callback, object) -> callback.apply(object.getDescriptionId()));
		TranslationSupport.addTranslationSupport(Registries.ATTRIBUTE, (callback, object) -> callback.apply(object.getDescriptionId()));
	}
}
