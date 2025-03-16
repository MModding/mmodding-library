package com.mmodding.library.datagen.impl.loot;

import com.mmodding.library.datagen.api.loot.block.BlockLootProcessor;
import net.minecraft.block.Block;
import net.minecraft.data.server.loottable.BlockLootTableGenerator;
import org.jetbrains.annotations.ApiStatus;

@ApiStatus.Internal
public class BlockLootProcessorImpl implements BlockLootProcessor {

	@Override
	public void process(Block block, BlockLootTableGenerator generator) {
		generator.addDrop(block);
	}
}
