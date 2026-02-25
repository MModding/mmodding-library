package com.mmodding.library.datagen.impl;

import com.mmodding.library.datagen.api.loot.block.BlockLootContainer;
import com.mmodding.library.datagen.api.loot.entity.EntityLootContainer;
import com.mmodding.library.datagen.api.recipe.RecipeContainer;
import com.mmodding.library.datagen.api.recipe.RecipeHelper;
import com.mmodding.library.datagen.impl.recipe.RecipeHelperImpl;
import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricBlockLootTableProvider;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider;
import net.fabricmc.fabric.api.datagen.v1.provider.SimpleFabricLootTableProvider;
import net.minecraft.block.Block;
import net.minecraft.data.server.recipe.RecipeJsonProvider;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.item.ItemConvertible;
import net.minecraft.loot.LootTable;
import net.minecraft.loot.context.LootContextTypes;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.ApiStatus;

import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

@ApiStatus.Internal
public class DataProcessor {

	public static DataGeneratorEntrypoint process(DataContainers containers) {
		return fabricDataGenerator -> {
			FabricDataGenerator.Pack pack = fabricDataGenerator.createPack();
			pack.addProvider((output, future) -> new AutomatedRecipeProvider(containers.recipeContainers(), output));
			pack.addProvider((output, future) -> new AutomatedBlockLootProvider(containers.blockLootContainers(), output));
			pack.addProvider((output, future) -> new AutomatedEntityLootProvider(containers.entityLootContainers(), output));
		};
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
