package com.mmodding.mmodding_lib.library.items;

import net.minecraft.item.MusicDiscItem;
import net.minecraft.sound.SoundEvent;

import java.util.concurrent.atomic.AtomicBoolean;

public class CustomMusicDiscItem extends MusicDiscItem implements ItemRegistrable {

	private final AtomicBoolean registered = new AtomicBoolean(false);

	public CustomMusicDiscItem(Settings settings, SoundEvent soundEvent, int comparatorOutput, int seconds) {
		super(comparatorOutput, soundEvent, settings, seconds);
	}

	@Override
	public boolean isNotRegistered() {
		return !this.registered.get();
	}

	@Override
	public void setRegistered() {
		registered.set(true);
	}
}
