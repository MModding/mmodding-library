package com.mmodding.mmodding_lib.library.sounds.client;

import net.minecraft.client.sound.*;
import net.minecraft.sound.SoundCategory;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.Nullable;
import org.quiltmc.loader.api.minecraft.ClientOnly;

import java.util.LinkedList;
import java.util.Queue;
import java.util.function.DoubleSupplier;

@ClientOnly
public class SoundQueue implements TickableSoundInstance {

	private final Queue<SoundInstance> queue = new LinkedList<>();
	private final SoundManager soundManager;
	private final Identifier identifier;
	private final DoubleSupplier x;
	private final DoubleSupplier y;
	private final DoubleSupplier z;
	private final boolean infinite;

	private int delayBeforeStarting;

	public SoundQueue(SoundManager soundManager, Identifier identifier, DoubleSupplier x, DoubleSupplier y, DoubleSupplier z, boolean infinite) {
		this(soundManager, identifier, x, y, z, infinite, 0);
	}

	public SoundQueue(SoundManager soundManager, Identifier identifier, DoubleSupplier x, DoubleSupplier y, DoubleSupplier z, boolean infinite, int delayBeforeStarting) {
		this.soundManager = soundManager;
		this.identifier = identifier;
		this.x = x;
		this.y = y;
		this.z = z;
		this.infinite = infinite;
		this.delayBeforeStarting = delayBeforeStarting;
	}

	@Override
	public boolean isDone() {
		return this.queue.isEmpty() && this.delayBeforeStarting < 0 && !this.infinite;
	}

	@Override
	public void tick() {
		if (this.delayBeforeStarting >= 0) {
			if (this.delayBeforeStarting == 0) {
				if (!this.queue.isEmpty()) {
					this.soundManager.play(this.queue.element());
					this.delayBeforeStarting = -1; // The start has been executed, now delaying to update method
				}
				else {
					this.delayBeforeStarting = 10;
				}
			}
			else {
				this.delayBeforeStarting--;
			}
		}
		else {
			if (!this.queue.isEmpty()) {
				this.update();
			}
		}
	}

	private void update() {
		SoundInstance current = this.queue.element();
		if (!this.soundManager.isPlaying(current)) {
			this.queue.remove();
			if (!this.queue.isEmpty()) {
				this.soundManager.play(this.queue.element());
			}
		}
	}

	public void add(SoundInstance soundInstance) {
		this.queue.add(soundInstance);
		// If, after adding a sound instance to the queue, the queue has a size of 1, it means that it did not have
		// any sound instances before, therefore not playing any, which is why we are playing it if infinite.
		if (this.delayBeforeStarting < 0 && this.infinite && this.queue.size() == 1) {
			this.soundManager.play(this.queue.element());
		}
	}

	public void forward(int index) {
		if (index >= 0 && index < this.queue.size()) {
			SoundInstance current = this.queue.remove();
			for (int i = 0; i < index - 1; i++) {
				this.queue.remove();
			}
			Queue<SoundInstance> merging = new LinkedList<>();
			for (int i = 0; i < this.queue.size(); i++) {
				merging.add(this.queue.remove());
			}
			this.queue.add(current);
			for (int i = 0; i < merging.size(); i++) {
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

	public void stop() {
		if (!this.queue.isEmpty()) {
			SoundInstance current = this.queue.remove();
			this.queue.clear();
			this.queue.add(current);
		}
		this.delayBeforeStarting = -1;
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
	public Identifier getId() {
		return this.identifier;
	}

	@Override
	@Nullable
	public WeightedSoundSet getSoundSet(SoundManager soundManager) {
		return null;
	}

	@Override
	public Sound getSound() {
		return null;
	}

	@Override
	public SoundCategory getCategory() {
		return !this.queue.isEmpty() ? this.queue.element().getCategory() : SoundCategory.NEUTRAL;
	}

	@Override
	public boolean isRepeatable() {
		return !this.queue.isEmpty() && this.queue.element().isRepeatable();
	}

	@Override
	public boolean isRelative() {
		return !this.queue.isEmpty() && this.queue.element().isRelative();
	}

	@Override
	public int getRepeatDelay() {
		return !this.queue.isEmpty() ? this.queue.element().getRepeatDelay() : 0;
	}

	@Override
	public float getVolume() {
		return !this.queue.isEmpty() ? this.queue.element().getVolume() : 0;
	}

	@Override
	public float getPitch() {
		return !this.queue.isEmpty() ? this.queue.element().getPitch() : 0;
	}

	@Override
	public double getX() {
		return this.x.getAsDouble();
	}

	@Override
	public double getY() {
		return this.y.getAsDouble();
	}

	@Override
	public double getZ() {
		return this.z.getAsDouble();
	}

	@Override
	public AttenuationType getAttenuationType() {
		return !this.queue.isEmpty() ? this.queue.element().getAttenuationType() : AttenuationType.LINEAR;
	}
}
