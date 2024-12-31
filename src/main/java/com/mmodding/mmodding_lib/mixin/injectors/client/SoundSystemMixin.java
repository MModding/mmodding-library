package com.mmodding.mmodding_lib.mixin.injectors.client;

import com.mmodding.mmodding_lib.library.sounds.client.SoundQueue;
import net.minecraft.client.sound.SoundInstance;
import net.minecraft.client.sound.SoundSystem;
import net.minecraft.client.sound.TickableSoundInstance;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.ArrayList;
import java.util.List;

@Mixin(SoundSystem.class)
public abstract class SoundSystemMixin {

	@Unique
	private final List<TickableSoundInstance> soundQueues = new ArrayList<>();

	@Shadow
	public abstract boolean isPlaying(SoundInstance sound);

	@Inject(method = "play(Lnet/minecraft/client/sound/SoundInstance;)V", at = @At("HEAD"), cancellable = true)
	private void injectSoundQueueToTickingSounds(SoundInstance sound, CallbackInfo ci) {
		if (sound instanceof SoundQueue queue) {
			this.soundQueues.add(queue);
			ci.cancel();
		}
	}

	@Inject(method = "tick()V", at = @At("HEAD"))
	private void tickSoundQueues(CallbackInfo ci) {
		for (TickableSoundInstance queue : this.soundQueues) {
			queue.tick();
			if (queue.isDone()) {
				this.soundQueues.remove(queue);
			}
		}
		this.soundQueues.forEach(TickableSoundInstance::tick);
	}
}
