package com.mmodding.library.resource.api.client.cosmetic.catalog;

import com.mmodding.library.resource.api.client.cosmetic.Cosmetic;
import com.mmodding.library.resource.api.client.model.EntityModelFactory;
import com.mmodding.library.resource.api.client.model.SimpleEntityModel;
import net.minecraft.client.renderer.entity.state.HumanoidRenderState;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.ItemStack;

import java.util.Map;

public record DirectWithSlimCosmetic(SimpleEntityModel<HumanoidRenderState> normal, SimpleEntityModel<HumanoidRenderState> slim, Identifier texture) implements Cosmetic {

	@Override
	public Map<String, EntityModelFactory<HumanoidRenderState>> getModelFactories() {
		return Map.of(
			"normal", _ -> this.normal,
			"slim", _ -> this.slim
		);
	}

	@Override
	public String getModel(ItemStack stack, boolean isSlim) {
		return isSlim ? "slim" : "main";
	}

	@Override
	public Identifier getTexture(ItemStack stack, boolean isSlim) {
		return this.texture;
	}
}
