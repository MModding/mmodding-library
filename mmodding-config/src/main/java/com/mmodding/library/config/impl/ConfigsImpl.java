package com.mmodding.library.config.impl;

import com.mmodding.library.config.api.Config;
import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap;
import net.minecraft.util.Identifier;

import java.util.Map;

public class ConfigsImpl {

	static final Map<Identifier, Config> CONFIGS = new Object2ObjectOpenHashMap<>();

	public static Map<Identifier, Config> getAll() {
		return new Object2ObjectOpenHashMap<>(ConfigsImpl.CONFIGS);
	}

	public static Config get(Identifier identifier) {
		return ConfigsImpl.CONFIGS.get(identifier);
	}
}
