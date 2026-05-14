package com.mmodding.library.block.test;

import com.mmodding.library.block.api.catalog.SimpleBedBlock;
import com.mmodding.library.block.api.util.BlockFactory;
import com.mmodding.library.block.api.wrapper.BlockHeap;
import com.mmodding.library.core.api.AdvancedContainer;
import com.mmodding.library.core.api.management.ElementsManager;
import com.mmodding.library.core.api.ExtendedModInitializer;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.FurnaceBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;

public class BlockTests implements ExtendedModInitializer {

	public static final Block FIRST_BLOCK = register("first_block", BlockBehaviour.Properties.ofFullCopy(Blocks.AIR)).registerItem();

	public static final Block SECOND_BLOCK = register("second_block", BlockBehaviour.Properties.ofFullCopy(Blocks.AIR)).registerItem();

	public static final BlockHeap FURNACE_BLOCKS = BlockHeap.register(FurnaceBlock::new, () -> BlockBehaviour.Properties.ofFullCopy(Blocks.FURNACE), "mmodding_block_tests", "red", "green", "blue").registerBlockItems();

	public static final Block TEST_SIZED = register("test_sized", TestSizedBlock::new, BlockBehaviour.Properties.ofFullCopy(Blocks.STONE)).registerItem();

	public static final Block TEST_FACING_SIZED = register("test_facing_sized", TestFacingSizedBlock::new, BlockBehaviour.Properties.ofFullCopy(Blocks.STONE)).registerItem();

	public static final Block TEST_BED_BLOCK = register("test_bed_block", SimpleBedBlock::new, BlockBehaviour.Properties.ofFullCopy(Blocks.WHITE_BED)).registerItem();

	@Override
	public void setupManager(ElementsManager manager) {
		manager.content(BlockTests::registerBlocks);
	}

	@Override
	public void onInitialize(AdvancedContainer mod) {}

	public static Block register(String path, BlockBehaviour.Properties properties) {
		return register(path, Block::new, properties);
	}

	public static <T extends Block> Block register(String path, BlockFactory<T> factory, BlockBehaviour.Properties properties) {
		return Blocks.register(ResourceKey.create(Registries.BLOCK, Identifier.fromNamespaceAndPath("mmodding_block_tests", path)), factory::make, properties);
	}

	public static void registerBlocks(AdvancedContainer mod) {}
}
