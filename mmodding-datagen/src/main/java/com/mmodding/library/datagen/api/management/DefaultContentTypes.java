package com.mmodding.library.datagen.api.management;

import com.mmodding.library.datagen.api.family.BlockFamilyProcessor;
import com.mmodding.library.datagen.api.lang.TranslationProcessor;
import com.mmodding.library.datagen.api.loot.block.BlockLootProcessor;
import com.mmodding.library.datagen.api.loot.entity.EntityLootProcessor;
import com.mmodding.library.datagen.api.model.block.BlockStateModelProcessor;
import com.mmodding.library.datagen.api.model.item.ItemModelProcessor;
import com.mmodding.library.datagen.api.recipe.RecipeProcessor;
import com.mmodding.library.datagen.api.tag.TagProcessor;
import com.mmodding.library.datagen.impl.management.handler.*;
import net.minecraft.core.Registry;
import net.minecraft.data.BlockFamily;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Block;

public class DefaultContentTypes {

	public static final DataContentType<Item, ItemModelProcessor> ITEM_MODELS = new ItemModelTypeImpl();

	public static final DataContentType<Block, BlockStateModelProcessor> BLOCK_MODELS = new BlockModelTypeImpl();

	public static final DataContentType<Block, BlockLootProcessor> BLOCK_LOOTS = new BlockLootTypeImpl();

	public static final DataContentType<EntityType<?>, EntityLootProcessor> ENTITY_LOOTS = new EntityLootTypeImpl();

	public static final DataContentType<ItemLike, RecipeProcessor<ItemLike>> ITEM_CONVERTIBLE_RECIPES = new RecipeTypeImpl<>();

	public static final DataContentType<BlockFamily, BlockFamilyProcessor> BLOCK_FAMILIES = new BlockFamilyTypeImpl();

	public static <T> DataContentType<T, TagProcessor<T>> getTagHandler(ResourceKey<? extends Registry<T>> registry) {
		return new TagTypeImpl<>(registry);
	}

	public static <T> DataContentType<T, TranslationProcessor<T>> getTranslationHandler(ResourceKey<? extends Registry<T>> registry) {
		return new TranslationTypeImpl<>(registry);
	}
}
