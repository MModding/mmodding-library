package com.mmodding.library.datagen.api.loot.block;

import net.minecraft.data.server.loottable.BlockLootTableGenerator;

public class DefaultBlockLootProcessors {

	public static final BlockLootProcessor SIMPLE = BlockLootTableGenerator::addDrop;

	public static final BlockLootProcessor SILK_TOUCH = BlockLootTableGenerator::addDropWithSilkTouch;
}
