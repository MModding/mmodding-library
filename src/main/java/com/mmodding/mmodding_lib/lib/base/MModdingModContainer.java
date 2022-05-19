package com.mmodding.mmodding_lib.lib.base;

import org.quiltmc.loader.api.ModContainer;
import org.quiltmc.loader.api.Version;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MModdingModContainer {

	private final String name;
	private final String identifier;
	private final Version version;

	MModdingModContainer(String name, String identifier, Version version) {
		this.name = name;
		this.identifier = identifier;
		this.version = version;
	}

	public static MModdingModContainer from(ModContainer mod) {
		return new MModdingModContainer(
				mod.metadata().name(),
				mod.metadata().id(),
				mod.metadata().version()
		);
	}

	public String getName() {
		return this.name;
	}

	public String getIdentifier() {
		return this.identifier;
	}

	public Version getVersion() {
		return this.version;
	}

	public Logger getLogger() {
		return LoggerFactory.getLogger(this.getName());
	}
}
