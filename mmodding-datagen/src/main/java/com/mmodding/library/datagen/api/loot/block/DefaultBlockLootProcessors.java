package com.mmodding.library.datagen.api.loot.block;

import net.minecraft.data.loot.BlockLootSubProvider;

public class DefaultBlockLootProcessors {

	public static final BlockLootProcessor SIMPLE = BlockLootSubProvider::dropSelf;

	public static final BlockLootProcessor SILK_TOUCH = BlockLootSubProvider::dropWhenSilkTouch;
}
