package com.mmodding.library.resource.test.client;

import com.mmodding.library.resource.api.client.cosmetic.catalog.DirectCosmetic;
import com.mmodding.library.resource.api.client.cosmetic.renderer.CosmeticRendererRegistry;
import com.mmodding.library.resource.api.client.cosmetic.renderer.HeadAnchor;
import com.mmodding.library.resource.api.client.model.data.DataDrivenModelEvents;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.ModelLayerRegistry;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.resources.Identifier;

public class RenderingTestsClient implements ClientModInitializer {

	public static final ModelLayerLocation CAP = new ModelLayerLocation(Identifier.fromNamespaceAndPath("mmodding_rendering_testmod", "test_cap"), "main");
	public static final ModelLayerLocation SUIT = new ModelLayerLocation(Identifier.fromNamespaceAndPath("mmodding_rendering_testmod", "test_suit"), "main");
	public static final ModelLayerLocation PANTS = new ModelLayerLocation(Identifier.fromNamespaceAndPath("mmodding_rendering_testmod", "test_pants"), "main");
	public static final ModelLayerLocation SHOES = new ModelLayerLocation(Identifier.fromNamespaceAndPath("mmodding_rendering_testmod", "test_shoes"), "main");

	@Override
	public void onInitializeClient() {
		ModelLayerRegistry.registerModelLayer(CAP, TestModels::createTestCapLayer);
		ModelLayerRegistry.registerModelLayer(SUIT, TestModels::createTestSuitLayer);
		ModelLayerRegistry.registerModelLayer(PANTS, TestModels::createTestPantsLayer);
		ModelLayerRegistry.registerModelLayer(SHOES, TestModels::createTestShoesLayer);
		CosmeticRendererRegistry.registerCapRenderer(TestCosmetics.CAP, HeadAnchor.HEAD_CENTER, RenderingTests.TEST_CAP);
		CosmeticRendererRegistry.registerSuitRenderer(TestCosmetics.SUIT, RenderingTests.TEST_SUIT);
		CosmeticRendererRegistry.registerPantsRenderer(TestCosmetics.PANTS, RenderingTests.TEST_PANTS);
		CosmeticRendererRegistry.registerShoesRenderer(TestCosmetics.SHOES, RenderingTests.TEST_SHOES);
	}
}
