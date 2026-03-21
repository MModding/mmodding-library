package com.mmodding.library.datagen.api.model.block;

import net.minecraft.block.Block;
import net.minecraft.data.client.*;

public class DefaultBlockModelProcessing {

	public static final Model LADDER = Models.block("ladder", TextureKey.TEXTURE, TextureKey.PARTICLE);

	public static void ladder(BlockStateModelGenerator generator, Block block) {
		generator.registerNorthDefaultHorizontalRotation(block);
		LADDER.upload(block, TextureMap.textureParticle(block), generator.modelCollector);
		generator.registerItemModel(block);
	}
}
