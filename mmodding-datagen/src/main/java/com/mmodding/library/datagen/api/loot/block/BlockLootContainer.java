package com.mmodding.library.datagen.api.loot.block;

import com.mmodding.library.core.api.management.info.InjectedContent;
import net.minecraft.block.Block;

@InjectedContent(Block.class)
public interface BlockLootContainer {

	default Block loot(BlockLootProcessor processor) {
		throw new IllegalStateException();
	}
}
