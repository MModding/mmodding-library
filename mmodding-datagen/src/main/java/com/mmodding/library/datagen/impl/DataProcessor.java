package com.mmodding.library.datagen.impl;

import com.mmodding.library.datagen.api.lang.LangContainer;
import com.mmodding.library.datagen.api.lang.TranslationSupport;
import com.mmodding.library.datagen.api.loot.block.BlockLootContainer;
import com.mmodding.library.datagen.api.loot.entity.EntityLootContainer;
import com.mmodding.library.datagen.api.recipe.RecipeContainer;
import com.mmodding.library.datagen.api.recipe.RecipeHelper;
import com.mmodding.library.datagen.impl.lang.TranslationSupportImpl;
import com.mmodding.library.datagen.impl.recipe.RecipeHelperImpl;
import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricBlockLootTableProvider;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricLanguageProvider;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider;
import net.fabricmc.fabric.api.datagen.v1.provider.SimpleFabricLootTableProvider;
import net.minecraft.block.Block;
import net.minecraft.data.server.recipe.RecipeJsonProvider;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.item.ItemConvertible;
import net.minecraft.loot.LootTable;
import net.minecraft.loot.context.LootContextTypes;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.ApiStatus;

import java.util.List;
import java.util.Optional;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

@ApiStatus.Internal
public class DataProcessor {

	public static DataGeneratorEntrypoint process(DataContainers containers) {
		return fabricDataGenerator -> {
			FabricDataGenerator.Pack pack = fabricDataGenerator.createPack();
			pack.addProvider((output, future) -> new AutomatedLanguageProvider(containers.langContainers(), output));
			pack.addProvider((output, future) -> new AutomatedRecipeProvider(containers.recipeContainers(), output));
			pack.addProvider((output, future) -> new AutomatedBlockLootProvider(containers.blockLootContainers(), output));
			pack.addProvider((output, future) -> new AutomatedEntityLootProvider(containers.entityLootContainers(), output));
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
				if (TranslationSupportImpl.REGISTRY.containsKey(container.langRegistry())) {
					@SuppressWarnings("rawtypes")
					Registry<LangContainer> registry = Registries.REGISTRIES.get((RegistryKey) container.langRegistry());
					assert registry != null;
					Optional<RegistryKey<LangContainer>> optional = registry.getKey(container);
					optional.ifPresentOrElse(
						key -> {
							TranslationSupport.TranslationCallback callback = translation -> translationBuilder.add(
								translation,
								InternalDataAccess.langProcessor(container).process(key)
							);
							TranslationSupportImpl.REGISTRY.get(container.langRegistry()).accept(callback, container);
						},
						() -> {
							throw new IllegalStateException(container + " does not exist in " + container.langRegistry() + "!");
						}
					);

				}
				else {
					throw new IllegalStateException(container.langRegistry() + " is not a valid translation support type!");
				}
			}
		}
	}

	private static class AutomatedRecipeProvider extends FabricRecipeProvider {

		private final List<RecipeContainer> recipeContainers;

		protected AutomatedRecipeProvider(List<RecipeContainer> recipeContainers, FabricDataOutput dataOutput) {
			super(dataOutput);
			this.recipeContainers = recipeContainers;
		}

		@Override
		public void generate(Consumer<RecipeJsonProvider> exporter) {
			for (RecipeContainer container : this.recipeContainers) {
				Consumer<RecipeHelper> recipeGenerator = InternalDataAccess.recipeGenerator(container);
				RecipeHelperImpl helper = new RecipeHelperImpl((ItemConvertible) container);
				recipeGenerator.accept(helper);
				helper.getFactories().forEach(supplier -> supplier.get().offerTo(exporter));
			}
		}
	}

	private static class AutomatedBlockLootProvider extends FabricBlockLootTableProvider {

		private final List<BlockLootContainer> blockLootContainers;

		protected AutomatedBlockLootProvider(List<BlockLootContainer> blockLootContainers, FabricDataOutput dataOutput) {
			super(dataOutput);
			this.blockLootContainers = blockLootContainers;
		}

		@Override
		public void generate() {
			for (BlockLootContainer container : this.blockLootContainers) {
				InternalDataAccess.blockLootProcessor(container).process((Block) container, this);
			}
		}
	}

	private static class AutomatedEntityLootProvider extends SimpleFabricLootTableProvider {

		private final List<EntityLootContainer> entityLootContainers;

		protected AutomatedEntityLootProvider(List<EntityLootContainer> entityLootContainers, FabricDataOutput dataOutput) {
			super(dataOutput, LootContextTypes.ENTITY);
			this.entityLootContainers = entityLootContainers;
		}

		@Override
		@SuppressWarnings("unchecked")
		public void accept(BiConsumer<Identifier, LootTable.Builder> biConsumer) {
			for (EntityLootContainer container : this.entityLootContainers) {
				biConsumer.accept(
					((EntityType<?>) container).getLootTableId(),
					InternalDataAccess.entityLootProcessor(container).process((EntityType<Entity>) container)
				);
			}
		}
	}
}
