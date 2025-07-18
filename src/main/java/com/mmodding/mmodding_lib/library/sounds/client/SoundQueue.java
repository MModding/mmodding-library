package com.mmodding.mmodding_lib.library.sounds.client;

import com.mmodding.mmodding_lib.library.sounds.client.stream.AdaptiveOggAudioStream;
import com.mmodding.mmodding_lib.library.sounds.client.stream.ReleasableRepeatingAudioStream;
import com.mmodding.mmodding_lib.mixin.accessors.client.SoundLoaderAccessor;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.sound.*;
import net.minecraft.entity.Entity;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.Identifier;
import net.minecraft.util.Util;
import org.jetbrains.annotations.Nullable;

import javax.sound.sampled.AudioFormat;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.CompletableFuture;

@Environment(EnvType.CLIENT)
public class SoundQueue extends AbstractSoundInstance implements TickableSoundInstance {

	private final Queue<SoundInstance> queue = new LinkedList<>();
	private final SoundManager soundManager;
	private final float volume;
	private final float pitch;
	private final float sampleRate;
	private final AdaptiveOggAudioStream.TrackType trackType;

	public SoundQueue(SoundManager soundManager, Identifier identifier, SoundCategory category, float volume, float pitch) {
		this(soundManager, identifier, category, volume, pitch, 44100.0f, AdaptiveOggAudioStream.TrackType.STEREO);
	}

	public SoundQueue(SoundManager soundManager, Identifier identifier, SoundCategory category, float volume, float pitch, float sampleRate, AdaptiveOggAudioStream.TrackType trackType) {
		super(identifier, category, SoundInstance.method_43221());
		this.soundManager = soundManager;
		this.volume = volume;
		this.pitch = pitch;
		this.sampleRate = sampleRate;
		this.trackType = trackType;
	}

	public void addPositioned(SoundEvent soundEvent, boolean isLooping) {
		SoundInstance soundInstance = new PositionedSoundInstance(soundEvent, SoundCategory.MASTER, 0.0f, 0.0f, this.random, 0.0, 0.0, 0.0);
		this.add(isLooping ? new SimpleLoopingSoundInstance<>(soundInstance) : soundInstance);
	}

	public void addTracking(Entity entity, SoundEvent soundEvent, boolean isLooping) {
		TickableSoundInstance tickableSoundInstance = new EntityTrackingSoundInstance(soundEvent, SoundCategory.MASTER, 0.0f, 0.0f, entity, this.random.nextLong());
		this.add(isLooping ? new TickableLoopingSoundInstance<>(tickableSoundInstance) : tickableSoundInstance);
	}

	public void add(SoundInstance soundInstance) {
		if (soundInstance.getSound() == null) {
			soundInstance.getSoundSet(this.soundManager);
		}
		this.queue.add(soundInstance);
	}

	public void forward(int index) {
		if (index >= 0 && index < this.queue.size()) {
			SoundInstance current = this.queue.remove();
			for (int i = 0; i < index - 1; i++) {
				this.queue.remove();
			}
			Queue<SoundInstance> merging = new LinkedList<>();
			int toMergeSize = this.queue.size();
			for (int i = 0; i < toMergeSize; i++) {
				merging.add(this.queue.remove());
			}
			if (current instanceof LoopingSoundInstance looping) {
				looping.release();
			}
			this.queue.add(current);
			for (int i = 0; i < toMergeSize; i++) {
				this.queue.add(merging.remove());
			}
		}
		else if (index < 0) {
			throw new IllegalArgumentException("SoundQueue does not keep track of previous Sound Instances. Negative indexes are prohibited.");
		}
		else {
			throw new IndexOutOfBoundsException();
		}
	}

	public void release(boolean firstOnly) {
		for (SoundInstance current : this.queue) {
			if (current instanceof LoopingSoundInstance looping) {
				looping.release();
				if (firstOnly) {
					return;
				}
			}
		}
	}

	public void clear() {
		if (!this.queue.isEmpty()) {
			SoundInstance current = this.queue.remove();
			this.queue.clear();
			if (current instanceof LoopingSoundInstance looping) {
				looping.release();
			}
			this.queue.add(current);
		}
	}

	@Override
	public void tick() {
		if (!this.queue.isEmpty() && this.queue.element() instanceof TickableSoundInstance tickable) {
			tickable.tick();
		}
	}

	@Override
	public boolean isDone() {
		return false;
	}

	@Nullable
	public SoundInstance getCurrentlyPlaying() {
		return this.queue.peek();
	}

	public SoundInstance getCurrentlyPlayingOrThrow() {
		return this.queue.element();
	}

	public int getLength() {
		return this.queue.size();
	}

	@Override
	public float getVolume() {
		return this.volume;
	}

	@Override
	public float getPitch() {
		return this.pitch;
	}

	@Override
	public double getX() {
		return !this.queue.isEmpty() ? this.queue.element().getX() : 0.0;
	}

	@Override
	public double getY() {
		return !this.queue.isEmpty() ? this.queue.element().getY() : 0.0;
	}

	@Override
	public double getZ() {
		return !this.queue.isEmpty() ? this.queue.element().getZ() : 0.0;
	}

	@Override
	public AttenuationType getAttenuationType() {
		return AttenuationType.LINEAR;
	}

	@Override
	public CompletableFuture<AudioStream> getAudioStream(SoundLoader loader, Identifier id, boolean repeatInstantly) {
		return CompletableFuture.supplyAsync(
			() -> new SoundQueueStream(soundInstance -> this.getInnerStream(loader, soundInstance), this),
			Util.getMainWorkerExecutor()
		);
	}

	private AudioStream getInnerStream(SoundLoader loader, SoundInstance soundInstance) throws IOException {
		InputStream stream = ((SoundLoaderAccessor) loader).mmodding_lib$getResourceManager().open(soundInstance.getSound().getLocation());
		if (soundInstance instanceof LoopingSoundInstance) {
			return new ReleasableRepeatingAudioStream(
				inputStream -> new AdaptiveOggAudioStream(inputStream) {

					@Override
					public float getForcedSampleRate() {
						return SoundQueue.this.sampleRate;
					}

					@Override
					public TrackType getTrackType() {
						return SoundQueue.this.trackType;
					}
				},
				stream
			);
		}
		else {
			return new AdaptiveOggAudioStream(stream) {

				@Override
				public float getForcedSampleRate() {
					return SoundQueue.this.sampleRate;
				}

				@Override
				public TrackType getTrackType() {
					return SoundQueue.this.trackType;
				}
			};
		}
	}

	public static class SoundQueueStream implements AudioStream {

		private final DelegateFactory delegateFactory;
		private final SoundQueue sounds;
		private final AudioFormat format;
		private final int bufferSize;

		@Nullable
		private AudioStream delegate;

		public SoundQueueStream(DelegateFactory delegateFactory, SoundQueue sounds) {
			this.delegateFactory = delegateFactory;
			this.sounds = sounds;
			this.format = new AudioFormat(this.sounds.sampleRate, 16, this.sounds.trackType.equals(AdaptiveOggAudioStream.TrackType.STEREO) ? 2 : 1, true, false);
			this.bufferSize = (int) (this.format.getSampleSizeInBits() / 8.0f * this.format.getChannels() * this.format.getSampleRate());
		}

		@Override
		public AudioFormat getFormat() {
			return this.format;
		}

		public boolean isEmpty() {
			return this.sounds.queue.isEmpty();
		}

		private void replaceDelegate() throws IOException {
			if (!this.sounds.queue.isEmpty()) {
				this.delegate = this.delegateFactory.create(this.sounds.queue.element());
			}
			else {
				this.delegate = null;
			}
		}

		// If the delegate is a repeating stream, checks if it needs to be released
		private void checkRepeatableCases() {
			if (this.delegate instanceof ReleasableRepeatingAudioStream releasable) {
				if (this.sounds.queue.peek() instanceof LoopingSoundInstance looping && !looping.isRepeatable()) {
					releasable.release();
				}
			}
		}

		@Override
		public ByteBuffer getBuffer(int ignored) throws IOException {
			if (this.delegate == null) {
				this.replaceDelegate();
			}
			if (this.delegate != null) {
				this.checkRepeatableCases();
				ByteBuffer byteBuffer = this.delegate.getBuffer(this.bufferSize);
				if (!byteBuffer.hasRemaining()) {
					this.delegate.close();
					this.sounds.queue.remove();
					this.replaceDelegate();
					if (this.delegate != null) {
						return this.delegate.getBuffer(this.bufferSize);
					}
				}
				else {
					return byteBuffer;
				}
			}
			return null;
		}

		@Override
		public void close() throws IOException {
			if (this.delegate != null) {
				this.delegate.close();
			}
		}
	}

	@FunctionalInterface
	@Environment(EnvType.CLIENT)
	public interface DelegateFactory {
		AudioStream create(SoundInstance soundInstance) throws IOException;
	}
}
