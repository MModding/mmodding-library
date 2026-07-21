package com.mmodding.library.resource.api.client.cosmetic.catalog;

import com.mmodding.library.resource.api.client.cosmetic.Cosmetic;
import com.mmodding.library.resource.api.client.model.EntityModelFactory;
import net.minecraft.client.renderer.entity.state.HumanoidRenderState;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.ItemStack;

import java.util.Map;

public record DataDrivenCosmetic(Identifier location, Identifier texture) implements Cosmetic {

	@Override
	public Map<String, EntityModelFactory<HumanoidRenderState>> getModelFactories() {
		return Map.of("main", context -> context.getDataDrivenModel(this.location));
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
