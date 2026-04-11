package com.mmodding.library.datagen.api.model.block;

import net.minecraft.data.models.BlockModelGenerators;
import net.minecraft.data.models.blockstates.Condition;
import net.minecraft.data.models.blockstates.MultiPartGenerator;
import net.minecraft.data.models.blockstates.Variant;
import net.minecraft.data.models.blockstates.VariantProperties;
import net.minecraft.data.models.model.ModelLocationUtils;
import net.minecraft.data.models.model.ModelTemplate;
import net.minecraft.data.models.model.ModelTemplates;
import net.minecraft.data.models.model.TextureMapping;
import net.minecraft.data.models.model.TextureSlot;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;

public class DefaultBlockModelProcessing {

	public static final ModelTemplate LADDER = ModelTemplates.create("ladder", TextureSlot.TEXTURE, TextureSlot.PARTICLE);

	/**
	 * Registers a ladder-like block state, block model, and item model.
	 * @param generator the generator
	 * @param block the ladder-like block
	 */
	public static void ladder(BlockModelGenerators generator, Block block) {
		generator.createNonTemplateHorizontalBlock(block);
		LADDER.create(block, TextureMapping.defaultTexture(block).put(TextureSlot.PARTICLE, TextureMapping.getBlockTexture(block)), generator.modelOutput);
		generator.createSimpleFlatItemModel(block);
	}

	/**
	 * Registers a pane block state, block model, and item model.
	 * @param generator the generator
	 * @param glassBlock the block from which the pane block is made
	 * @param paneBlock the pane block
	 */
	public static void pane(BlockModelGenerators generator, Block glassBlock, Block paneBlock) {
		DefaultBlockModelProcessing.pane(generator, glassBlock, paneBlock, TextureMapping.getBlockTexture(paneBlock, "_top"));
	}

	/**
	 * Registers a pane block state, block model, and item model.
	 * @param generator the generator
	 * @param glassBlock the block from which the pane block is made
	 * @param paneBlock the pane block
	 * @param paneTopPath the location of the pane top texture
	 */
	public static void pane(BlockModelGenerators generator, Block glassBlock, Block paneBlock, ResourceLocation paneTopPath) {
		TextureMapping textures = new TextureMapping();
		textures.put(TextureSlot.PANE, TextureMapping.getBlockTexture(glassBlock)).put(TextureSlot.EDGE, paneTopPath);
		ResourceLocation panePost = ModelTemplates.STAINED_GLASS_PANE_POST.create(paneBlock, textures, generator.modelOutput);
		ResourceLocation paneSide = ModelTemplates.STAINED_GLASS_PANE_SIDE.create(paneBlock, textures, generator.modelOutput);
		ResourceLocation paneSideAlt = ModelTemplates.STAINED_GLASS_PANE_SIDE_ALT.create(paneBlock, textures, generator.modelOutput);
		ResourceLocation paneNoSide = ModelTemplates.STAINED_GLASS_PANE_NOSIDE.create(paneBlock, textures, generator.modelOutput);
		ResourceLocation paneNoSideAlt = ModelTemplates.STAINED_GLASS_PANE_NOSIDE_ALT.create(paneBlock, textures, generator.modelOutput);
		if (paneBlock.asItem() != Items.AIR) {
			ModelTemplates.FLAT_ITEM.create(ModelLocationUtils.getModelLocation(paneBlock.asItem()), TextureMapping.layer0(glassBlock), generator.modelOutput);
		}
		generator.blockStateOutput.accept(
			MultiPartGenerator.multiPart(paneBlock)
				.with(
					Variant.variant()
						.with(VariantProperties.MODEL, panePost)
				)
				.with(
					Condition.condition().term(BlockStateProperties.NORTH, true),
					Variant.variant()
						.with(VariantProperties.MODEL, paneSide)
				)
				.with(
					Condition.condition().term(BlockStateProperties.EAST, true),
					Variant.variant()
						.with(VariantProperties.MODEL, paneSide)
						.with(VariantProperties.Y_ROT, VariantProperties.Rotation.R90)
				)
				.with(
					Condition.condition().term(BlockStateProperties.SOUTH, true),
					Variant.variant()
						.with(VariantProperties.MODEL, paneSideAlt)
				)
				.with(
					Condition.condition().term(BlockStateProperties.WEST, true),
					Variant.variant()
						.with(VariantProperties.MODEL, paneSideAlt)
						.with(VariantProperties.Y_ROT, VariantProperties.Rotation.R90)
				)
				.with(
					Condition.condition().term(BlockStateProperties.NORTH, false),
					Variant.variant()
						.with(VariantProperties.MODEL, paneNoSide)
				)
				.with(
					Condition.condition().term(BlockStateProperties.EAST, false),
					Variant.variant()
						.with(VariantProperties.MODEL, paneNoSideAlt)
				)
				.with(
					Condition.condition().term(BlockStateProperties.SOUTH, false),
					Variant.variant().with(VariantProperties.MODEL, paneNoSideAlt)
						.with(VariantProperties.Y_ROT, VariantProperties.Rotation.R90)
				)
				.with(
					Condition.condition().term(BlockStateProperties.WEST, false),
					Variant.variant().with(VariantProperties.MODEL, paneNoSide)
						.with(VariantProperties.Y_ROT, VariantProperties.Rotation.R270)
				)
		);
	}
}
