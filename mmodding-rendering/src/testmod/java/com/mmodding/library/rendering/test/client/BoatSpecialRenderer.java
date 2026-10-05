package com.mmodding.library.rendering.test.client;

import com.mmodding.library.rendering.api.client.block.SpecialBlockRenderState;
import com.mmodding.library.rendering.api.client.block.SpecialBlockRenderer;
import com.mmodding.library.rendering.api.client.block.SpecialBlockRendererProvider;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.model.Model;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.rendertype.RenderTypes;
import net.minecraft.client.renderer.state.level.CameraRenderState;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.Identifier;

public class BoatSpecialRenderer implements SpecialBlockRenderer<SpecialBlockRenderState> {

	private final Identifier texture;
	private final Model<SpecialBlockRenderState> model;

	public BoatSpecialRenderer(SpecialBlockRendererProvider.Context context) {
		this.texture = ModelLayers.ACACIA_BOAT.model().withPath(p -> "textures/entity/" + p + ".png");
		this.model = new Model<>(context.bakeLayer(ModelLayers.ACACIA_BOAT), RenderTypes::entityCutout) {
			@Override
			public void setupAnim(SpecialBlockRenderState state) {
				super.setupAnim(state);
			}
		};
	}

	public void submit(SpecialBlockRenderState state, PoseStack poseStack, SubmitNodeCollector submitNodeCollector, CameraRenderState camera) {
		poseStack.pushPose();
		poseStack.translate(0.0F, 0.375F, 0.0F);

		poseStack.scale(-1.0F, -1.0F, 1.0F);
		poseStack.rotateDegrees(Axis.YP, 90.0F);
		submitNodeCollector.submitModel(this.model, state, poseStack, this.texture, state.lightCoords, OverlayTexture.NO_OVERLAY, 0);
		this.submitTypeAdditions(state, poseStack, submitNodeCollector, state.lightCoords);
		poseStack.popPose();
	}

	protected void submitTypeAdditions(final SpecialBlockRenderState state, final PoseStack poseStack, final SubmitNodeCollector submitNodeCollector, final int lightCoords) {
	}

	public SpecialBlockRenderState createRenderState() {
		return new SpecialBlockRenderState();
	}
}
