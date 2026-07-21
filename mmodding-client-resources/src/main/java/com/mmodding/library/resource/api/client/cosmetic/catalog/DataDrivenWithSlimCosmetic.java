package com.mmodding.library.resource.api.client.cosmetic.catalog;

import com.mmodding.library.resource.api.client.cosmetic.Cosmetic;
import com.mmodding.library.resource.api.client.model.EntityModelFactory;
import net.minecraft.client.renderer.entity.state.HumanoidRenderState;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.ItemStack;

import java.util.Map;

public record DataDrivenWithSlimCosmetic(Identifier normal, Identifier slim, Identifier texture) implements Cosmetic {

	@Override
	public Map<String, EntityModelFactory<HumanoidRenderState>> getModelFactories() {
		return Map.of(
			"normal", context -> context.getDataDrivenModel(this.normal),
			"slim", context -> context.getDataDrivenModel(this.slim)
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
