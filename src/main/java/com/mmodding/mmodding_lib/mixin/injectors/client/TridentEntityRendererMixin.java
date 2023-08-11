package com.mmodding.mmodding_lib.mixin.injectors.client;

import com.mmodding.mmodding_lib.library.glint.GlintPackView;
import com.mmodding.mmodding_lib.mixin.accessors.TridentEntityAccessor;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.TridentEntityRenderer;
import net.minecraft.client.render.entity.model.TridentEntityModel;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.projectile.TridentEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(TridentEntityRenderer.class)
public abstract class TridentEntityRendererMixin extends EntityRendererMixin<TridentEntity> {

    @Shadow
    @Final
    private TridentEntityModel model;

    @Shadow
    public abstract Identifier getTexture(TridentEntity tridentEntity);

    @Inject(method = "render(Lnet/minecraft/entity/projectile/TridentEntity;FFLnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumerProvider;I)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/render/item/ItemRenderer;getDirectItemGlintConsumer(Lnet/minecraft/client/render/VertexConsumerProvider;Lnet/minecraft/client/render/RenderLayer;ZZ)Lcom/mojang/blaze3d/vertex/VertexConsumer;"), cancellable = true)
    private void render(TridentEntity tridentEntity, float f, float g, MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider, int i, CallbackInfo ci) {
        ItemStack stack = ((TridentEntityAccessor) tridentEntity).getTridentStack();

        if (GlintPackView.ofStack(stack) != null) {
            VertexConsumer vertexConsumer = GlintPackView.ofStack(stack).getGlintPack().getDirectItemConsumer(
                vertexConsumerProvider,
                this.model.getLayer(this.getTexture(tridentEntity)),
                false,
                tridentEntity.isEnchanted()
            );

            this.model.render(matrixStack, vertexConsumer, i, OverlayTexture.DEFAULT_UV, 1.0F, 1.0F, 1.0F, 1.0F);

            matrixStack.pop();
            super.render(tridentEntity, f, g, matrixStack, vertexConsumerProvider, i);
            ci.cancel();
        }
    }
}
