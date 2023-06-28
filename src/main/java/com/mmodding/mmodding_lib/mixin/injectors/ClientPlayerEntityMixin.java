package com.mmodding.mmodding_lib.mixin.injectors;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.DeathScreen;
import net.minecraft.client.gui.screen.DownloadingTerrainScreen;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.sound.PositionedSoundInstance;
import net.minecraft.sound.SoundEvents;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ClientPlayerEntity.class)
public abstract class ClientPlayerEntityMixin extends PlayerEntityMixin {

	@Shadow
	public float lastNauseaStrength;

	@Shadow
	public float nextNauseaStrength;

	@Shadow
	@Final
	protected MinecraftClient client;

	@Shadow
	public abstract void closeHandledScreen();

	@Inject(method = "updateNausea", at = @At(value = "HEAD", shift = At.Shift.BY, by = 2), cancellable = true)
	private void updateNausea(CallbackInfo ci) {
		if (this.inCustomPortal) {
			this.lastNauseaStrength = this.nextNauseaStrength;

			if (this.client.currentScreen != null
				&& !this.client.currentScreen.isPauseScreen()
				&& !(this.client.currentScreen instanceof DeathScreen)
				&& !(this.client.currentScreen instanceof DownloadingTerrainScreen)) {
				if (this.client.currentScreen instanceof HandledScreen) {
					this.closeHandledScreen();
				}

				this.client.setScreen(null);
			}

			if (this.nextNauseaStrength == 0.0F) {
				this.client.getSoundManager().play(PositionedSoundInstance.ambient(SoundEvents.BLOCK_PORTAL_TRIGGER, this.random.nextFloat() * 0.4F + 0.8F, 0.25F));
			}

			this.nextNauseaStrength += 0.0125F;
			if (this.nextNauseaStrength >= 1.0F) {
				this.nextNauseaStrength = 1.0F;
			}

			this.inCustomPortal = false;
			this.tickNetherPortalCooldown();

			ci.cancel();
		}
	}
}
