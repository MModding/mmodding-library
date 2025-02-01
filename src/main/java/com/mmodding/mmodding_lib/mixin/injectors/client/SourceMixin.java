package com.mmodding.mmodding_lib.mixin.injectors.client;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.mmodding.mmodding_lib.library.sounds.client.SoundQueue;
import net.minecraft.client.sound.AudioStream;
import net.minecraft.client.sound.Source;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Source.class)
public abstract class SourceMixin {

	@Shadow
	private @Nullable AudioStream stream;

	@Shadow
	public abstract void play();

	@Shadow
	public abstract boolean isStopped();

	@WrapOperation(method = "setStream", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/sound/Source;method_19640(I)V"))
	private void cancelConditionally(Source instance, int i, Operation<Void> original) {
		original.call(instance, this.stream instanceof SoundQueue.SoundQueueStream ? 1 : i);
	}

	@Inject(method = "tick", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/sound/Source;method_19640(I)V", shift = At.Shift.AFTER))
	private void continuePlaying(CallbackInfo ci) {
		if (this.stream instanceof SoundQueue.SoundQueueStream soundQueueStream && !soundQueueStream.isEmpty() && this.isStopped()) {
			this.play();
		}
	}
}
