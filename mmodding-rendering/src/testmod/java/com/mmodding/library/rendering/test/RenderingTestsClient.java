package com.mmodding.library.rendering.test;

import com.mmodding.library.rendering.api.accessory.renderer.CapModelRenderer;
import com.mmodding.library.rendering.api.accessory.renderer.SuitModelRenderer;
import com.mmodding.library.rendering.test.accessory.TestCap;
import com.mmodding.library.rendering.test.accessory.TestSuit;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.ArmorRenderer;
import net.fabricmc.fabric.api.client.rendering.v1.ModelLayerRegistry;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.resources.Identifier;

public class RenderingTestsClient implements ClientModInitializer {

	public static final ModelLayerLocation CAP = new ModelLayerLocation(Identifier.fromNamespaceAndPath("mmodding_rendering_testmod", "test_cap"), "main");
	public static final ModelLayerLocation SUIT = new ModelLayerLocation(Identifier.fromNamespaceAndPath("mmodding_rendering_testmod", "test_suit"), "main");

	@Override
	public void onInitializeClient() {
		ModelLayerRegistry.registerModelLayer(CAP, TestModels::createTestCapLayer);
		ModelLayerRegistry.registerModelLayer(SUIT, TestModels::createTestSuitLayer);
		ArmorRenderer.register(context -> new CapModelRenderer(new TestCap(), CapModelRenderer.Anchor.HEAD_CENTER, context), RenderingTests.TEST_CAP);
		ArmorRenderer.register(context -> new SuitModelRenderer(new TestSuit(), context), RenderingTests.TEST_SUIT);
	}
}
