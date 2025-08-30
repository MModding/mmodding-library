package com.mmodding.library.core.api;

import com.mmodding.library.core.impl.MModdingInitializer;
import com.mmodding.library.core.api.management.ElementsManager;
import dev.yumi.commons.event.Event;
import dev.yumi.commons.event.EventManager;
import net.fabricmc.loader.api.ModContainer;
import net.minecraft.util.Identifier;

import java.util.HashMap;
import java.util.Map;

public interface MModdingLibrary {

	/**
	 * Retrieves the {@link ElementsManager} to create new {@link Event} objects.
	 * @return the event manager
	 */
	static EventManager<Identifier> getEventManager() {
		return MModdingInitializer.EVENT_MANAGER;
	}

	/**
	 * Allows to retrieve the {@link ElementsManager} from a mod namespace.
	 * @param modId the mod namespace
	 * @return the elements manager of the corresponding mod
	 */
	static ElementsManager getManager(String modId) {
		return MModdingInitializer.MANAGERS.get(modId);
	}

	/**
	 * Allows to retrieve all the registered {@link ElementsManager} objects.
	 * @return the elements managers
	 */
	static Map<String, ElementsManager> getAllManagers() {
		return new HashMap<>(MModdingInitializer.MANAGERS);
	}

	/**
	 * Retrieves the {@link ModContainer} object from an entrypoint class implementation.
	 * @param entrypoint the entrypoint class object
	 * @return the mod container
	 */
	static ModContainer getModContainer(Class<?> entrypoint) {
		return MModdingInitializer.getModContainer(entrypoint);
	}

	/**
	 * Retrieves the {@link ModContainer} object from a mod namespace.
	 * @param mod the mod namespace
	 * @return the mod container
	 */
	static ModContainer getModContainer(String mod) {
		return MModdingInitializer.getModContainer(mod);
	}

	/**
	 * A standardized static-method to create a MModding identifier.
	 * @param path the identifier path
	 * @return the newly created identifier
	 */
	static Identifier createId(String path) {
		return new Identifier("mmodding", path);
	}
}
