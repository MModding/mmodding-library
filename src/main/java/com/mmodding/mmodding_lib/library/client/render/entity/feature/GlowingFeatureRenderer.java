package com.mmodding.mmodding_lib.library.client.render.entity.feature;

import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.feature.FeatureRenderer;
import net.minecraft.client.render.entity.feature.FeatureRendererContext;
import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.Entity;
import org.quiltmc.loader.api.minecraft.ClientOnly;

@ClientOnly
public abstract class GlowingFeatureRenderer<T extends Entity, M extends EntityModel<T>> extends FeatureRenderer<T, M> {

	public GlowingFeatureRenderer(FeatureRendererContext<T, M> context) {
		super(context);
	}

	@Override
	public void render(MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, T entity, float limbAngle, float limbDistance, float tickDelta, float animationProgress, float headYaw, float headPitch) {
		this.getContextModel().render(matrices, vertexConsumers.getBuffer(this.getGlowingLayer(entity)), 15728640, OverlayTexture.DEFAULT_UV, 1.0f, 1.0f, 1.0f, 1.0f);
	}

	public abstract RenderLayer getGlowingLayer(T entity);
}
