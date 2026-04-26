package com.mmodding.library.rendering.impl.cosmetic.renderer;

import com.mmodding.library.rendering.api.cosmetic.Cosmetic;
import com.mmodding.library.rendering.api.cosmetic.renderer.CosmeticRenderer;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.client.renderer.entity.state.HumanoidRenderState;
import net.minecraft.client.renderer.rendertype.RenderType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ItemStack;

public class ShoesCosmeticRenderer extends CosmeticRenderer {

	public ShoesCosmeticRenderer(Cosmetic cosmetic, EntityRendererProvider.Context context) {
		super(cosmetic, context);
	}

	@Override
	public void render(PoseStack poseStack, SubmitNodeCollector submitNodeCollector, ItemStack stack, HumanoidRenderState state, EquipmentSlot slot, int light, HumanoidModel<HumanoidRenderState> contextModel) {
		EntityModel<HumanoidRenderState> modelToRender = this.getModel(stack, this.isSlim(state));
		RenderType renderType = modelToRender.renderType(this.getTexture(stack, this.isSlim(state)));
		EntityModel<HumanoidRenderState> leftShoe = this.getSubModel(modelToRender, "left_shoe");
		EntityModel<HumanoidRenderState> rightShoe = this.getSubModel(modelToRender, "right_shoe");
		if (leftShoe == null && rightShoe == null) {
			throw new IllegalStateException("Provided model to ShoesCosmeticRenderer does not match requirements");
		}
		int overlayCoords = LivingEntityRenderer.getOverlayCoords(state, 0.0f);
		poseStack.pushPose();
		contextModel.root().translateAndRotate(poseStack);
		if (leftShoe != null && rightShoe != null) {
			poseStack.pushPose();
			contextModel.leftLeg.translateAndRotate(poseStack);
			poseStack.translate(-0.125f, -0.625, 0.0f);
			submitNodeCollector.submitModel(leftShoe, state, poseStack, renderType, state.lightCoords, overlayCoords, state.outlineColor, null);
			poseStack.popPose();
			poseStack.pushPose();
			contextModel.rightLeg.translateAndRotate(poseStack);
			poseStack.translate(0.125f, -0.625, 0.0f);
			submitNodeCollector.submitModel(rightShoe, state, poseStack, renderType, state.lightCoords, overlayCoords, state.outlineColor, null);
			poseStack.popPose();
		}
		poseStack.popPose();
	}
}
