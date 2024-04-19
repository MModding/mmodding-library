package com.mmodding.mmodding_lib.library.soundtracks;

import net.minecraft.sound.MusicSound;
import net.minecraft.sound.SoundEvent;

public class SoundTrack extends MusicSound {

	public SoundTrack(SoundEvent sound, int minDelay, int maxDelay, boolean replaceCurrentMusic) {
		super(sound, minDelay, maxDelay, replaceCurrentMusic);
	}
}
