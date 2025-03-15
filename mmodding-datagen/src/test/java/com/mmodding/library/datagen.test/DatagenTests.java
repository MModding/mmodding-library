package com.mmodding.library.datagen.test;

import com.mmodding.library.core.api.container.AdvancedContainer;
import com.mmodding.library.core.api.management.ElementsManager;
import com.mmodding.library.core.api.management.initializer.ExtendedModInitializer;
import com.mmodding.library.datagen.api.lang.LangProcessor;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.Block;
import net.minecraft.block.MapColor;
import net.minecraft.item.Items;
import net.minecraft.recipe.book.RecipeCategory;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class DatagenTests implements ExtendedModInitializer {

	public static final Block BLOCK = new Block(FabricBlockSettings.of().mapColor(MapColor.BLACK))
		.<Block>lang(LangProcessor.standard())
		.recipe(helper -> helper.shapeless(RecipeCategory.BUILDING_BLOCKS, recipe -> recipe.with(Items.DIAMOND)));

	@Override
	public void setupManager(ElementsManager.Builder manager) {
		manager.content(DatagenTests::register);
	}

	@Override
	public void onInitialize(AdvancedContainer mod) {}

	private static void register(AdvancedContainer advancedContainer) {
		Registry.register(Registries.BLOCK, new Identifier("mmodding_datagen", "block"), BLOCK);
	}
}
