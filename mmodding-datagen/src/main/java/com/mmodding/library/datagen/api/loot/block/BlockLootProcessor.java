package com.mmodding.library.datagen.api.loot.block;

import com.mmodding.library.datagen.impl.loot.BlockLootProcessorImpl;
import net.minecraft.block.Block;
import net.minecraft.data.server.loottable.BlockLootTableGenerator;

@FunctionalInterface
public interface BlockLootProcessor {

	static BlockLootProcessor standard() {
		return new BlockLootProcessorImpl();
	}

	void process(Block block, BlockLootTableGenerator generator);
}
