package com.mmodding.mmodding_lib.mixin.injectors;

import com.mmodding.mmodding_lib.ducks.EntityDuckInterface;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.BufferBuilder;
import com.mojang.blaze3d.vertex.Tessellator;
import com.mojang.blaze3d.vertex.VertexFormat;
import com.mojang.blaze3d.vertex.VertexFormats;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.hud.InGameHud;
import net.minecraft.client.texture.Sprite;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(InGameHud.class)
public class InGameHudMixin {

	@Shadow
	private int scaledHeight;

	@Shadow
	private int scaledWidth;

	@Shadow
	@Final
	private MinecraftClient client;

	@Inject(method = "renderPortalOverlay", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/render/block/BlockModels;getModelParticleSprite(Lnet/minecraft/block/BlockState;)Lnet/minecraft/client/texture/Sprite;", shift = At.Shift.BEFORE), cancellable = true)
	private void renderPortalOverlay(float nauseaStrength, CallbackInfo ci) {
		assert client.player != null;
		EntityDuckInterface duckedEntity = (EntityDuckInterface) this.client.player;

		if (duckedEntity.mmodding_lib$isInCustomPortal() || duckedEntity.mmodding_lib$getCustomPortalCache() != null) {
			Sprite sprite = this.client.getBlockRenderManager().getModels().getModelParticleSprite(
				!duckedEntity.mmodding_lib$isInCustomPortal()
					? duckedEntity.mmodding_lib$getCustomPortalCache().getDefaultState()
					: duckedEntity.mmodding_lib$getCustomPortalElements().getSecond().getDefaultState()
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
			RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
			ci.cancel();
		}
	}
}
