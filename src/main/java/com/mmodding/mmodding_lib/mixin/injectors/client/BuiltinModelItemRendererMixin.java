package com.mmodding.mmodding_lib.mixin.injectors.client;

import com.mmodding.mmodding_lib.library.glint.client.GlintPack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.datafixers.util.Pair;
import net.minecraft.block.entity.BannerBlockEntity;
import net.minecraft.block.entity.BannerPattern;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.block.entity.BannerBlockEntityRenderer;
import net.minecraft.client.render.entity.model.ShieldEntityModel;
import net.minecraft.client.render.entity.model.TridentEntityModel;
import net.minecraft.client.render.item.BuiltinModelItemRenderer;
import net.minecraft.client.render.model.ModelLoader;
import net.minecraft.client.render.model.json.ModelTransformation;
import net.minecraft.client.util.SpriteIdentifier;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ShieldItem;
import net.minecraft.util.DyeColor;
import net.minecraft.util.Holder;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;

@Mixin(BuiltinModelItemRenderer.class)
public class BuiltinModelItemRendererMixin {

    @Shadow
    private ShieldEntityModel modelShield;

    @Shadow
    private TridentEntityModel modelTrident;

    @Inject(method = "render", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/util/SpriteIdentifier;getSprite()Lnet/minecraft/client/texture/Sprite;"), cancellable = true)
    private void changeFirstDirectItemConsumer(ItemStack stack, ModelTransformation.Mode mode, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, int overlay, CallbackInfo ci) {
		GlintPack.of(stack).ifPresent(glintPack -> {
			boolean hasBlockEntity = BlockItem.getBlockEntityNbtFromStack(stack) != null;
			SpriteIdentifier spriteIdentifier = hasBlockEntity ? ModelLoader.SHIELD_BASE : ModelLoader.SHIELD_BASE_NO_PATTERN;

			VertexConsumer vertexConsumer = spriteIdentifier.getSprite().getTextureSpecificVertexConsumer(
				glintPack.getDirectItemConsumer(
					vertexConsumers,
					this.modelShield.getLayer(spriteIdentifier.getAtlasId()),
					true,
					stack.hasGlint()
				)
			);

			this.modelShield.getHandle().render(matrices, vertexConsumer, light, overlay, 1.0F, 1.0F, 1.0F, 1.0F);

			if (hasBlockEntity) {
				List<Pair<Holder<BannerPattern>, DyeColor>> list = BannerBlockEntity.getPatternsFromNbt(
					ShieldItem.getColor(stack), BannerBlockEntity.getPatternListTag(stack)
				);

				BannerBlockEntityRenderer.renderCanvas(
					matrices, vertexConsumers, light, overlay, this.modelShield.getPlate(), spriteIdentifier, false, list, stack.hasGlint()
				);
			} else {
				this.modelShield.getPlate().render(matrices, vertexConsumer, light, overlay, 1.0F, 1.0F, 1.0F, 1.0F);
			}

			matrices.pop();
			ci.cancel();
		});
    }

    @Inject(method = "render", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/render/item/ItemRenderer;getDirectItemGlintConsumer(Lnet/minecraft/client/render/VertexConsumerProvider;Lnet/minecraft/client/render/RenderLayer;ZZ)Lcom/mojang/blaze3d/vertex/VertexConsumer;", ordinal = 1), cancellable = true)
    private void changeSecondDirectItemConsumer(ItemStack stack, ModelTransformation.Mode mode, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, int overlay, CallbackInfo ci) {
        GlintPack.of(stack).ifPresent(glintPack -> {
			VertexConsumer vertexConsumer = glintPack.getDirectItemConsumer(
				vertexConsumers,
				this.modelTrident.getLayer(TridentEntityModel.TEXTURE),
				false,
				stack.hasGlint()
			);

			this.modelTrident.render(matrices, vertexConsumer, light, overlay, 1.0F, 1.0F, 1.0F, 1.0F);

			matrices.pop();
			ci.cancel();
		});
    }
}
