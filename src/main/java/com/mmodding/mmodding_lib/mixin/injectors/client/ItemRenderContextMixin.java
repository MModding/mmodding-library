package com.mmodding.mmodding_lib.mixin.injectors.client;

import com.mmodding.mmodding_lib.library.glint.GlintPackView;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.fabricmc.fabric.api.renderer.v1.material.BlendMode;
import net.fabricmc.fabric.impl.client.indigo.renderer.render.ItemRenderContext;
import net.minecraft.client.render.TexturedRenderLayers;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(value = ItemRenderContext.class, remap = false)
public class ItemRenderContextMixin {

	@Shadow
	private ItemStack itemStack;

	@Shadow
	private VertexConsumerProvider vertexConsumerProvider;

	@Shadow
	private VertexConsumer translucentVertexConsumer;

	@Shadow
	private VertexConsumer cutoutVertexConsumer;

	@Inject(method = "quadVertexConsumer", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/render/item/ItemRenderer;getItemGlintConsumer(Lnet/minecraft/client/render/VertexConsumerProvider;Lnet/minecraft/client/render/RenderLayer;ZZ)Lcom/mojang/blaze3d/vertex/VertexConsumer;", ordinal = 0), cancellable = true)
	private void changeFirstItemConsumer(BlendMode blendMode, CallbackInfoReturnable<VertexConsumer> cir) {
		if (GlintPackView.of(this.itemStack.getItem()) != null) {
			this.translucentVertexConsumer = GlintPackView.of(this.itemStack.getItem()).getGlintPack(this.itemStack).getItemConsumer(
				this.vertexConsumerProvider,
				TexturedRenderLayers.getItemEntityTranslucentCull(),
				true,
				this.itemStack.hasGlint()
			);
			cir.setReturnValue(this.translucentVertexConsumer);
		}
	}

	@Inject(method = "quadVertexConsumer", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/render/item/ItemRenderer;getItemGlintConsumer(Lnet/minecraft/client/render/VertexConsumerProvider;Lnet/minecraft/client/render/RenderLayer;ZZ)Lcom/mojang/blaze3d/vertex/VertexConsumer;", ordinal = 1), cancellable = true)
	private void changeSecondItemConsumer(BlendMode blendMode, CallbackInfoReturnable<VertexConsumer> cir) {
		if (GlintPackView.of(this.itemStack.getItem()) != null) {
			this.translucentVertexConsumer = GlintPackView.of(this.itemStack.getItem()).getGlintPack(this.itemStack).getItemConsumer(
				this.vertexConsumerProvider,
				TexturedRenderLayers.getEntityTranslucentCull(),
				true,
				this.itemStack.hasGlint()
			);
			cir.setReturnValue(this.translucentVertexConsumer);
		}
	}

	@Inject(method = "quadVertexConsumer", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/render/item/ItemRenderer;getDirectItemGlintConsumer(Lnet/minecraft/client/render/VertexConsumerProvider;Lnet/minecraft/client/render/RenderLayer;ZZ)Lcom/mojang/blaze3d/vertex/VertexConsumer;", ordinal = 0), cancellable = true)
	private void changeFirstDirectItemConsumer(BlendMode blendMode, CallbackInfoReturnable<VertexConsumer> cir) {
		if (GlintPackView.of(this.itemStack.getItem()) != null) {
			this.translucentVertexConsumer = GlintPackView.of(this.itemStack.getItem()).getGlintPack(this.itemStack).getDirectItemConsumer(
				this.vertexConsumerProvider,
				TexturedRenderLayers.getEntityTranslucentCull(),
				true,
				this.itemStack.hasGlint()
			);
			cir.setReturnValue(this.translucentVertexConsumer);
		}
	}

	@Inject(method = "quadVertexConsumer", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/render/item/ItemRenderer;getDirectItemGlintConsumer(Lnet/minecraft/client/render/VertexConsumerProvider;Lnet/minecraft/client/render/RenderLayer;ZZ)Lcom/mojang/blaze3d/vertex/VertexConsumer;", ordinal = 1), cancellable = true)
	private void changeSecondDirectItemConsumer(BlendMode blendMode, CallbackInfoReturnable<VertexConsumer> cir) {
		if (GlintPackView.of(this.itemStack.getItem()) != null) {
			this.cutoutVertexConsumer = GlintPackView.of(this.itemStack.getItem()).getGlintPack(this.itemStack).getDirectItemConsumer(
				this.vertexConsumerProvider,
				TexturedRenderLayers.getEntityCutout(),
				true,
				this.itemStack.hasGlint()
			);
			cir.setReturnValue(this.cutoutVertexConsumer);
		}
	}
}
