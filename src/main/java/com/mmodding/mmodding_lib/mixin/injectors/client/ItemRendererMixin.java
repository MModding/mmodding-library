package com.mmodding.mmodding_lib.mixin.injectors.client;

import com.llamalad7.mixinextras.sugar.Share;
import com.llamalad7.mixinextras.sugar.ref.LocalRef;
import com.mmodding.mmodding_lib.library.glint.GlintPackView;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.item.ItemRenderer;
import net.minecraft.client.render.model.BakedModel;
import net.minecraft.client.render.model.json.ModelTransformation;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ItemRenderer.class)
public abstract class ItemRendererMixin {

    @Inject(method = "renderItem(Lnet/minecraft/item/ItemStack;Lnet/minecraft/client/render/model/json/ModelTransformation$Mode;ZLnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumerProvider;IILnet/minecraft/client/render/model/BakedModel;)V", at = @At("HEAD"))
    private void hook(ItemStack stack, ModelTransformation.Mode renderMode, boolean leftHanded, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, int overlay, BakedModel model, CallbackInfo ci, @Share("itemStack") LocalRef<ItemStack> ref) {
        ref.set(stack);
    }

    @Redirect(method = "renderItem(Lnet/minecraft/item/ItemStack;Lnet/minecraft/client/render/model/json/ModelTransformation$Mode;ZLnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumerProvider;IILnet/minecraft/client/render/model/BakedModel;)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/render/item/ItemRenderer;getDirectCompassGlintConsumer(Lnet/minecraft/client/render/VertexConsumerProvider;Lnet/minecraft/client/render/RenderLayer;Lnet/minecraft/client/util/math/MatrixStack$Entry;)Lcom/mojang/blaze3d/vertex/VertexConsumer;"))
    private VertexConsumer redirectDirectCompassConsumer(VertexConsumerProvider provider, RenderLayer layer, MatrixStack.Entry entry, @Share("itemStack") LocalRef<ItemStack> ref) {
        if (GlintPackView.of(ref.get().getItem()) != null) {
            return GlintPackView.of(ref.get().getItem()).getGlintPack(ref.get()).getDirectCompassConsumer(provider, layer, entry);
        }
        else {
            return ItemRenderer.getDirectCompassGlintConsumer(provider, layer, entry);
        }
    }

    @Redirect(method = "renderItem(Lnet/minecraft/item/ItemStack;Lnet/minecraft/client/render/model/json/ModelTransformation$Mode;ZLnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumerProvider;IILnet/minecraft/client/render/model/BakedModel;)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/render/item/ItemRenderer;getCompassGlintConsumer(Lnet/minecraft/client/render/VertexConsumerProvider;Lnet/minecraft/client/render/RenderLayer;Lnet/minecraft/client/util/math/MatrixStack$Entry;)Lcom/mojang/blaze3d/vertex/VertexConsumer;"))
    private VertexConsumer redirectCompassConsumer(VertexConsumerProvider provider, RenderLayer layer, MatrixStack.Entry entry, @Share("itemStack") LocalRef<ItemStack> ref) {
        if (GlintPackView.of(ref.get().getItem()) != null) {
            return GlintPackView.of(ref.get().getItem()).getGlintPack(ref.get()).getCompassConsumer(provider, layer, entry);
        }
        else {
            return ItemRenderer.getCompassGlintConsumer(provider, layer, entry);
        }
    }

    @Redirect(method = "renderItem(Lnet/minecraft/item/ItemStack;Lnet/minecraft/client/render/model/json/ModelTransformation$Mode;ZLnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumerProvider;IILnet/minecraft/client/render/model/BakedModel;)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/render/item/ItemRenderer;getDirectItemGlintConsumer(Lnet/minecraft/client/render/VertexConsumerProvider;Lnet/minecraft/client/render/RenderLayer;ZZ)Lcom/mojang/blaze3d/vertex/VertexConsumer;"))
    private VertexConsumer redirectDirectItemConsumer(VertexConsumerProvider provider, RenderLayer layer, boolean solid, boolean glint, @Share("itemStack") LocalRef<ItemStack> ref) {
        if (GlintPackView.of(ref.get().getItem()) != null) {
            return GlintPackView.of(ref.get().getItem()).getGlintPack(ref.get()).getDirectItemConsumer(provider, layer, solid, glint);
        }
        else {
            return ItemRenderer.getDirectItemGlintConsumer(provider, layer, solid, glint);
        }
    }

    @Redirect(method = "renderItem(Lnet/minecraft/item/ItemStack;Lnet/minecraft/client/render/model/json/ModelTransformation$Mode;ZLnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumerProvider;IILnet/minecraft/client/render/model/BakedModel;)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/render/item/ItemRenderer;getItemGlintConsumer(Lnet/minecraft/client/render/VertexConsumerProvider;Lnet/minecraft/client/render/RenderLayer;ZZ)Lcom/mojang/blaze3d/vertex/VertexConsumer;"))
    private VertexConsumer changeItemConsumer(VertexConsumerProvider provider, RenderLayer layer, boolean solid, boolean glint, @Share("itemStack") LocalRef<ItemStack> ref) {
        if (GlintPackView.of(ref.get().getItem()) != null) {
            return GlintPackView.of(ref.get().getItem()).getGlintPack(ref.get()).getItemConsumer(provider, layer, solid, glint);
        }
        else {
            return ItemRenderer.getItemGlintConsumer(provider, layer, solid, glint);
        }
    }
}
