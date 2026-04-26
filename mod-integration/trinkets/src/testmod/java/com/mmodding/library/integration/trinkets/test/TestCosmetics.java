package com.mmodding.library.integration.trinkets.test;

import com.mmodding.library.rendering.api.cosmetic.Cosmetic;
import com.mmodding.library.rendering.api.cosmetic.SimpleCosmetic;

public class TestCosmetics {

	public static final Cosmetic CAP = new SimpleCosmetic(IntegrationTestsClient.CAP, IntegrationTests.createTexture("test_head"));
	public static final Cosmetic SUIT = new SimpleCosmetic(IntegrationTestsClient.SUIT, IntegrationTests.createTexture("test_chest"));
	public static final Cosmetic PANTS = new SimpleCosmetic(IntegrationTestsClient.PANTS, IntegrationTests.createTexture("test_legs"));
	public static final Cosmetic SHOES = new SimpleCosmetic(IntegrationTestsClient.SHOES, IntegrationTests.createTexture("test_feet"));
}
