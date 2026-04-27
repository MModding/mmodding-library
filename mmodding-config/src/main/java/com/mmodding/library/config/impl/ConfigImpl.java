package com.mmodding.library.config.impl;

import com.mmodding.library.config.api.Config;
import com.mmodding.library.config.api.ConfigLevel;
import com.mmodding.library.config.api.ConfigNetworkManagement;
import com.mmodding.library.config.api.content.ConfigContent;
import com.mmodding.library.config.api.content.ConfigSpec;
import com.mmodding.library.config.impl.content.ConfigSpecImpl;
import com.mojang.serialization.Codec;

public class ConfigImpl implements Config {

	private final String translationKey;
	private final String filePath;
	private final ConfigLevel level;
	private final ConfigNetworkManagement networkManagement;
	private final Codec<ConfigContent> codec;
	// private final ConfigSchema schema;
	private final ConfigContent defaultContent;

	private ConfigContent cachedContent = null;

	public ConfigImpl(String translationKey, String filePath, ConfigLevel level, ConfigNetworkManagement networkManagement, ConfigSpec spec) {
		this.translationKey = translationKey;
		this.filePath = filePath;
		this.level = level;
		this.networkManagement = networkManagement;
		this.codec = ConfigSpecImpl.buildCodec(spec);
		// this.schema = ConfigSpecImpl.retrieveSchema(spec);
		this.defaultContent = ConfigSpecImpl.retrieveDefaultContent(spec);
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
	public Codec<ConfigContent> getCodec() {
		return this.codec;
	}

	/* @Override
	public ConfigSchema getSchema() {
		return this.schema;
	} */

	@Override
	public ConfigContent getDefaultContent() {
		return this.defaultContent;
	}

	@Override
	public ConfigContent getContent() {
		return this.level.equals(ConfigLevel.ALWAYS_UPDATED) ? ConfigLoader.load(this) : this.cachedContent;
	}

	public void updateContent(ConfigContent content) {
		this.cachedContent = content;
	}
}
