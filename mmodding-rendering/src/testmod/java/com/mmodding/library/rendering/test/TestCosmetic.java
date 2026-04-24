package com.mmodding.library.rendering.test;

import com.mmodding.library.rendering.api.cosmetic.Cosmetic;
import com.mmodding.library.rendering.api.model.EntityModelFactory;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.renderer.entity.state.HumanoidRenderState;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.ItemStack;

import java.util.Map;

public record TestCosmetic(ModelLayerLocation location, String texture) implements Cosmetic {

	public static final TestCosmetic CAP = new TestCosmetic(RenderingTestsClient.CAP, "head");
	public static final TestCosmetic SUIT = new TestCosmetic(RenderingTestsClient.SUIT, "chest");
	public static final TestCosmetic PANTS = new TestCosmetic(RenderingTestsClient.PANTS, "legs");
	public static final TestCosmetic SHOES = new TestCosmetic(RenderingTestsClient.SHOES, "feet");

	@Override
	public Map<String, EntityModelFactory<HumanoidRenderState>> getModelFactories() {
		return Map.of("main", EntityModelFactory.of(this.location()));
	}

	@Override
	public String getModel(ItemStack stack, boolean isSlim) {
		return "main";
	}

	@Override
	public Identifier getTexture(ItemStack stack, boolean isSlim) {
		return Identifier.fromNamespaceAndPath("mmodding_rendering_testmod", "test_" + this.texture() + ".png");
	}
}
