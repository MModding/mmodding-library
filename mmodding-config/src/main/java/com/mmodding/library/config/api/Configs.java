package com.mmodding.library.config.api;

import com.mmodding.library.config.api.element.ConfigElementTypeWrapper;
import com.mmodding.library.config.impl.ConfigsImpl;
import net.minecraft.util.Identifier;

import java.util.Map;

/**
 * A class allowing to access built configurations.
 */
public class Configs {

	public Configs() {
		throw new IllegalStateException("Configs class only contains static definitions");
	}

	/**
	 * Gives all configurations associated with their respective identifiers.
	 * @return a map containing all configurations
	 */
	public static Map<Identifier, Config> getAll() {
		return ConfigsImpl.getAll();
	}

	/**
	 * Allows to retrieve a configuration by its identifier.
	 * @param identifier the configuration identifier
	 * @return the retrieved configuration
	 */
	public static Config get(Identifier identifier) {
		return ConfigsImpl.get(identifier);
	}

	/**
	 * Allows to register a new configuration element type wrapper for a specified type.
	 * @param type the specified type
	 * @param wrapper the wrapped
	 */
	public static <T, V, P extends ConfigElementTypeWrapper.Properties> void registerWrapper(Class<T> type, ConfigElementTypeWrapper<T, V, P> wrapper) {
		ConfigsImpl.registerWrapper(type, wrapper);
	}
}
