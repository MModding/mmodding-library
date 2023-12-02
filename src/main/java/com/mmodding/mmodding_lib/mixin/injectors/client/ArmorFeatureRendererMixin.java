package com.mmodding.mmodding_lib.mixin.injectors.client;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.sugar.Share;
import com.llamalad7.mixinextras.sugar.ref.LocalRef;
import com.mmodding.mmodding_lib.library.glint.client.GlintPack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.feature.ArmorFeatureRenderer;
import net.minecraft.client.render.entity.model.BipedEntityModel;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
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

	@WrapOperation(method = "renderArmor", at = @At(value = "INVOKE", target = "Lnet/minecraft/item/ItemStack;getItem()Lnet/minecraft/item/Item;", ordinal = 0))
	private Item renderArmor(ItemStack stack, Operation<Item> original, @Share("itemStack") LocalRef<ItemStack> ref) {
		ref.set(stack);
		return original.call(stack);
	}

	@Inject(method = "renderArmorParts", at = @At(value = "HEAD"), cancellable = true)
	private void renderArmorParts(MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, ArmorItem item, boolean usesSecondLayer, A model, boolean legs, float red, float green, float blue, @Nullable String overlay, CallbackInfo ci, @Share("itemStack") LocalRef<ItemStack> ref) {
		GlintPack.of(ref.get()).ifPresent(glintPack -> {
			VertexConsumer vertexConsumer = glintPack.getArmorConsumer(
				vertexConsumers,
				RenderLayer.getArmorCutoutNoCull(this.getArmorTexture(item, legs, overlay)),
				false,
				usesSecondLayer
			);
			model.render(matrices, vertexConsumer, light, OverlayTexture.DEFAULT_UV, red, green, blue, 1.0f);
			ci.cancel();
		});
	}
}
