package com.mmodding.library.core.api;

import com.mmodding.library.core.impl.MModdingInitializer;
import com.mmodding.library.core.api.management.ElementsManager;
import dev.yumi.commons.event.EventManager;
import net.fabricmc.loader.api.ModContainer;
import net.minecraft.util.Identifier;

import java.util.Map;

public interface MModdingLibrary {

	static EventManager<Identifier> getEventManager() {
		return MModdingInitializer.EVENT_MANAGER;
	}

	static Map<String, ElementsManager> getAllManagers() {
		return MModdingInitializer.MANAGERS;
	}

	static ModContainer getModContainer(Class<?> entrypoint) {
		return MModdingInitializer.getModContainer(entrypoint);
	}

	static ModContainer getModContainer(String mod) {
		return MModdingInitializer.getModContainer(mod);
	}

	static Identifier createId(String path) {
		return new Identifier("mmodding", path);
	}
}
