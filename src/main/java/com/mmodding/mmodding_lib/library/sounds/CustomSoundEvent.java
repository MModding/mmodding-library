package com.mmodding.mmodding_lib.library.sounds;

import net.minecraft.sound.SoundEvent;
import net.minecraft.util.Identifier;

import java.util.concurrent.atomic.AtomicBoolean;

public class CustomSoundEvent extends SoundEvent implements SoundEventRegistrable {

    private final AtomicBoolean registered = new AtomicBoolean(false);

	public CustomSoundEvent(Identifier id) {
		super(id);
	}

	public CustomSoundEvent(Identifier identifier, float f) {
		super(identifier, f);
	}

	@Override
    public boolean isNotRegistered() {
        return !this.registered.get();
    }

    @Override
    public void setRegistered() {
        this.registered.set(true);
    }
}
