package com.mmodding.mmodding_lib.interface_injections;

import com.mmodding.mmodding_lib.library.soundtracks.SoundtrackPlayer;
import net.minecraft.entity.player.PlayerEntity;
import org.quiltmc.qsl.base.api.util.InjectedInterface;

@InjectedInterface(PlayerEntity.class)
public interface SoundtrackPlayerContainer {

	default SoundtrackPlayer getSoundtrackPlayer() {
		throw new IllegalStateException();
	}
}
