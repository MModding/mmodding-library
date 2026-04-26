package com.mmodding.library.rendering.api.cosmetic;

import com.mmodding.library.rendering.api.model.EntityModelFactory;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.renderer.entity.state.HumanoidRenderState;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.ItemStack;

import java.util.Map;

public record SimpleCosmetic(ModelLayerLocation location, Identifier texture) implements Cosmetic {

	@Override
	public Map<String, EntityModelFactory<HumanoidRenderState>> getModelFactories() {
		return Map.of("main", EntityModelFactory.of(this.location));
	}

	@Override
	public String getModel(ItemStack stack, boolean isSlim) {
		return "main";
	}

	@Override
	public Identifier getTexture(ItemStack stack, boolean isSlim) {
		return this.texture;
	}
}
