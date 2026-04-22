package com.mmodding.library.rendering.api.accessory.renderer;

import com.mmodding.library.rendering.api.accessory.Accessory;
import net.fabricmc.fabric.api.client.rendering.v1.ArmorRenderer;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.state.AvatarRenderState;
import net.minecraft.client.renderer.entity.state.HumanoidRenderState;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.PlayerModelType;
import net.minecraft.world.item.ItemStack;

import java.util.Map;
import java.util.stream.Collectors;

/**
 * Rendering {@link Accessory} stuff.
 */
public abstract class AccessoryRenderer implements ArmorRenderer {

	private final Accessory accessory;
	private final Map<String, EntityModel<HumanoidRenderState>> models;

	public AccessoryRenderer(Accessory accessory, EntityRendererProvider.Context context) {
		this.accessory = accessory;
		this.models = accessory.getModelFactories().entrySet()
			.stream()
			.map(entry -> Map.entry(entry.getKey(), entry.getValue().createModel(context)))
			.collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
	}

	protected boolean isSlim(HumanoidRenderState state) {
		return state instanceof AvatarRenderState avatar && avatar.skin.model().equals(PlayerModelType.SLIM);
	}

	protected EntityModel<HumanoidRenderState> getModel(ItemStack stack, boolean isSlim) {
		return this.models.get(this.accessory.getModel(stack, isSlim));
	}

	protected Identifier getTexture(ItemStack stack, boolean isSlim) {
		return this.accessory.getTexture(stack, isSlim);
	}

	@Override
	public boolean shouldRenderDefaultHeadItem(LivingEntity entity, ItemStack stack) {
		return false;
	}
}
