package com.mmodding.mmodding_lib.library.sounds;

import com.mmodding.mmodding_lib.library.utils.Registrable;
import com.mmodding.mmodding_lib.library.utils.RegistrationUtils;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.Identifier;

public interface SoundEventRegistrable extends Registrable {

	default void register() {
		if (this instanceof SoundEvent soundEvent && this.isNotRegistered()) {
			RegistrationUtils.registerSoundEvent(soundEvent);
			this.setRegistered();
		}
	}

	default void register(Identifier identifier) {
		if (this instanceof SoundEvent soundEvent && this.isNotRegistered()) {
			RegistrationUtils.registerSoundEvent(identifier, soundEvent);
			this.setRegistered();
		}
	}
}
