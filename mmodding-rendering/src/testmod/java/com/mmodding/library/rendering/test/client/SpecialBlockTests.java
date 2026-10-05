package com.mmodding.library.rendering.test.client;

import com.mmodding.library.rendering.api.client.block.SpecialBlockRegistry;
import net.fabricmc.api.ClientModInitializer;
import net.minecraft.world.level.block.Blocks;

public class SpecialBlockTests implements ClientModInitializer {

	@Override
	public void onInitializeClient() {
		SpecialBlockRegistry.register(Blocks.BAMBOO, BoatSpecialRenderer::new);
	}
}
