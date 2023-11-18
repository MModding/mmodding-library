package com.mmodding.mmodding_lib.mixin.injectors.client;

import com.mmodding.mmodding_lib.library.glint.GlintPackView;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.fabricmc.fabric.api.client.rendering.v1.ArmorRenderer;
import net.minecraft.client.model.Model;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = ArmorRenderer.class, remap = false)
public interface ArmorRendererMixin {

    @Inject(method = "renderPart", at = @At(value = "HEAD"), cancellable = true)
    private static void renderParts(MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, ItemStack stack, Model model, Identifier texture, CallbackInfo ci) {
        if (GlintPackView.of(stack.getItem()) != null) {
            VertexConsumer vertexConsumer = GlintPackView.of(stack.getItem()).getGlintPack(stack).getArmorConsumer(
                vertexConsumers,
                RenderLayer.getArmorCutoutNoCull(texture),
                false,
                stack.hasGlint()
            );
            model.render(matrices, vertexConsumer, light, OverlayTexture.DEFAULT_UV, 1, 1, 1, 1);
            ci.cancel();
        }
    }
}
