package com.mmodding.library.datagen.api.management;

import com.mmodding.library.datagen.api.lang.TranslationProcessor;
import com.mmodding.library.datagen.api.loot.block.BlockLootProcessor;
import com.mmodding.library.datagen.api.loot.entity.EntityLootProcessor;
import com.mmodding.library.datagen.api.management.handler.DataProcessHandler;
import com.mmodding.library.datagen.api.management.handler.FinalDataHandler;
import com.mmodding.library.datagen.api.model.block.BlockModelProcessor;
import com.mmodding.library.datagen.api.model.item.ItemModelProcessor;
import com.mmodding.library.datagen.api.recipe.RecipeProcessor;
import com.mmodding.library.datagen.api.tag.ValueTagProcessor;
import com.mmodding.library.datagen.api.tag.KeyTagProcessor;
import com.mmodding.library.datagen.impl.management.handler.*;
import net.minecraft.core.Registry;
import net.minecraft.data.BlockFamily;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.material.Fluid;

public class DefaultDataHandlers {

	public static final DataProcessHandler<Item, ItemModelProcessor> ITEM_MODELS = new ItemModelHandler();

	public static final DataProcessHandler<Block, BlockModelProcessor> BLOCK_MODELS = new BlockModelHandler();

	public static final DataProcessHandler<Block, BlockLootProcessor> BLOCK_LOOTS = new BlockLootHandler();

	public static final DataProcessHandler<EntityType<?>, EntityLootProcessor> ENTITY_LOOTS = new EntityLootHandler();

	public static final DataProcessHandler<ItemLike, RecipeProcessor<ItemLike>> ITEM_CONVERTIBLE_RECIPES = new RecipeHandler<>();

	public static final DataProcessHandler<Block, ValueTagProcessor<Block>> BLOCK_TAGS = new ValueTagHandler<>(ValueTagHandler.SupportedElement.BLOCK);
	public static final DataProcessHandler<Item, ValueTagProcessor<Item>> ITEM_TAGS = new ValueTagHandler<>(ValueTagHandler.SupportedElement.ITEM);
	public static final DataProcessHandler<Fluid, ValueTagProcessor<Fluid>> FLUID_TAGS = new ValueTagHandler<>(ValueTagHandler.SupportedElement.FLUID);
	public static final DataProcessHandler<EntityType<?>, ValueTagProcessor<EntityType<?>>> ENTITY_TYPE_TAGS = new ValueTagHandler<>(ValueTagHandler.SupportedElement.ENTITY_TYPE);

	public static <T> DataProcessHandler<T, KeyTagProcessor<T>> getTagHandler(ResourceKey<? extends Registry<T>> registry, Class<T> type) {
		return new KeyTagHandler<>(registry, type);
	}

	public static <T> DataProcessHandler<T, TranslationProcessor<T>> getTranslationHandler(ResourceKey<? extends Registry<T>> registry, Class<T> type) {
		return new TranslationHandler<>(registry, type);
	}

	public static final FinalDataHandler<BlockFamily> BLOCK_FAMILIES = new BlockFamilyFinalDataHandler();
}
