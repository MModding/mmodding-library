package com.mmodding.library.resource.api.client.cosmetic.catalog;

import com.mmodding.library.resource.api.client.cosmetic.Cosmetic;
import com.mmodding.library.resource.api.client.model.EntityModelFactory;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.renderer.entity.state.HumanoidRenderState;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.ItemStack;

import java.util.Map;

public record WithSlimCosmetic(ModelLayerLocation normal, ModelLayerLocation slim, Identifier texture) implements Cosmetic {

	@Override
	public Map<String, EntityModelFactory<HumanoidRenderState>> getModelFactories() {
		return Map.of(
			"normal", EntityModelFactory.of(this.normal),
			"slim", EntityModelFactory.of(this.slim)
		);
	}

	@Override
	public String getModel(ItemStack stack, boolean isSlim) {
		return isSlim ? "slim" : "normal";
	}

	@Override
	public Identifier getTexture(ItemStack stack, boolean isSlim) {
		return this.texture;
	}
}
