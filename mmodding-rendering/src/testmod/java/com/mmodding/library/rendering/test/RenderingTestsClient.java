package com.mmodding.library.rendering.test;

import com.mmodding.library.rendering.api.cosmetic.renderer.CapCosmeticRenderer;
import com.mmodding.library.rendering.api.cosmetic.renderer.PantsCosmeticRenderer;
import com.mmodding.library.rendering.api.cosmetic.renderer.ShoesCosmeticRenderer;
import com.mmodding.library.rendering.api.cosmetic.renderer.SuitCosmeticRenderer;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.ArmorRenderer;
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
		ArmorRenderer.register(context -> new CapCosmeticRenderer(TestCosmetic.CAP, CapCosmeticRenderer.Anchor.HEAD_CENTER, context), RenderingTests.TEST_CAP);
		ArmorRenderer.register(context -> new SuitCosmeticRenderer(TestCosmetic.SUIT, context), RenderingTests.TEST_SUIT);
		ArmorRenderer.register(context -> new PantsCosmeticRenderer(TestCosmetic.PANTS, context), RenderingTests.TEST_PANTS);
		ArmorRenderer.register(context -> new ShoesCosmeticRenderer(TestCosmetic.SHOES, context), RenderingTests.TEST_SHOES);
	}
}
