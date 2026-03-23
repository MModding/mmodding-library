package com.mmodding.library.datagen.api.model.block;

import net.minecraft.block.Block;
import net.minecraft.data.client.*;
import net.minecraft.item.Items;
import net.minecraft.state.property.Properties;
import net.minecraft.util.Identifier;

public class DefaultBlockModelProcessing {

	public static final Model LADDER = Models.block("ladder", TextureKey.TEXTURE, TextureKey.PARTICLE);

	/**
	 * Registers a ladder-like block state, block model, and item model.
	 * @param generator the generator
	 * @param block the ladder-like block
	 */
	public static void ladder(BlockStateModelGenerator generator, Block block) {
		generator.registerNorthDefaultHorizontalRotation(block);
		LADDER.upload(block, TextureMap.texture(block).put(TextureKey.PARTICLE, TextureMap.getId(block)), generator.modelCollector);
		generator.registerItemModel(block);
	}

	/**
	 * Registers a pane block state, block model, and item model.
	 * @param generator the generator
	 * @param glassBlock the block from which the pane block is made
	 * @param paneBlock the pane block
	 */
	public static void pane(BlockStateModelGenerator generator, Block glassBlock, Block paneBlock) {
		DefaultBlockModelProcessing.pane(generator, glassBlock, paneBlock, TextureMap.getSubId(paneBlock, "_top"));
	}

	/**
	 * Registers a pane block state, block model, and item model.
	 * @param generator the generator
	 * @param glassBlock the block from which the pane block is made
	 * @param paneBlock the pane block
	 * @param paneTopPath the location of the pane top texture
	 */
	public static void pane(BlockStateModelGenerator generator, Block glassBlock, Block paneBlock, Identifier paneTopPath) {
		TextureMap textures = new TextureMap();
		textures.put(TextureKey.PANE, TextureMap.getId(glassBlock)).put(TextureKey.EDGE, paneTopPath);
		Identifier panePost = Models.TEMPLATE_GLASS_PANE_POST.upload(paneBlock, textures, generator.modelCollector);
		Identifier paneSide = Models.TEMPLATE_GLASS_PANE_SIDE.upload(paneBlock, textures, generator.modelCollector);
		Identifier paneSideAlt = Models.TEMPLATE_GLASS_PANE_SIDE_ALT.upload(paneBlock, textures, generator.modelCollector);
		Identifier paneNoSide = Models.TEMPLATE_GLASS_PANE_NOSIDE.upload(paneBlock, textures, generator.modelCollector);
		Identifier paneNoSideAlt = Models.TEMPLATE_GLASS_PANE_NOSIDE_ALT.upload(paneBlock, textures, generator.modelCollector);
		if (paneBlock.asItem() != Items.AIR) {
			Models.GENERATED.upload(ModelIds.getItemModelId(paneBlock.asItem()), TextureMap.layer0(glassBlock), generator.modelCollector);
		}
		generator.blockStateCollector.accept(
			MultipartBlockStateSupplier.create(paneBlock)
				.with(
					BlockStateVariant.create()
						.put(VariantSettings.MODEL, panePost)
				)
				.with(
					When.create().set(Properties.NORTH, true),
					BlockStateVariant.create()
						.put(VariantSettings.MODEL, paneSide)
				)
				.with(
					When.create().set(Properties.EAST, true),
					BlockStateVariant.create()
						.put(VariantSettings.MODEL, paneSide)
						.put(VariantSettings.Y, VariantSettings.Rotation.R90)
				)
				.with(
					When.create().set(Properties.SOUTH, true),
					BlockStateVariant.create()
						.put(VariantSettings.MODEL, paneSideAlt)
				)
				.with(
					When.create().set(Properties.WEST, true),
					BlockStateVariant.create()
						.put(VariantSettings.MODEL, paneSideAlt)
						.put(VariantSettings.Y, VariantSettings.Rotation.R90)
				)
				.with(
					When.create().set(Properties.NORTH, false),
					BlockStateVariant.create()
						.put(VariantSettings.MODEL, paneNoSide)
				)
				.with(
					When.create().set(Properties.EAST, false),
					BlockStateVariant.create()
						.put(VariantSettings.MODEL, paneNoSideAlt)
				)
				.with(
					When.create().set(Properties.SOUTH, false),
					BlockStateVariant.create().put(VariantSettings.MODEL, paneNoSideAlt)
						.put(VariantSettings.Y, VariantSettings.Rotation.R90)
				)
				.with(
					When.create().set(Properties.WEST, false),
					BlockStateVariant.create().put(VariantSettings.MODEL, paneNoSide)
						.put(VariantSettings.Y, VariantSettings.Rotation.R270)
				)
		);
	}
}
