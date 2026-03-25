package com.mmodding.library.block.api.util;

import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.Block;

public interface AdvancedBlockFactory<T extends Block> {

	static <T extends Block> AdvancedBlockFactory<T> of(BlockFactory<T> factory) {
		return (string, settings) -> factory.make(settings);
	}

	T make(String string, FabricBlockSettings settings);
}
