package com.mmodding.library.datagen.api.loot.block;

import net.minecraft.data.loot.BlockLootSubProvider;
import net.minecraft.world.level.block.Block;

@FunctionalInterface
public interface BlockLootProcessor {

	void process(BlockLootSubProvider generator, Block block);
}
