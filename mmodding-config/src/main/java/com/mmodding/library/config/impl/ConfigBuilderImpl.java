package com.mmodding.library.config.impl;

import com.mmodding.library.config.api.Config;
import com.mmodding.library.config.api.Config.Builder;
import com.mmodding.library.config.api.ConfigLevel;
import com.mmodding.library.config.api.ConfigNetworkManagement;
import com.mmodding.library.config.api.schema.ConfigSchema;
import net.minecraft.util.Identifier;

public class ConfigBuilderImpl implements Builder {

	private final String translationKey;
	private final String filePath;

	private ConfigLevel level = ConfigLevel.SCREEN_MODIFICATION;
	private ConfigNetworkManagement networkManagement = ConfigNetworkManagement.UPSTREAM_SERVER;
	private ConfigSchema schema = ConfigSchema.empty();

	public ConfigBuilderImpl(String translationKey, String filePath) {
		this.translationKey = translationKey;
		this.filePath = filePath;
	}

	@Override
	public Builder withLevel(ConfigLevel level) {
		this.level = level;
		return this;
	}

	@Override
	public Builder withNetworkManagement(ConfigNetworkManagement networkManagement) {
		this.networkManagement = networkManagement;
		return null;
	}

	@Override
	public Builder withSchema(ConfigSchema schema) {
		this.schema = schema;
		return null;
	}

	@Override
	public Config build(Identifier identifier) {
		Config config = new ConfigImpl(this.translationKey, this.filePath, this.level, this.networkManagement, this.schema);
		ConfigsImpl.CONFIGS.put(identifier, config);
		return config;
	}
}
