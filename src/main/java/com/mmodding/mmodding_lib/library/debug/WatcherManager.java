package com.mmodding.mmodding_lib.library.debug;

import net.minecraft.entity.Entity;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.function.Supplier;

// Only works on integrated servers as the debugging is working by executing server-provided getters.
public class WatcherManager {

	private static final Map<UUID, Map<String, Supplier<Object>>> ENTITY_WATCHERS = new LinkedHashMap<>();

	public static boolean isWatchable(Entity entity) {
		return entity instanceof WatcherProvider;
	}

	// Used by the integrated server.
	public static void toggleEntityWatcher(Entity entity) {
		if (WatcherManager.ENTITY_WATCHERS.containsKey(entity.getUuid())) {
			WatcherManager.ENTITY_WATCHERS.remove(entity.getUuid());
		}
		else {
			WatcherProvider provider = (WatcherProvider) entity;
			WatcherManager.ENTITY_WATCHERS.put(entity.getUuid(), provider.watcher());
		}
	}

	// Used by the client.
	public static Set<Map.Entry<UUID, Map<String, Supplier<Object>>>> getEntries() {
		return WatcherManager.ENTITY_WATCHERS.entrySet();
	}
}
