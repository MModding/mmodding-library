package com.mmodding.library.config.impl;

import com.mmodding.library.config.api.Config;
import com.mmodding.library.config.api.ConfigLevel;
import com.mmodding.library.config.api.ConfigNetworkManagement;
import com.mmodding.library.config.api.schema.ConfigSchema;

@SuppressWarnings("ClassCanBeRecord") // we do not want equals and hashcode impl of records
public class ConfigImpl implements Config {

	private final String translationKey;
	private final String filePath;
	private final ConfigLevel level;
	private final ConfigNetworkManagement networkManagement;
	private final ConfigSchema schema;

	public ConfigImpl(String translationKey, String filePath, ConfigLevel level, ConfigNetworkManagement networkManagement, ConfigSchema schema) {
		this.translationKey = translationKey;
		this.filePath = filePath;
		this.level = level;
		this.networkManagement = networkManagement;
		this.schema = schema;
	}

	@Override
	public String getTranslationKey() {
		return this.translationKey;
	}

	@Override
	public String getFilePath() {
		return this.filePath;
	}

	@Override
	public ConfigLevel getLevel() {
		return this.level;
	}

	@Override
	public ConfigNetworkManagement getNetworkManagement() {
		return this.networkManagement;
	}

	@Override
	public ConfigSchema getSchema() {
		return this.schema;
	}
}
