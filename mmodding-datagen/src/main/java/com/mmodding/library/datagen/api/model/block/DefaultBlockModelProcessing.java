package com.mmodding.library.datagen.api.model.block;

import com.mmodding.library.java.api.function.AutoMapper;
import com.mojang.math.Quadrant;
import net.minecraft.client.data.models.BlockModelGenerators;
import net.minecraft.client.data.models.MultiVariant;
import net.minecraft.client.data.models.blockstates.MultiPartGenerator;
import net.minecraft.client.data.models.model.*;
import net.minecraft.client.renderer.block.dispatch.Variant;
import net.minecraft.client.resources.model.sprite.Material;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;

import static net.minecraft.client.data.models.BlockModelGenerators.condition;
import static net.minecraft.client.data.models.BlockModelGenerators.variant;

public class DefaultBlockModelProcessing {

	public static final ModelTemplate LADDER = ModelTemplates.create("ladder", TextureSlot.TEXTURE, TextureSlot.PARTICLE);

	public static void createChain(BlockModelGenerators generator, Block block) {
		MultiVariant variant = BlockModelGenerators.plainVariant(TexturedModel.CHAIN.create(block, generator.modelOutput));
		generator.createAxisAlignedPillarBlockCustomModel(block, variant);
		generator.registerSimpleFlatItemModel(block.asItem());
	}

	/**
	 * Registers a ladder-like block state, block model, and item model.
	 * @param generator the generator
	 * @param block the ladder-like block
	 */
	public static void createLadder(BlockModelGenerators generator, Block block) {
		generator.createNonTemplateHorizontalBlock(block);
		LADDER.create(block, TextureMapping.defaultTexture(block).put(TextureSlot.PARTICLE, TextureMapping.getBlockTexture(block)), generator.modelOutput);
		generator.registerSimpleFlatItemModel(block);
	}

	/**
	 * Registers a glass pane block state, block model, and item model.
	 * @param generator the generator
	 * @param paneBlock the pane block
	 */
	public static void createGlassPane(BlockModelGenerators generator, Block paneBlock) {
		DefaultBlockModelProcessing.createPaneLike(generator, paneBlock, path -> path.substring(0, path.length() - 5), TextureMapping.getBlockTexture(paneBlock, "_top"));
	}

	/**
	 * Registers a pane block state, block model, and item model, by getting its associated glass block automatically.
	 * @param generator the generator
	 * @param paneBlock the pane block
	 * @param paneTopMaterial the pane top material
	 */
	public static void createPaneLike(BlockModelGenerators generator, Block paneBlock, Material paneTopMaterial) {
		DefaultBlockModelProcessing.createPaneLike(generator, paneBlock, path -> path.substring(0, path.length() - 5), paneTopMaterial);
	}

	/**
	 * Registers a pane block state, block model, and item model, by getting its associated glass block automatically.
	 * @param generator the generator
	 * @param paneBlock the pane block
	 * @param paneToGlass a mapper to get the glass path from the pane path
	 */
	public static void createPaneLike(BlockModelGenerators generator, Block paneBlock, AutoMapper<String> paneToGlass) {
		DefaultBlockModelProcessing.createPaneLike(generator, paneBlock, paneToGlass, TextureMapping.getBlockTexture(paneBlock, "_top"));
	}

	/**
	 * Registers a pane block state, block model, and item model, by getting its associated glass block automatically.
	 * @param generator the generator
	 * @param paneBlock the pane block
	 * @param paneToGlass a mapper to get the glass path from the pane path
	 * @param paneTopMaterial the pane top material
	 */
	public static void createPaneLike(BlockModelGenerators generator, Block paneBlock, AutoMapper<String> paneToGlass, Material paneTopMaterial) {
		ResourceKey<Block> glassBlockKey = paneBlock.builtInRegistryHolder().key()
			.mapIdentifier(identifier -> identifier.withPath(paneToGlass::map));
		Block glassBlock = BuiltInRegistries.BLOCK.getValueOrThrow(glassBlockKey);
		DefaultBlockModelProcessing.createPaneLike(generator, glassBlock, paneBlock, paneTopMaterial);
	}

	/**
	 * Registers a pane block state, block model, and item model.
	 * @param generator the generator
	 * @param glassBlock the block from which the pane block is made
	 * @param paneBlock the pane block
	 */
	public static void createPaneLike(BlockModelGenerators generator, Block glassBlock, Block paneBlock) {
		DefaultBlockModelProcessing.createPaneLike(generator, glassBlock, paneBlock, TextureMapping.getBlockTexture(paneBlock, "_top"));
	}

	/**
	 * Registers a pane block state, block model, and item model.
	 * @param generator the generator
	 * @param glassBlock the block from which the pane block is made
	 * @param paneBlock the pane block
	 * @param paneTopMaterial the pane top material
	 */
	public static void createPaneLike(BlockModelGenerators generator, Block glassBlock, Block paneBlock, Material paneTopMaterial) {
		TextureMapping textures = new TextureMapping();
		textures.put(TextureSlot.PANE, TextureMapping.getBlockTexture(glassBlock)).put(TextureSlot.EDGE, paneTopMaterial.withForceTranslucent(true));
		Identifier panePost = ModelTemplates.STAINED_GLASS_PANE_POST.create(paneBlock, textures, generator.modelOutput);
		Identifier paneSide = ModelTemplates.STAINED_GLASS_PANE_SIDE.create(paneBlock, textures, generator.modelOutput);
		Identifier paneSideAlt = ModelTemplates.STAINED_GLASS_PANE_SIDE_ALT.create(paneBlock, textures, generator.modelOutput);
		Identifier paneNoSide = ModelTemplates.STAINED_GLASS_PANE_NOSIDE.create(paneBlock, textures, generator.modelOutput);
		Identifier paneNoSideAlt = ModelTemplates.STAINED_GLASS_PANE_NOSIDE_ALT.create(paneBlock, textures, generator.modelOutput);
		if (paneBlock.asItem() != Items.AIR) {
			Identifier itemModel = generator.createFlatItemModelWithBlockTexture(paneBlock.asItem(), glassBlock);
			generator.registerSimpleItemModel(paneBlock, itemModel);
		}
		generator.blockStateOutput.accept(
			MultiPartGenerator.multiPart(paneBlock)
				.with(variant(new Variant(panePost)))
				.with(condition().term(BlockStateProperties.NORTH, true), variant(new Variant(paneSide)))
				.with(condition().term(BlockStateProperties.EAST, true), variant(new Variant(paneSide).withYRot(Quadrant.R90)))
				.with(condition().term(BlockStateProperties.SOUTH, true), variant(new Variant(paneSideAlt)))
				.with(condition().term(BlockStateProperties.WEST, true), variant(new Variant(paneSideAlt).withYRot(Quadrant.R90)))
				.with(condition().term(BlockStateProperties.NORTH, false), variant(new Variant(paneNoSide)))
				.with(condition().term(BlockStateProperties.EAST, false), variant(new Variant(paneNoSideAlt)))
				.with(condition().term(BlockStateProperties.SOUTH, false), variant(new Variant(paneNoSideAlt).withYRot(Quadrant.R90)))
				.with(condition().term(BlockStateProperties.WEST, false), variant(new Variant(paneNoSide).withYRot(Quadrant.R270)))
		);
	}
}
