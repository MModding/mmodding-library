package com.mmodding.library.block.api.util;

import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.world.level.block.Block;

@FunctionalInterface
public interface BlockFactory<T extends Block> {

	T make(FabricBlockSettings settings);
}
