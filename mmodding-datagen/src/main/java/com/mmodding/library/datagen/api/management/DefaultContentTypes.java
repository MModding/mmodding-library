package com.mmodding.library.datagen.api.management;

import com.mmodding.library.datagen.api.family.BlockFamilyProcessor;
import com.mmodding.library.datagen.api.lang.TranslationProcessor;
import com.mmodding.library.datagen.api.loot.block.BlockLootProcessor;
import com.mmodding.library.datagen.api.loot.entity.EntityLootProcessor;
import com.mmodding.library.datagen.api.model.block.BlockStateModelProcessor;
import com.mmodding.library.datagen.api.model.item.ItemModelProcessor;
import com.mmodding.library.datagen.api.recipe.RecipeProcessor;
import com.mmodding.library.datagen.impl.management.handler.*;
import net.minecraft.block.Block;
import net.minecraft.data.family.BlockFamily;
import net.minecraft.entity.EntityType;
import net.minecraft.item.Item;
import net.minecraft.item.ItemConvertible;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;

public class DefaultContentTypes {

	public static final DataContentType<Item, ItemModelProcessor> ITEM_MODELS = new ItemModelTypeImpl();

	public static final DataContentType<Block, BlockStateModelProcessor> BLOCK_MODELS = new BlockModelTypeImpl();

	public static final DataContentType<Block, BlockLootProcessor> BLOCK_LOOTS = new BlockLootTypeImpl();

	public static final DataContentType<EntityType<?>, EntityLootProcessor> ENTITY_LOOTS = new EntityLootTypeImpl();

	public static final DataContentType<ItemConvertible, RecipeProcessor<ItemConvertible>> ITEM_CONVERTIBLE_RECIPES = new RecipeTypeImpl<>();

	public static final DataContentType<BlockFamily, BlockFamilyProcessor> BLOCK_FAMILIES = new BlockFamilyTypeImpl();

	public static <T> DataContentType<T, TranslationProcessor<T>> getTranslationHandler(RegistryKey<? extends Registry<T>> registry) {
		return new TranslationTypeImpl<>(registry);
	}
}
