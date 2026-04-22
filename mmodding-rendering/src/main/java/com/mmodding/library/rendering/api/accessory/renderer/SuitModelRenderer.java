package com.mmodding.library.rendering.api.accessory.renderer;

import com.mmodding.library.rendering.api.accessory.Accessory;
import com.mmodding.library.rendering.api.model.SimpleEntityModel;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.client.renderer.entity.state.HumanoidRenderState;
import net.minecraft.client.renderer.rendertype.RenderType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.Nullable;

/**
 * Rendering {@link Accessory} stuff as a suit-like.
 * <br>A suit model should have three named parts: <code>body</code>, <code>left_sleeve</code> and <code>right_sleeve</code>.
 * <br>But, the model can actually miss either <code>body</code> or both <code>left_sleeve</code> and <code>right_sleeve</code> if you only want the body or the sleeves to render.
 */
public class SuitModelRenderer extends AccessoryRenderer {

	public SuitModelRenderer(Accessory accessory, EntityRendererProvider.Context context) {
		super(accessory, context);
	}

	@Override
	public void render(PoseStack poseStack, SubmitNodeCollector submitNodeCollector, ItemStack stack, HumanoidRenderState state, EquipmentSlot slot, int light, HumanoidModel<HumanoidRenderState> contextModel) {
		EntityModel<HumanoidRenderState> modelToRender = this.getModel(stack, this.isSlim(state));
		RenderType renderType = modelToRender.renderType(this.getTexture(stack, this.isSlim(state)));
		EntityModel<HumanoidRenderState> bodyModel = this.subModel(modelToRender, "body");
		EntityModel<HumanoidRenderState> leftSleeveModel = this.subModel(modelToRender, "left_sleeve");
		EntityModel<HumanoidRenderState> rightSleeveModel = this.subModel(modelToRender, "right_sleeve");
		if (!(bodyModel != null || (leftSleeveModel != null && rightSleeveModel != null))) {
			throw new IllegalStateException("Provided model to SuitModelRenderer does not match requirements");
		}
		int overlayCoords = LivingEntityRenderer.getOverlayCoords(state, 0.0f);
		poseStack.pushPose();
		modelToRender.root().translateAndRotate(poseStack); // Applying root model transformations.
		if (bodyModel != null) {
			poseStack.pushPose();
			contextModel.root().translateAndRotate(poseStack);
			contextModel.body.translateAndRotate(poseStack);
			poseStack.translate(0.0f, -0.8125f, 0.0f);
			submitNodeCollector.submitModel(bodyModel, state, poseStack, renderType, state.lightCoords, overlayCoords, state.outlineColor, null);
			poseStack.popPose();
		}
		if (leftSleeveModel != null && rightSleeveModel != null) {
			poseStack.pushPose();
			contextModel.translateToHand(state, HumanoidArm.LEFT, poseStack);
			poseStack.translate(0.0625f, -0.875f, 0.0f);
			submitNodeCollector.submitModel(leftSleeveModel, state, poseStack, renderType, state.lightCoords, overlayCoords, state.outlineColor, null);
			poseStack.popPose();
			poseStack.pushPose();
			contextModel.translateToHand(state, HumanoidArm.RIGHT, poseStack);
			poseStack.translate(-0.0625f, -0.875f, 0.0f);
			submitNodeCollector.submitModel(rightSleeveModel, state, poseStack, renderType, state.lightCoords, overlayCoords, state.outlineColor, null);
			poseStack.popPose();
		}
		poseStack.popPose();
	}

	@Nullable
	private EntityModel<HumanoidRenderState> subModel(EntityModel<HumanoidRenderState> model, String sub) {
		ModelPart subPart = model.getChildPart(sub);
		return subPart != null ? new SimpleEntityModel<>(subPart) : null;
	}
}
