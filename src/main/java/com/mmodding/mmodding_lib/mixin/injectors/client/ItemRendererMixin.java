package com.mmodding.mmodding_lib.mixin.injectors.client;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.sugar.Local;
import com.llamalad7.mixinextras.sugar.Share;
import com.llamalad7.mixinextras.sugar.ref.LocalRef;
import com.mmodding.mmodding_lib.ducks.BakedModelManagerDuckInterface;
import com.mmodding.mmodding_lib.library.client.render.model.InventoryModels;
import com.mmodding.mmodding_lib.library.glint.client.GlintPack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.item.ItemModels;
import net.minecraft.client.render.item.ItemRenderer;
import net.minecraft.client.render.model.BakedModel;
import net.minecraft.client.render.model.json.ModelTransformation;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.concurrent.atomic.AtomicReference;

@Mixin(ItemRenderer.class)
public abstract class ItemRendererMixin {

	@Shadow
	@Final
	private ItemModels models;

	@Inject(method = "renderItem(Lnet/minecraft/item/ItemStack;Lnet/minecraft/client/render/model/json/ModelTransformation$Mode;ZLnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumerProvider;IILnet/minecraft/client/render/model/BakedModel;)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/render/model/BakedModel;getTransformation()Lnet/minecraft/client/render/model/json/ModelTransformation;"))
	private void wrapHeldModels(ItemStack stack, ModelTransformation.Mode renderMode, boolean leftHanded, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, int overlay, BakedModel model, CallbackInfo ci, @Local(argsOnly = true) LocalRef<BakedModel> mutableModel) {
		Identifier identifier = InventoryModels.EVENT.invoker().getModelForStack(stack);
		if (identifier != null) {
			mutableModel.set(((BakedModelManagerDuckInterface) this.models.getModelManager()).mmodding_lib$getModel(identifier));
		}
	}

    @Inject(method = "renderItem(Lnet/minecraft/item/ItemStack;Lnet/minecraft/client/render/model/json/ModelTransformation$Mode;ZLnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumerProvider;IILnet/minecraft/client/render/model/BakedModel;)V", at = @At("HEAD"))
    private void hook(ItemStack stack, ModelTransformation.Mode renderMode, boolean leftHanded, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, int overlay, BakedModel model, CallbackInfo ci, @Share("itemStack") LocalRef<ItemStack> ref) {
        ref.set(stack);
    }

    @WrapOperation(method = "renderItem(Lnet/minecraft/item/ItemStack;Lnet/minecraft/client/render/model/json/ModelTransformation$Mode;ZLnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumerProvider;IILnet/minecraft/client/render/model/BakedModel;)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/render/item/ItemRenderer;getDirectCompassGlintConsumer(Lnet/minecraft/client/render/VertexConsumerProvider;Lnet/minecraft/client/render/RenderLayer;Lnet/minecraft/client/util/math/MatrixStack$Entry;)Lcom/mojang/blaze3d/vertex/VertexConsumer;"))
    private VertexConsumer redirectDirectCompassConsumer(VertexConsumerProvider provider, RenderLayer layer, MatrixStack.Entry entry, Operation<VertexConsumer> original, @Share("itemStack") LocalRef<ItemStack> ref) {
		AtomicReference<VertexConsumer> value = new AtomicReference<>();
		GlintPack.of(ref.get()).ifPresentOrElse(
			glintPack -> value.set(glintPack.getDirectCompassConsumer(provider, layer, entry)),
			() -> value.set(original.call(provider, layer, entry))
		);
		return value.get();
    }

    @WrapOperation(method = "renderItem(Lnet/minecraft/item/ItemStack;Lnet/minecraft/client/render/model/json/ModelTransformation$Mode;ZLnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumerProvider;IILnet/minecraft/client/render/model/BakedModel;)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/render/item/ItemRenderer;getCompassGlintConsumer(Lnet/minecraft/client/render/VertexConsumerProvider;Lnet/minecraft/client/render/RenderLayer;Lnet/minecraft/client/util/math/MatrixStack$Entry;)Lcom/mojang/blaze3d/vertex/VertexConsumer;"))
    private VertexConsumer redirectCompassConsumer(VertexConsumerProvider provider, RenderLayer layer, MatrixStack.Entry entry, Operation<VertexConsumer> original, @Share("itemStack") LocalRef<ItemStack> ref) {
        AtomicReference<VertexConsumer> value = new AtomicReference<>();
		GlintPack.of(ref.get()).ifPresentOrElse(
			glintPack -> value.set(glintPack.getCompassConsumer(provider, layer, entry)),
			() -> value.set(original.call(provider, layer, entry))
		);
		return value.get();
    }

    @WrapOperation(method = "renderItem(Lnet/minecraft/item/ItemStack;Lnet/minecraft/client/render/model/json/ModelTransformation$Mode;ZLnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumerProvider;IILnet/minecraft/client/render/model/BakedModel;)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/render/item/ItemRenderer;getDirectItemGlintConsumer(Lnet/minecraft/client/render/VertexConsumerProvider;Lnet/minecraft/client/render/RenderLayer;ZZ)Lcom/mojang/blaze3d/vertex/VertexConsumer;"))
    private VertexConsumer redirectDirectItemConsumer(VertexConsumerProvider provider, RenderLayer layer, boolean solid, boolean glint, Operation<VertexConsumer> original, @Share("itemStack") LocalRef<ItemStack> ref) {
        AtomicReference<VertexConsumer> value = new AtomicReference<>();
		GlintPack.of(ref.get()).ifPresentOrElse(
			glintPack -> value.set(glintPack.getDirectItemConsumer(provider, layer, solid, glint)),
			() -> value.set(original.call(provider, layer, solid, glint))
		);
		return value.get();
    }

    @WrapOperation(method = "renderItem(Lnet/minecraft/item/ItemStack;Lnet/minecraft/client/render/model/json/ModelTransformation$Mode;ZLnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumerProvider;IILnet/minecraft/client/render/model/BakedModel;)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/render/item/ItemRenderer;getItemGlintConsumer(Lnet/minecraft/client/render/VertexConsumerProvider;Lnet/minecraft/client/render/RenderLayer;ZZ)Lcom/mojang/blaze3d/vertex/VertexConsumer;"))
    private VertexConsumer changeItemConsumer(VertexConsumerProvider provider, RenderLayer layer, boolean solid, boolean glint, Operation<VertexConsumer> original, @Share("itemStack") LocalRef<ItemStack> ref) {
        AtomicReference<VertexConsumer> value = new AtomicReference<>();
		GlintPack.of(ref.get()).ifPresentOrElse(
			glintPack -> value.set(glintPack.getItemConsumer(provider, layer, solid, glint)),
			() -> value.set(original.call(provider, layer, solid, glint))
		);
		return value.get();
    }
}
