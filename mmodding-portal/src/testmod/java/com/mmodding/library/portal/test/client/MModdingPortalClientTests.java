package com.mmodding.library.portal.test.client;

import com.mmodding.library.portal.api.client.ClientPortalEvents;
import com.mmodding.library.portal.test.MModdingPortalTests;
import net.fabricmc.api.ClientModInitializer;
import net.minecraft.client.Minecraft;
import net.minecraft.world.level.block.Blocks;

public class MModdingPortalClientTests implements ClientModInitializer {

	@Override
	public void onInitializeClient() {
		ClientPortalEvents.TRANSITION_SPRITE.register((level, player, portal, portalIntensity, defaultColor) -> { // Setting the Transition Screen for the Portal
			if (portal == MModdingPortalTests.EXAMPLE_PORTAL_BLOCK) {
				return new ClientPortalEvents.TransitionSprite.ColoredSprite(
					Minecraft.getInstance()
						.getModelManager()
						.getBlockStateModelSet()
						.getParticleMaterial(Blocks.END_STONE.defaultBlockState())
						.sprite(),
					defaultColor
				);
			}
			else {
				return null;
			}
		});
	}
}
