package com.mmodding.library.datagen.impl.loot;

import com.mmodding.library.datagen.api.loot.block.BlockLootProcessor;
import net.minecraft.block.Block;
import net.minecraft.data.server.loottable.BlockLootTableGenerator;
import net.minecraft.loot.LootTable;
import net.minecraft.registry.Registries;
import net.minecraft.registry.RegistryKey;
import org.jetbrains.annotations.ApiStatus;

@ApiStatus.Internal
public class BlockLootProcessorImpl implements BlockLootProcessor {

	@Override
	public LootTable.Builder process(RegistryKey<Block> key, BlockLootTableGenerator generator) {
		return generator.drops(Registries.BLOCK.get(key));
	}
}
