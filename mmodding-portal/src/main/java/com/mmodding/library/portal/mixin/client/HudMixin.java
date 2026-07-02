package com.mmodding.library.portal.mixin.client;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.sugar.Local;
import com.mmodding.library.java.api.color.Color;
import com.mmodding.library.portal.api.client.ClientPortalEvents;
import com.mojang.blaze3d.pipeline.RenderPipeline;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.Hud;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

import java.util.Objects;
import java.util.Optional;

@Mixin(Hud.class)
public class HudMixin {

	@WrapOperation(method = "extractPortalOverlay", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/GuiGraphicsExtractor;blitSprite(Lcom/mojang/blaze3d/pipeline/RenderPipeline;Lnet/minecraft/client/renderer/texture/TextureAtlasSprite;IIIII)V"))
	private void applyTransitionSpriteClientPortalEvent(GuiGraphicsExtractor instance, RenderPipeline renderPipeline, TextureAtlasSprite sprite, int x, int y, int width, int height, int color, Operation<Void> original, @Local(argsOnly = true, name = "alpha") float alpha) {
		LocalPlayer player = Minecraft.getInstance().player;
		ClientPortalEvents.TransitionSprite.ColoredSprite applied = ClientPortalEvents.TRANSITION_SPRITE.invoker().changeColoredSprite((ClientLevel) Objects.requireNonNull(player).level(), player, Optional.ofNullable(player.portalProcess).map(p -> p.portal).orElse(null), alpha, Color.argb(color));
		if (applied != null) {
			original.call(instance, renderPipeline, applied.sprite(), x, y, width, height, applied.color().toDecimal());
		}
		else {
			original.call(instance, renderPipeline, sprite, x, y, width, height, color);
		}
	}
}
