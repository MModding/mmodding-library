package com.mmodding.library.rendering.test;

import com.mmodding.library.rendering.api.cosmetic.Cosmetic;
import com.mmodding.library.rendering.api.cosmetic.SimpleCosmetic;

public class TestCosmetics {

	public static final Cosmetic CAP = new SimpleCosmetic(RenderingTestsClient.CAP, RenderingTests.createTexture("test_head"));
	public static final Cosmetic SUIT = new SimpleCosmetic(RenderingTestsClient.SUIT, RenderingTests.createTexture("test_chest"));
	public static final Cosmetic PANTS = new SimpleCosmetic(RenderingTestsClient.PANTS, RenderingTests.createTexture("test_legs"));
	public static final Cosmetic SHOES = new SimpleCosmetic(RenderingTestsClient.SHOES, RenderingTests.createTexture("test_feet"));
}
