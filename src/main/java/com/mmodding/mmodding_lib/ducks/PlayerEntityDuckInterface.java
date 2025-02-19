package com.mmodding.mmodding_lib.ducks;

import com.mmodding.mmodding_lib.MModdingCommand;
import com.mmodding.mmodding_lib.library.utils.InternalOf;

@InternalOf(MModdingCommand.class)
public interface PlayerEntityDuckInterface {

	default boolean mmodding_lib$isInvincible() {
		throw new IllegalStateException();
	}

	default boolean mmodding_lib$setInvincible(boolean invincible) {
		throw new IllegalStateException();
	}
}
