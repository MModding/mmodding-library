package com.mmodding.library.config.impl;

import com.mmodding.library.config.api.Config;
import com.mmodding.library.config.api.Config.Builder;
import com.mmodding.library.config.api.ConfigLevel;
import com.mmodding.library.config.api.ConfigNetworkManagement;
import com.mmodding.library.config.api.content.MutableConfigContent;
import com.mmodding.library.config.api.schema.ConfigSchema;
import com.mmodding.library.config.impl.content.MutableConfigContentImpl;
import com.mmodding.library.java.api.function.consumer.ReturnableConsumer;
import net.minecraft.util.Identifier;

import java.util.function.Consumer;

public class ConfigBuilderImpl implements Builder {

	private final String translationKey;
	private final String filePath;

	private ConfigLevel level = ConfigLevel.SCREEN_MODIFICATION;
	private ConfigNetworkManagement networkManagement = ConfigNetworkManagement.UPSTREAM_SERVER;
	private ConfigSchema schema = ConfigSchema.empty();
	private ReturnableConsumer<MutableConfigContent> defaultContent = mutable -> {};

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
		return this;
	}

	@Override
	public Builder withSchema(ConfigSchema schema) {
		this.schema = schema;
		return this;
	}

	@Override
	public Builder withDefaultContent(Consumer<MutableConfigContent> content) {
		this.defaultContent = ReturnableConsumer.of(content);
		return this;
	}

	@Override
	public Config build(Identifier identifier) {
		Config config = new ConfigImpl(this.translationKey, this.filePath, this.level, this.networkManagement, this.schema, ((MutableConfigContentImpl) this.defaultContent.acceptReturnable(new MutableConfigContentImpl())).immutable());
		ConfigsImpl.CONFIGS.put(identifier, config);
		InternalConfigRetriever.initialLoad(config);
		return config;
	}
}
