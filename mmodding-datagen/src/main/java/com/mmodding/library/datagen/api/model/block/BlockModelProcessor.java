package com.mmodding.library.datagen.api.model.block;

import net.minecraft.client.data.models.BlockModelGenerators;
import net.minecraft.world.level.block.Block;

public interface BlockModelProcessor {

	void process(BlockModelGenerators generator, Block block);
}
