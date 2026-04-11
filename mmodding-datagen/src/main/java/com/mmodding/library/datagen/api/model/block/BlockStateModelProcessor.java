package com.mmodding.library.datagen.api.model.block;

import net.minecraft.data.models.BlockModelGenerators;
import net.minecraft.world.level.block.Block;

public interface BlockStateModelProcessor {

	void process(BlockModelGenerators generator, Block block);
}
