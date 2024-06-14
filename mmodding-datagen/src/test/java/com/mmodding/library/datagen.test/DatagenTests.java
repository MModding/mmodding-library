package com.mmodding.library.datagen.test;

import com.mmodding.library.datagen.api.lang.LangProcessor;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.Block;
import net.minecraft.block.MapColor;
import net.minecraft.block.Material;

public class DatagenTests {

	public static final Block BLOCK = new Block(FabricBlockSettings.of(Material.AIR, MapColor.BLACK)).lang(LangProcessor.standard());

	public void register() {}
}
