package com.mmodding.library.config.api;

public enum ConfigLevel {

	/**
	 * The configuration is always reading content from the configuration file
	 * when the user tries to retrieve information from it.
	 */
	ALWAYS_UPDATED,

	/**
	 * The configuration is updated when a modification happens on it
	 * after being saved on the configuration screen.
	 */
	SCREEN_MODIFICATION,

	/**
	 * The configuration is updated when a world is loaded.
	 */
	WORLD_LOAD,

	/**
	 * The configuration is updated when an instance is loaded.
	 */
	INSTANCE_LOAD
}
