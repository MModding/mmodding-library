package com.mmodding.library.portal.mixin.client;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.mmodding.library.portal.api.client.ClientPortalEvents;
import com.mojang.authlib.GameProfile;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.client.resources.sounds.SimpleSoundInstance;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(LocalPlayer.class)
public abstract class LocalPlayerMixin extends Player {

	public LocalPlayerMixin(Level level, GameProfile gameProfile) {
		super(level, gameProfile);
	}

	@WrapOperation(method = "handlePortalTransitionEffect", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/resources/sounds/SimpleSoundInstance;forLocalAmbience(Lnet/minecraft/sounds/SoundEvent;FF)Lnet/minecraft/client/resources/sounds/SimpleSoundInstance;"))
	private SimpleSoundInstance applyEntranceSoundClientPortalEvent(SoundEvent sound, float pitch, float volume, Operation<SimpleSoundInstance> original) {
		if (this.portalProcess != null) {
			ClientPortalEvents.EnteringSound.ModulatedSound applied = ClientPortalEvents.ENTERING_SOUND.invoker().changeSound((ClientLevel) this.level(), (LocalPlayer) (Object) this, this.portalProcess.portal, pitch, volume);
			if (applied != null) {
				return original.call(applied.soundEvent(), applied.pitch(), applied.volume());
			}
		}
		return original.call(sound, pitch, volume);
	}
}
