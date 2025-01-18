package com.mmodding.mmodding_lib.mixin.injectors.client;

import com.mmodding.mmodding_lib.library.sounds.client.SoundQueue;
import net.minecraft.client.sound.AudioStream;
import net.minecraft.client.sound.Source;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import javax.sound.sampled.AudioFormat;

@Mixin(Source.class)
public abstract class SourceMixin {

	@Shadow
	private int bufferSize;

	@Shadow
	private @Nullable AudioStream stream;

	@Shadow
	private static int getBufferSize(AudioFormat format, int time) {
		throw new IllegalStateException();
	}

	@Inject(method = "method_19640", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/sound/AudioStream;getBuffer(I)Ljava/nio/ByteBuffer;"))
	private void reloadFormat(int i, CallbackInfo ci) {
		assert this.stream != null;
		if (this.stream instanceof SoundQueue.SoundQueueStream soundQueueStream && soundQueueStream.isDirty()) {
			this.bufferSize = getBufferSize(this.stream.getFormat(), 1);
			soundQueueStream.clean();
		}
	}
}
