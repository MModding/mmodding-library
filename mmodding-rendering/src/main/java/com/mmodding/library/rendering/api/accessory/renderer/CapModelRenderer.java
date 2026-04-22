package com.mmodding.library.rendering.api.accessory.renderer;

import com.mmodding.library.rendering.api.accessory.Accessory;
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
import net.minecraft.world.phys.Vec3;

/**
 * Rendering {@link Accessory} stuff as a cap-like.
 */
public class CapModelRenderer extends AccessoryRenderer {

	private final Anchor anchor;

	public CapModelRenderer(Accessory accessory, Anchor anchor, EntityRendererProvider.Context context) {
		super(accessory, context);
		this.anchor = anchor;
	}

	@Override
	public void render(PoseStack poseStack, SubmitNodeCollector submitNodeCollector, ItemStack stack, HumanoidRenderState state, EquipmentSlot slot, int light, HumanoidModel<HumanoidRenderState> contextModel) {
		EntityModel<HumanoidRenderState> modelToRender = this.getModel(stack, this.isSlim(state));
		RenderType renderType = modelToRender.renderType(this.getTexture(stack, this.isSlim(state)));
		poseStack.pushPose();
		contextModel.root().translateAndRotate(poseStack);
		contextModel.translateToHead(poseStack);
		// this is for the player's head; would be good if I could make that for every mob
		// Constants borrowed from https://github.com/Patbox/trinkets/blob/e8493dea30e3b1e542f9063cd00dd0ecc9ec4565/src/main/java/eu/pb4/trinkets/api/client/TrinketRenderer.java#L76 to make this.
		float headSizeY = 0.5f; // 8 px / 16 px
		float headSizeZ = 0.5f; // 8 px / 16 px
		switch (this.anchor) {
			case HEAD_CENTER -> poseStack.translate(0.0f, headSizeY * 0.5f, 0.0f);
			case HEAD_TOP -> poseStack.translate(0.0f, headSizeY * -0.5f, 0.0f);
			case HEAD_FACE -> poseStack.translate(0.0f, headSizeY * 0.5f, headSizeZ * -0.6f);
		}
		// state.eyeHeight changes with the pose, but the translation we want is always the standard eye height
		poseStack.translate(Vec3.Y_AXIS.scale(-state.entityType.getDimensions().eyeHeight()));
		int overlayCoords = LivingEntityRenderer.getOverlayCoords(state, 0.0f);
		submitNodeCollector.submitModel(modelToRender, state, poseStack, renderType, state.lightCoords, overlayCoords, state.outlineColor, null);
		poseStack.popPose();
	}

	public enum Anchor {
		HEAD_CENTER,
		HEAD_TOP,
		HEAD_FACE
	}
}
