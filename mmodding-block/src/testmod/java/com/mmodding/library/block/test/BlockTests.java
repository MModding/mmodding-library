package com.mmodding.library.block.test;

import com.mmodding.library.block.api.wrapper.BlockHeap;
import com.mmodding.library.core.api.AdvancedContainer;
import com.mmodding.library.core.api.management.ElementsManager;
import com.mmodding.library.core.api.ExtendedModInitializer;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.FurnaceBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;

public class BlockTests implements ExtendedModInitializer {

	public static final Block FIRST_BLOCK = new Block(BlockBehaviour.Properties.ofFullCopy(Blocks.AIR)).registerItem(new Item.Properties());

	public static final Block SECOND_BLOCK = new Block(BlockBehaviour.Properties.ofFullCopy(Blocks.AIR)).registerItem(new Item.Properties());

	public static final BlockHeap FURNACE_BLOCKS = BlockHeap.register(FurnaceBlock::new, BlockBehaviour.Properties.ofFullCopy(Blocks.FURNACE), "mmodding_test", "red", "green", "blue");

	@Override
	public void setupManager(ElementsManager manager) {
		manager.content(BlockTests::registerBlocks);
	}

	@Override
	public void onInitialize(AdvancedContainer mod) {}

	public static void registerBlocks(AdvancedContainer mod) {
		mod.register(BuiltInRegistries.BLOCK, factory -> {
			factory.register("first_block", FIRST_BLOCK);
			factory.register("second_block", SECOND_BLOCK);
		});
		mod.register(BuiltInRegistries.ITEM, factory -> {
			factory.register("first_block", FIRST_BLOCK.asItem());
			factory.register("second_block", SECOND_BLOCK.asItem());
		});
	}
}
