package com.mmodding.library.block.api.util;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;

@FunctionalInterface
public interface BlockFactory<T extends Block> {

	T make(BlockBehaviour.Properties properties);
}
