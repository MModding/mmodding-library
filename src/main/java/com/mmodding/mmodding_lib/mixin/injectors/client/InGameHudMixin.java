package com.mmodding.mmodding_lib.mixin.injectors.client;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.mmodding.mmodding_lib.ducks.EntityDuckInterface;
import com.mmodding.mmodding_lib.library.items.settings.AdvancedItemSettings;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.BufferBuilder;
import com.mojang.blaze3d.vertex.Tessellator;
import com.mojang.blaze3d.vertex.VertexFormat;
import com.mojang.blaze3d.vertex.VertexFormats;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.hud.InGameHud;
import net.minecraft.client.texture.Sprite;
import net.minecraft.item.ItemStack;
import net.minecraft.text.MutableText;
import net.minecraft.util.Formatting;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;

@Mixin(InGameHud.class)
public class InGameHudMixin {

	@Shadow
	private int scaledHeight;

	@Shadow
	private int scaledWidth;

	@Shadow
	@Final
	private MinecraftClient client;

	@Shadow
	private ItemStack currentStack;

	@WrapOperation(method = "renderHeldItemTooltip", at = @At(value = "INVOKE", target = "Lnet/minecraft/text/MutableText;formatted(Lnet/minecraft/util/Formatting;)Lnet/minecraft/text/MutableText;", ordinal = 0))
	private MutableText renderHeldItemTooltip(MutableText mutableText, Formatting formatting, Operation<MutableText> original) {
		List<Formatting> formattings = AdvancedItemSettings.NAME_FORMATTINGS.get(this.currentStack.getItem());
		if (!formattings.isEmpty()) {
			formattings.forEach(mutableText::formatted);
			return mutableText;
		}
		else {
			return original.call(mutableText, formatting);
		}
	}

	@Inject(method = "renderPortalOverlay", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/render/block/BlockModels;getModelParticleSprite(Lnet/minecraft/block/BlockState;)Lnet/minecraft/client/texture/Sprite;"), cancellable = true)
	private void renderPortalOverlay(float nauseaStrength, CallbackInfo ci) {
		assert client.player != null;
		EntityDuckInterface duckedEntity = (EntityDuckInterface) this.client.player;

		if (duckedEntity.mmodding_lib$isInCustomPortal() || duckedEntity.mmodding_lib$getCustomPortalCache() != null) {
			Sprite sprite = this.client.getBlockRenderManager().getModels().getModelParticleSprite(
				!duckedEntity.mmodding_lib$isInCustomPortal()
					? duckedEntity.mmodding_lib$getCustomPortalCache().getDefaultState()
					: duckedEntity.mmodding_lib$getCustomPortal().getPortalBlock().getDefaultState()
			);
			float f = sprite.getMinU();
			float g = sprite.getMinV();
			float h = sprite.getMaxU();
			float i = sprite.getMaxV();
			Tessellator tessellator = Tessellator.getInstance();
			BufferBuilder bufferBuilder = tessellator.getBufferBuilder();
			bufferBuilder.begin(VertexFormat.DrawMode.QUADS, VertexFormats.POSITION_TEXTURE);
			bufferBuilder.vertex(0.0, this.scaledHeight, -90.0).uv(f, i).next();
			bufferBuilder.vertex(this.scaledWidth, this.scaledHeight, -90.0).uv(h, i).next();
			bufferBuilder.vertex(this.scaledWidth, 0.0, -90.0).uv(h, g).next();
			bufferBuilder.vertex(0.0, 0.0, -90.0).uv(f, g).next();
			tessellator.draw();
			RenderSystem.depthMask(true);
			RenderSystem.enableDepthTest();
			RenderSystem.setShaderColor(1.0f, 1.0f, 1.0f, 1.0f);
			ci.cancel();
		}
	}
}
