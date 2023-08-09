package com.mmodding.mmodding_lib.mixin.injectors.client;

import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.feature.ArmorFeatureRenderer;
import net.minecraft.client.render.entity.model.BipedEntityModel;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ArmorItem;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ArmorFeatureRenderer.class)
public abstract class ArmorFeatureRendererMixin<T extends LivingEntity, A extends BipedEntityModel<T>> {

	@Shadow
	protected abstract Identifier getArmorTexture(ArmorItem item, boolean legs, @Nullable String overlay);

	@Inject(method = "renderArmorParts", at = @At(value = "HEAD"), cancellable = true)
	private void renderArmorParts(MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, ArmorItem item, boolean usesSecondLayer, A model, boolean legs, float red, float green, float blue, @Nullable String overlay, CallbackInfo ci) {
		if (item.getGlintPackView() != null) {
			VertexConsumer vertexConsumer = item.getGlintPackView().getGlintPack().getArmorConsumer(
				vertexConsumers,
				RenderLayer.getArmorCutoutNoCull(this.getArmorTexture(item, legs, overlay)),
				false,
				usesSecondLayer
			);
			model.render(matrices, vertexConsumer, light, OverlayTexture.DEFAULT_UV, red, green, blue, 1.0f);
			ci.cancel();
		}
	}
}
