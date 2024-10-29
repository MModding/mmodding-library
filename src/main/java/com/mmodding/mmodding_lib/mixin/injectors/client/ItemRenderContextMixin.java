package com.mmodding.mmodding_lib.mixin.injectors.client;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.mmodding.mmodding_lib.library.glint.client.GlintPack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.fabricmc.fabric.impl.client.indigo.renderer.render.ItemRenderContext;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;

import java.util.Optional;

@Mixin(value = ItemRenderContext.class)
public class ItemRenderContextMixin {

	@Shadow
	private ItemStack itemStack;

	@WrapOperation(method = "quadVertexConsumer", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/render/item/ItemRenderer;getItemGlintConsumer(Lnet/minecraft/client/render/VertexConsumerProvider;Lnet/minecraft/client/render/RenderLayer;ZZ)Lcom/mojang/blaze3d/vertex/VertexConsumer;"))
	private VertexConsumer changeItemConsumer(VertexConsumerProvider vertexConsumers, RenderLayer layer, boolean solid, boolean glint, Operation<VertexConsumer> original) {
		Optional<GlintPack> glintPack = GlintPack.of(this.itemStack);
		if (glintPack.isPresent()) {
			return glintPack.get().getItemConsumer(vertexConsumers, layer, solid, glint);
		}
		else {
			return original.call(vertexConsumers, layer, solid, glint);
		}
	}

	@WrapOperation(method = "quadVertexConsumer", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/render/item/ItemRenderer;getDirectItemGlintConsumer(Lnet/minecraft/client/render/VertexConsumerProvider;Lnet/minecraft/client/render/RenderLayer;ZZ)Lcom/mojang/blaze3d/vertex/VertexConsumer;"))
	private VertexConsumer changeDirectItemConsumer(VertexConsumerProvider provider, RenderLayer layer, boolean solid, boolean glint, Operation<VertexConsumer> original) {
		Optional<GlintPack> glintPack = GlintPack.of(this.itemStack);
		if (glintPack.isPresent()) {
			return glintPack.get().getDirectItemConsumer(provider, layer, solid, glint);
		}
		else {
			return original.call(provider, layer, solid, glint);
		}
	}
}
