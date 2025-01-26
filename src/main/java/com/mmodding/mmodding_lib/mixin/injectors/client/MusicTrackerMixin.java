package com.mmodding.mmodding_lib.mixin.injectors.client;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.mmodding.mmodding_lib.library.soundtracks.client.ClientSoundtrackPlayer;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.sound.MusicTracker;
import net.minecraft.client.sound.Sound;
import net.minecraft.client.sound.SoundInstance;
import net.minecraft.client.sound.SoundManager;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(MusicTracker.class)
public class MusicTrackerMixin {

	@Shadow
	@Final
	private MinecraftClient client;

	@WrapOperation(method = "play", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/sound/SoundInstance;getSound()Lnet/minecraft/client/sound/Sound;"))
	private Sound cancelGameMusicIfPlayingSoundtrack(SoundInstance instance, Operation<Sound> original) {
		if (this.client.player != null) {
			if (((ClientSoundtrackPlayer) this.client.player.getSoundtrackPlayer()).isPlaying()) {
				return SoundManager.MISSING_SOUND;
			}
		}
		return original.call(instance);
	}
}
