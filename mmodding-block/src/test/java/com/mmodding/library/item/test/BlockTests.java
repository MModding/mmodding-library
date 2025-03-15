package com.mmodding.library.item.test;

import com.mmodding.library.block.api.util.BlockHeap;
import com.mmodding.library.core.api.container.AdvancedContainer;
import com.mmodding.library.core.api.management.ElementsManager;
import com.mmodding.library.core.api.management.initializer.ExtendedModInitializer;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.block.FurnaceBlock;
import net.minecraft.registry.Registries;

public class BlockTests implements ExtendedModInitializer {

	public static final Block FIRST_BLOCK = new Block(FabricBlockSettings.copyOf(Blocks.AIR)).withItem(new FabricItemSettings());

	public static final Block SECOND_BLOCK = new Block(FabricBlockSettings.copyOf(Blocks.AIR)).withItem(new FabricItemSettings());

	public static final BlockHeap<FurnaceBlock> FURNACE_BLOCKS = BlockHeap.create(FurnaceBlock::new, FabricBlockSettings.copyOf(Blocks.FURNACE), "red", "green", "blue");

	@Override
	public void setupManager(ElementsManager.Builder manager) {
		manager.content(BlockTests::registerBlocks);
	}

	@Override
	public void onInitialize(AdvancedContainer mod) {}

	public static void registerBlocks(AdvancedContainer mod) {
		mod.withRegistry(Registries.BLOCK).execute(pusher -> {
			FIRST_BLOCK.register(pusher.createId("first_block"));
			SECOND_BLOCK.register(pusher.createId("second_block"));
		});
		FURNACE_BLOCKS.register(name -> mod.createId(name + "_furnace"));
	}
}
