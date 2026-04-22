package com.mmodding.library.rendering.test.accessory;

import com.mmodding.library.rendering.api.accessory.Accessory;
import com.mmodding.library.rendering.api.model.EntityModelFactory;
import com.mmodding.library.rendering.test.RenderingTestsClient;
import net.minecraft.client.renderer.entity.state.HumanoidRenderState;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.ItemStack;

import java.util.Map;

public class TestCap implements Accessory {

	@Override
	public Map<String, EntityModelFactory<HumanoidRenderState>> getModelFactories() {
		return Map.of("main", EntityModelFactory.of(RenderingTestsClient.CAP));
	}

	@Override
	public String getModel(ItemStack stack, boolean isSlim) {
		return "main";
	}

	@Override
	public Identifier getTexture(ItemStack stack, boolean isSlim) {
		return Identifier.fromNamespaceAndPath("mmodding_rendering_testmod", "cap/dummy.png");
	}
}
