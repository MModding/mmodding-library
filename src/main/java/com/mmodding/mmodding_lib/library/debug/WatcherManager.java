package com.mmodding.mmodding_lib.library.debug;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.function.Function;

// Only works on integrated servers as the debugging is working by executing server-provided getters.
public class WatcherManager {

	private static final Map<UUID, WatcherProvider> ENTITY_WATCHERS = new LinkedHashMap<>();

	public static boolean isWatchable(Entity entity) {
		return entity instanceof WatcherProvider;
	}

	// Used by the integrated server.
	public static void toggleEntityWatcher(Entity entity) {
		if (WatcherManager.ENTITY_WATCHERS.containsKey(entity.getUuid())) {
			WatcherManager.ENTITY_WATCHERS.remove(entity.getUuid());
		}
		else {
			WatcherManager.ENTITY_WATCHERS.put(entity.getUuid(), (WatcherProvider) entity);
		}
	}

	// Used by the integrated server.
	public static void removeEntityWatcher(Entity entity) {
		WatcherManager.ENTITY_WATCHERS.remove(entity.getUuid());
	}

	// Used by the client.
	public static Set<Map.Entry<UUID, WatcherProvider>> getEntries() {
		return WatcherManager.ENTITY_WATCHERS.entrySet();
	}
}
