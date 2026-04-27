package com.mmodding.library.config.impl;

import com.mmodding.library.config.api.Config;
import com.mmodding.library.config.api.Config.Builder;
import com.mmodding.library.config.api.ConfigLevel;
import com.mmodding.library.config.api.ConfigNetworkManagement;
import com.mmodding.library.config.api.content.ConfigSpec;
import net.minecraft.resources.Identifier;

public class ConfigBuilderImpl implements Builder {

	private final String translationKey;
	private final String filePath;
	private final ConfigSpec specification;

	private ConfigLevel level = ConfigLevel.IN_GAME_MODIFICATION;
	private ConfigNetworkManagement networkManagement = ConfigNetworkManagement.UPSTREAM_SERVER;

	public ConfigBuilderImpl(String translationKey, String filePath, ConfigSpec specification) {
		this.translationKey = translationKey;
		this.filePath = filePath;
		this.specification = specification;
	}

	@Override
	public Builder withLevel(ConfigLevel level) {
		this.level = level;
		return this;
	}

	@Override
	public Builder withNetworkManagement(ConfigNetworkManagement networkManagement) {
		this.networkManagement = networkManagement;
		return this;
	}

	@Override
	public Config build(Identifier identifier) {
		Config config = new ConfigImpl(this.translationKey, this.filePath, this.level, this.networkManagement, this.specification);
		ConfigsImpl.CONFIGS.put(identifier, config);
		ConfigLoader.initialLoad(config);
		return config;
	}
}
