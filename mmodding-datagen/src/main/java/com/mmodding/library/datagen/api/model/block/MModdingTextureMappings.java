package com.mmodding.library.datagen.api.model.block;

import net.minecraft.client.data.models.model.TextureMapping;
import net.minecraft.client.data.models.model.TextureSlot;
import net.minecraft.world.level.block.Block;

public class MModdingTextureMappings {

	public static TextureMapping specificParticle(Block block, Block particle) {
		return TextureMapping.defaultTexture(block)
			.put(TextureSlot.PARTICLE, TextureMapping.getBlockTexture(particle));
	}

	public static TextureMapping orientableWithBack(Block block) {
		return TextureMapping.orientableCube(block)
			.put(TextureSlot.BACK, TextureMapping.getBlockTexture(block, "_back"));
	}
}
