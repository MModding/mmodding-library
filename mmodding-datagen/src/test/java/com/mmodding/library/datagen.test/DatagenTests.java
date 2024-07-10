package com.mmodding.library.datagen.test;

import com.mmodding.library.datagen.api.lang.LangProcessor;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.Block;
import net.minecraft.block.MapColor;
import net.minecraft.block.Material;
import net.minecraft.item.Items;
import net.minecraft.recipe.RecipeCategory;

public class DatagenTests {

	public static final Block BLOCK = new Block(FabricBlockSettings.of(Material.AIR, MapColor.BLACK))
		.<Block>lang(LangProcessor.standard())
		.recipe(helper -> helper.shapeless(RecipeCategory.BUILDING_BLOCKS, recipe -> recipe.with(Items.DIAMOND)));

	public void register() {}
}
