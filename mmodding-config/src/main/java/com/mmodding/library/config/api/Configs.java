package com.mmodding.library.config.api;

import com.mmodding.library.config.impl.ConfigsImpl;
import net.minecraft.util.Identifier;

import java.util.Map;

public class Configs {

	public Configs() {
		throw new IllegalStateException("Configs class only contains static definitions");
	}

	public static Map<Identifier, Config> getAll() {
		return ConfigsImpl.getAll();
	}

	public static Config get(Identifier identifier) {
		return ConfigsImpl.get(identifier);
	}
}
