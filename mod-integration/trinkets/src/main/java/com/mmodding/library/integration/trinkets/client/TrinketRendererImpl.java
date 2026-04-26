package com.mmodding.library.integration.trinkets.client;

import com.mojang.blaze3d.vertex.PoseStack;
import eu.pb4.trinkets.api.TrinketSlotAccess;
import eu.pb4.trinkets.api.client.TrinketRenderer;
import net.fabricmc.fabric.api.client.rendering.v1.ArmorRenderer;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.entity.state.HumanoidRenderState;
import net.minecraft.client.renderer.entity.state.LivingEntityRenderState;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ItemStack;

public class TrinketRendererImpl implements TrinketRenderer {

	private final ArmorRenderer renderer;

	public TrinketRendererImpl(ArmorRenderer renderer) {
		this.renderer = renderer;
	}

	@Override
	@SuppressWarnings("unchecked")
	public void submit(ItemStack stack, TrinketSlotAccess slotReference, EntityModel<? extends LivingEntityRenderState> contextModel, PoseStack matrices, SubmitNodeCollector submit, int light, LivingEntityRenderState state, float limbAngle, float limbDistance) {
		if (state instanceof HumanoidRenderState humanoidRenderState) {
			this.renderer.render(matrices, submit, stack, humanoidRenderState, EquipmentSlot.MAINHAND, light, (HumanoidModel<HumanoidRenderState>) contextModel);
		}
	}
}
