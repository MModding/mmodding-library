package com.mmodding.mmodding_lib.library.sounds.client;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.sound.*;
import net.minecraft.sound.SoundCategory;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.Nullable;

@Environment(EnvType.CLIENT)
public class TickableLoopingSoundInstance<S extends TickableSoundInstance> implements TickableSoundInstance, LoopingSoundInstance {

	private final S wrapped;

	private boolean canRepeat;

	public TickableLoopingSoundInstance(S soundInstance) {
		this.wrapped = soundInstance;
		this.canRepeat = true;
	}

	@Override
	public void release() {
		this.canRepeat = false;
	}

	@Override
	public Identifier getId() {
		return this.wrapped.getId();
	}

	@Override
	@Nullable
	public WeightedSoundSet getSoundSet(SoundManager soundManager) {
		return this.wrapped.getSoundSet(soundManager);
	}

	@Override
	public Sound getSound() {
		return this.wrapped.getSound();
	}

	@Override
	public SoundCategory getCategory() {
		return this.wrapped.getCategory();
	}

	@Override
	public boolean isRepeatable() {
		return this.canRepeat;
	}

	@Override
	public boolean isRelative() {
		return this.wrapped.isRelative();
	}

	@Override
	public int getRepeatDelay() {
		return this.wrapped.getRepeatDelay();
	}

	@Override
	public float getVolume() {
		return this.wrapped.getVolume();
	}

	@Override
	public float getPitch() {
		return this.wrapped.getPitch();
	}

	@Override
	public double getX() {
		return this.wrapped.getX();
	}

	@Override
	public double getY() {
		return this.wrapped.getY();
	}

	@Override
	public double getZ() {
		return this.wrapped.getZ();
	}

	@Override
	public AttenuationType getAttenuationType() {
		return this.wrapped.getAttenuationType();
	}

	@Override
	public boolean equals(Object obj) {
		return this.wrapped.equals(obj instanceof TickableLoopingSoundInstance<?> looping ? looping.wrapped : obj);
	}

	@Override
	public String toString() {
		return this.wrapped.toString().replace("SoundInstance", "LoopingSoundInstance");
	}

	@Override
	public boolean isDone() {
		return this.wrapped.isDone() && !this.canRepeat;
	}

	@Override
	public void tick() {
		this.wrapped.tick();
	}
}
