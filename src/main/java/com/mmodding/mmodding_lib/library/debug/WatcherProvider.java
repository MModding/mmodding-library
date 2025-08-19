package com.mmodding.mmodding_lib.library.debug;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.Vec3d;

import java.util.Map;
import java.util.function.Function;

/**
 * An interface used for providing watchers.
 * @implNote Currently only working with the "/mmodding watch" for entities, but I'll open it more in future versions.
 * It will only work with integrated servers.
 */
public interface WatcherProvider {

	/**
	 * @return a map associating a string to a value getter
	 * @apiNote The "null" value makes it disabled by default.
	 */
	default Map<String, Function<PlayerEntity, Object>> valueWatcher() {
		return null;
	}

	/**
	 * @return a map associating a string to a space position getter
	 * @apiNote The "null" value makes it disabled by default.
	 */
	default Map<String, Function<PlayerEntity, Vec3d>> spaceWatcher() {
		return null;
	}
}
