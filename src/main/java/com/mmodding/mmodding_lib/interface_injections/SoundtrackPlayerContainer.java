package com.mmodding.mmodding_lib.interface_injections;

import com.mmodding.mmodding_lib.library.soundtracks.SoundtrackPlayer;
import com.mmodding.mmodding_lib.library.utils.ClassExtension;
import net.minecraft.entity.player.PlayerEntity;

@ClassExtension(PlayerEntity.class)
public interface SoundtrackPlayerContainer {

	default SoundtrackPlayer getSoundtrackPlayer() {
		throw new IllegalStateException();
	}
}
