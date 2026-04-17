package com.mmodding.library.rendering.api.renderer;

import com.mmodding.library.rendering.api.AccessoryInfo;
import com.mojang.blaze3d.vertex.PoseStack;
import net.fabricmc.fabric.api.client.rendering.v1.ArmorRenderer;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.client.renderer.entity.state.HumanoidRenderState;
import net.minecraft.client.renderer.rendertype.RenderType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.Vec3;

/**
 * Rendering {@link AccessoryInfo} stuff as a cap-like accessory.
 */
public class CapModelRenderer implements ArmorRenderer {

	private final AccessoryInfo info;
	private final EntityModel<HumanoidRenderState> model;

	public CapModelRenderer(AccessoryInfo info, EntityRendererProvider.Context context) {
		this.info = info;
		this.model = info.getModel(context);
	}

	@Override
	public void render(PoseStack poseStack, SubmitNodeCollector submitNodeCollector, ItemStack stack, HumanoidRenderState state, EquipmentSlot slot, int light, HumanoidModel<HumanoidRenderState> contextModel) {
		RenderType renderType = this.model.renderType(this.info.getTexture());
		poseStack.pushPose();
		contextModel.root().translateAndRotate(poseStack);
		contextModel.translateToHead(poseStack);
		poseStack.translate(Vec3.Y_AXIS.scale(-state.eyeHeight));
		int overlayCoords = LivingEntityRenderer.getOverlayCoords(state, 0.0f);
		submitNodeCollector.submitModel(this.model, state, poseStack, renderType, state.lightCoords, overlayCoords, state.outlineColor, null);
		poseStack.popPose();
	}

	@Override
	public boolean shouldRenderDefaultHeadItem(LivingEntity entity, ItemStack stack) {
		return false;
	}
}
