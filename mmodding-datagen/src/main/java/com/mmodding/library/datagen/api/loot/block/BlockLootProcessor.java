package com.mmodding.library.datagen.api.loot.block;

import com.mmodding.library.datagen.impl.loot.BlockLootProcessorImpl;
import net.minecraft.block.Block;
import net.minecraft.data.server.loottable.BlockLootTableGenerator;
import net.minecraft.loot.LootTable;
import net.minecraft.registry.RegistryKey;

@FunctionalInterface
public interface BlockLootProcessor {

	static BlockLootProcessor standard() {
		return new BlockLootProcessorImpl();
	}

	LootTable.Builder process(RegistryKey<Block> key, BlockLootTableGenerator generator);
}
