package com.mmodding.library.rendering.api.cosmetic.renderer;

import com.mmodding.library.rendering.api.cosmetic.Cosmetic;
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

/**
 * Rendering {@link Cosmetic} stuff as a pants-like.
 * <br>A pants model should have three named parts: <code>junction</code>, <code>left_legging</code> and <code>right_legging</code>.
 * <br>But, the model can actually miss either <code>junction</code> or both <code>left_legging</code> and <code>right_legging</code> if you only want the junction or the leggings to render.
 */
public class PantsCosmeticRenderer extends CosmeticRenderer {

	public PantsCosmeticRenderer(Cosmetic cosmetic, EntityRendererProvider.Context context) {
		super(cosmetic, context);
	}

	@Override
	public void render(PoseStack poseStack, SubmitNodeCollector submitNodeCollector, ItemStack stack, HumanoidRenderState state, EquipmentSlot slot, int light, HumanoidModel<HumanoidRenderState> contextModel) {
		EntityModel<HumanoidRenderState> modelToRender = this.getModel(stack, this.isSlim(state));
		RenderType renderType = modelToRender.renderType(this.getTexture(stack, this.isSlim(state)));
		EntityModel<HumanoidRenderState> junctionModel = this.getSubModel(modelToRender, "junction");
		EntityModel<HumanoidRenderState> leftLeggingModel = this.getSubModel(modelToRender, "left_legging");
		EntityModel<HumanoidRenderState> rightLeggingModel = this.getSubModel(modelToRender, "right_legging");
		if (!(junctionModel != null || (leftLeggingModel != null && rightLeggingModel != null))) {
			throw new IllegalStateException("Provided model to PantsCosmeticRenderer does not match requirements");
		}
		int overlayCoords = LivingEntityRenderer.getOverlayCoords(state, 0.0f);
		poseStack.pushPose();
		contextModel.root().translateAndRotate(poseStack);
		modelToRender.root().translateAndRotate(poseStack); // Applying root model transformations.
		if (junctionModel != null) {
			poseStack.pushPose();
			contextModel.body.translateAndRotate(poseStack);
			poseStack.translate(0.0f, -0.375f, 0.0f);
			submitNodeCollector.submitModel(junctionModel, state, poseStack, renderType, state.lightCoords, overlayCoords, state.outlineColor, null);
			poseStack.popPose();
		}
		if (leftLeggingModel != null && rightLeggingModel != null) {
			poseStack.pushPose();
			contextModel.leftLeg.translateAndRotate(poseStack);
			poseStack.translate(0.0f, -0.75f, 0.0f); // Standard from: https://github.com/Patbox/trinkets/blob/6277184520132e1195b899057f8d7b56a15f4e9c/src/main/java/eu/pb4/trinkets/api/client/TrinketRenderer.java#L165.
			submitNodeCollector.submitModel(leftLeggingModel, state, poseStack, renderType, state.lightCoords, overlayCoords, state.outlineColor, null);
			poseStack.popPose();
			poseStack.pushPose();
			contextModel.rightLeg.translateAndRotate(poseStack);
			poseStack.translate(-0.0f, -0.75f, 0.0f); // Standard from: https://github.com/Patbox/trinkets/blob/6277184520132e1195b899057f8d7b56a15f4e9c/src/main/java/eu/pb4/trinkets/api/client/TrinketRenderer.java#L155.
			submitNodeCollector.submitModel(rightLeggingModel, state, poseStack, renderType, state.lightCoords, overlayCoords, state.outlineColor, null);
			poseStack.popPose();
		}
		poseStack.popPose();
	}
}
