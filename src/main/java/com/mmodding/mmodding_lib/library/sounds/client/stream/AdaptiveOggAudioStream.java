package com.mmodding.mmodding_lib.library.sounds.client.stream;

import com.mmodding.mmodding_lib.mixin.injectors.client.OggAudioStreamMixin;
import net.minecraft.client.sound.OggAudioStream;
import net.minecraft.util.math.MathHelper;
import org.jetbrains.annotations.ApiStatus;
import org.lwjgl.BufferUtils;

import java.io.IOException;
import java.io.InputStream;
import java.nio.FloatBuffer;

/**
 * This class is combined with {@link OggAudioStreamMixin}
 */
public class AdaptiveOggAudioStream extends OggAudioStream {

	private float sourceSampleRate;
	private float targetSampleRate;

	public AdaptiveOggAudioStream(InputStream inputStream) throws IOException {
		super(inputStream);
	}

	public FloatBuffer resample(FloatBuffer source, float sourceRate, float targetRate) {
		// Create Target Buffer
		float ratio = targetRate / sourceRate;
		int newLength = Math.round(source.remaining() * ratio);
		FloatBuffer target = BufferUtils.createFloatBuffer(newLength);

		// Use Linear Interpolation
		for (int i = 0; i < newLength; i++) {
			float srcIndex = i / ratio;
			int index = (int) Math.floor(srcIndex);
			float delta = srcIndex - index;
			float sourceSample = source.get(index);
			float targetSample = index + 1 < source.limit() ? source.get(index + 1) : sourceSample;
			float interpolated = MathHelper.lerp(delta, sourceSample, targetSample);
			target.put(interpolated);
		}

		target.flip();
		return target;
	}

	@Override
	protected void readChannels(FloatBuffer floatBuffer, OggAudioStream.ChannelList channels) {
		if (this.sourceSampleRate != this.targetSampleRate) {
			floatBuffer = this.resample(floatBuffer, this.sourceSampleRate, this.targetSampleRate);
		}
		if (this.getTrackType().equals(TrackType.STEREO)) {
			while (floatBuffer.hasRemaining()) {
				float f = floatBuffer.get();
				channels.addChannel(f);
				channels.addChannel(f);
			}
		}
		else {
			super.readChannels(floatBuffer, channels);
		}
	}

	@Override
	protected void readChannels(FloatBuffer leftFloatBuffer, FloatBuffer rightFloatBuffer, ChannelList channels) {
		if (this.sourceSampleRate != this.targetSampleRate) {
			leftFloatBuffer = this.resample(leftFloatBuffer, this.sourceSampleRate, this.targetSampleRate);
			rightFloatBuffer = this.resample(rightFloatBuffer, this.sourceSampleRate, this.targetSampleRate);
		}
		if (this.getTrackType().equals(TrackType.MONO)) {
			while (leftFloatBuffer.hasRemaining()) {
				channels.addChannel((leftFloatBuffer.get() + rightFloatBuffer.get()) / 2.0f);
			}
		}
		else {
			super.readChannels(leftFloatBuffer, rightFloatBuffer, channels);
		}
	}

	// Return Value must be a Constant
	public float getForcedSampleRate() {
		return 44100.0f;
	}

	// Return Value must be a Constant
	public TrackType getTrackType() {
		return TrackType.STEREO;
	}

	@ApiStatus.Internal
	public void setSampleRates(float sourceSampleRate, float targetSampleRate) {
		this.sourceSampleRate = sourceSampleRate;
		this.targetSampleRate = targetSampleRate;
	}

	public enum TrackType {
		MONO,
		STEREO
	}
}
