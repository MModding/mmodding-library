package com.mmodding.library.datagen.api.loot.block;

import net.minecraft.block.Block;
import net.minecraft.data.server.loottable.BlockLootTableGenerator;

@FunctionalInterface
public interface BlockLootProcessor {

	void process(BlockLootTableGenerator generator, Block block);
}
