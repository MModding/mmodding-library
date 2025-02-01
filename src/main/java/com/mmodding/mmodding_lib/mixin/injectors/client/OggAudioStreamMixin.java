package com.mmodding.mmodding_lib.mixin.injectors.client;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.mmodding.mmodding_lib.library.sounds.client.stream.AdaptiveOggAudioStream;
import com.mmodding.mmodding_lib.library.utils.Self;
import net.minecraft.client.sound.OggAudioStream;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

import javax.sound.sampled.AudioFormat;

@Mixin(OggAudioStream.class)
public class OggAudioStreamMixin implements Self<OggAudioStream> {

	@WrapOperation(method = "<init>", at = @At(value = "NEW", target = "(FIIZZ)Ljavax/sound/sampled/AudioFormat;"))
	private AudioFormat forceAdaptivity(float sampleRate, int sampleSizeInBits, int channels, boolean signed, boolean bigEndian, Operation<AudioFormat> original) {
		if (this.getObject() instanceof AdaptiveOggAudioStream adaptive) { // If Adaptive
			float sourceSampleRate = sampleRate;
			if (adaptive.getTrackType().equals(AdaptiveOggAudioStream.TrackType.MONO) && channels == 2) { // Force Mono if Stereo
				channels = 1;
			}
			else if (adaptive.getTrackType().equals(AdaptiveOggAudioStream.TrackType.STEREO) && channels == 1) { // Force Stereo if Mono
				channels = 2;
			}
			if (adaptive.getForcedSampleRate() != sampleRate) { // Force New Sample Rate if Source Sample Rate is not Equal
				sampleRate = adaptive.getForcedSampleRate();
			}
			adaptive.setSampleRates(sourceSampleRate, sampleRate); // Provide Original & New Sample Rate Information
		}
		return original.call(sampleRate, sampleSizeInBits, channels, signed, bigEndian);
	}
}
