package com.mmodding.library.datagen.impl;

import com.mmodding.library.datagen.api.lang.LangContainer;
import com.mmodding.library.datagen.api.lang.TranslationSupport;
import com.mmodding.library.datagen.impl.access.LangProcessorAccess;
import com.mmodding.library.datagen.impl.lang.TranslationSupportImpl;
import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricLanguageProvider;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import org.jetbrains.annotations.ApiStatus;

import java.util.List;
import java.util.Optional;

@ApiStatus.Internal
public class DataProcessor {

	public static DataGeneratorEntrypoint process(DataContainers containers) {
		return fabricDataGenerator -> {
			FabricDataGenerator.Pack pack = fabricDataGenerator.createPack();
			pack.addProvider((output, future) -> new AutomatedLanguageProvider(containers.langContainers(), output));
		};
	}

	private static class AutomatedLanguageProvider extends FabricLanguageProvider {

		private final List<LangContainer> langContainers;

		protected AutomatedLanguageProvider(List<LangContainer> langContainers, FabricDataOutput dataOutput) {
			super(dataOutput);
			this.langContainers = langContainers;
		}

		@Override
		@SuppressWarnings("unchecked")
		public void generateTranslations(TranslationBuilder translationBuilder) {
			for (LangContainer container : this.langContainers) {
				if (TranslationSupportImpl.REGISTRY.containsKey(container.registry())) {
					@SuppressWarnings("rawtypes")
					Registry<LangContainer> registry = Registries.REGISTRY.get((RegistryKey) container.registry());
					assert registry != null;
					Optional<RegistryKey<LangContainer>> optional = registry.getKey(container);
					optional.ifPresentOrElse(
						key -> {
							TranslationSupport.TranslationCallback callback = translation -> translationBuilder.add(
								translation,
								LangProcessorAccess.access(container).process(key)
							);
							TranslationSupportImpl.REGISTRY.get(container.registry()).accept(callback, container);
						},
						() -> {
							throw new IllegalStateException(container + " does not exist in " + container.registry() + "!");
						}
					);

				}
				else {
					throw new IllegalStateException(container.registry() + " is not a valid translation support type!");
				}
			}
		}
	}
}
