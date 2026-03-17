package com.mmodding.library.datagen.api.model.block;

import net.minecraft.block.Block;
import net.minecraft.data.client.BlockStateModelGenerator;

public interface BlockStateModelProcessor {

	void process(BlockStateModelGenerator generator, Block block);
}
