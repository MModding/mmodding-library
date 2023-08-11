package com.mmodding.mmodding_lib.mixin.injectors.client;

import com.mmodding.mmodding_lib.glint.GlintPackView;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.item.ItemRenderer;
import net.minecraft.client.render.model.BakedModel;
import net.minecraft.client.render.model.json.ModelTransformation;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ItemRenderer.class)
public abstract class ItemRendererMixin {

    @Unique
    private ItemStack stack;

    @Inject(method = "renderItem(Lnet/minecraft/item/ItemStack;Lnet/minecraft/client/render/model/json/ModelTransformation$Mode;ZLnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumerProvider;IILnet/minecraft/client/render/model/BakedModel;)V", at = @At("HEAD"))
    private void hook(ItemStack stack, ModelTransformation.Mode renderMode, boolean leftHanded, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, int overlay, BakedModel model, CallbackInfo ci) {
        this.stack = stack;
    }

    @Redirect(method = "renderItem(Lnet/minecraft/item/ItemStack;Lnet/minecraft/client/render/model/json/ModelTransformation$Mode;ZLnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumerProvider;IILnet/minecraft/client/render/model/BakedModel;)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/render/item/ItemRenderer;getDirectCompassGlintConsumer(Lnet/minecraft/client/render/VertexConsumerProvider;Lnet/minecraft/client/render/RenderLayer;Lnet/minecraft/client/util/math/MatrixStack$Entry;)Lcom/mojang/blaze3d/vertex/VertexConsumer;"))
    private VertexConsumer redirectDirectCompassConsumer(VertexConsumerProvider provider, RenderLayer layer, MatrixStack.Entry entry) {
        if (GlintPackView.ofStack(this.stack) != null) {
            return GlintPackView.ofStack(this.stack).getGlintPack().getDirectCompassConsumer(provider, layer, entry);
        }
        else {
            return ItemRenderer.getDirectCompassGlintConsumer(provider, layer, entry);
        }
    }

    @Redirect(method = "renderItem(Lnet/minecraft/item/ItemStack;Lnet/minecraft/client/render/model/json/ModelTransformation$Mode;ZLnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumerProvider;IILnet/minecraft/client/render/model/BakedModel;)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/render/item/ItemRenderer;getCompassGlintConsumer(Lnet/minecraft/client/render/VertexConsumerProvider;Lnet/minecraft/client/render/RenderLayer;Lnet/minecraft/client/util/math/MatrixStack$Entry;)Lcom/mojang/blaze3d/vertex/VertexConsumer;"))
    private VertexConsumer redirectCompassConsumer(VertexConsumerProvider provider, RenderLayer layer, MatrixStack.Entry entry) {
        if (GlintPackView.ofStack(this.stack) != null) {
            return GlintPackView.ofStack(this.stack).getGlintPack().getCompassConsumer(provider, layer, entry);
        }
        else {
            return ItemRenderer.getCompassGlintConsumer(provider, layer, entry);
        }
    }

    @Redirect(method = "renderItem(Lnet/minecraft/item/ItemStack;Lnet/minecraft/client/render/model/json/ModelTransformation$Mode;ZLnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumerProvider;IILnet/minecraft/client/render/model/BakedModel;)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/render/item/ItemRenderer;getDirectItemGlintConsumer(Lnet/minecraft/client/render/VertexConsumerProvider;Lnet/minecraft/client/render/RenderLayer;ZZ)Lcom/mojang/blaze3d/vertex/VertexConsumer;"))
    private VertexConsumer redirectDirectItemConsumer(VertexConsumerProvider provider, RenderLayer layer, boolean solid, boolean glint) {
        if (GlintPackView.ofStack(this.stack) != null) {
            return GlintPackView.ofStack(this.stack).getGlintPack().getDirectItemConsumer(provider, layer, solid, glint);
        }
        else {
            return ItemRenderer.getDirectItemGlintConsumer(provider, layer, solid, glint);
        }
    }

    @Redirect(method = "renderItem(Lnet/minecraft/item/ItemStack;Lnet/minecraft/client/render/model/json/ModelTransformation$Mode;ZLnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumerProvider;IILnet/minecraft/client/render/model/BakedModel;)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/render/item/ItemRenderer;getItemGlintConsumer(Lnet/minecraft/client/render/VertexConsumerProvider;Lnet/minecraft/client/render/RenderLayer;ZZ)Lcom/mojang/blaze3d/vertex/VertexConsumer;"))
    private VertexConsumer changeItemConsumer(VertexConsumerProvider provider, RenderLayer layer, boolean solid, boolean glint) {
        if (GlintPackView.ofStack(this.stack) != null) {
            return GlintPackView.ofStack(this.stack).getGlintPack().getItemConsumer(provider, layer, solid, glint);
        }
        else {
            return ItemRenderer.getItemGlintConsumer(provider, layer, solid, glint);
        }
    }
}
