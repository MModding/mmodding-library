package com.mmodding.library.config.impl;

import com.mmodding.library.config.api.Config;
import com.mmodding.library.config.api.ConfigLevel;
import com.mmodding.library.config.api.ConfigNetworkManagement;
import com.mmodding.library.config.api.content.ConfigContent;
import com.mmodding.library.config.api.content.ConfigSchema;
import com.mmodding.library.config.api.content.ConfigSpec;
import com.mmodding.library.config.impl.content.ConfigCodec;
import com.mmodding.library.config.impl.content.ConfigSchemaImpl;
import com.mmodding.library.config.impl.content.ConfigSpecImpl;
import com.mmodding.library.config.impl.content.ConfigStreamCodec;
import com.mojang.serialization.Codec;
import io.netty.buffer.ByteBuf;
import net.minecraft.network.codec.StreamCodec;
import org.jspecify.annotations.Nullable;

public class ConfigImpl implements Config {

	private final String translationKey;
	private final String filePath;
	private final ConfigLevel level;
	private final ConfigNetworkManagement networkManagement;
	private final ConfigSchema schema;
	private final Codec<ConfigContent> codec;
	private final StreamCodec<? super ByteBuf, ConfigContent> streamCodec;
	private final ConfigContent defaultContent;

	private ConfigContent localCachedContent = null;
	private ConfigContent upstreamCachedContent = null;

	public ConfigImpl(String translationKey, String filePath, ConfigLevel level, ConfigNetworkManagement networkManagement, ConfigSpec spec) {
		this.translationKey = translationKey;
		this.filePath = filePath;
		this.level = level;
		this.networkManagement = networkManagement;
		this.schema = ConfigSpecImpl.retrieveSchema(spec);
		this.codec = new ConfigCodec(this.schema, spec);
		this.streamCodec = new ConfigStreamCodec<>(this.schema, spec);
		this.defaultContent = ConfigSpecImpl.retrieveDefaultContent((ConfigSchemaImpl) this.schema, "", spec);
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

	@Override
	public Codec<ConfigContent> getCodec() {
		return this.codec;
	}

	@Override
	public StreamCodec<? super ByteBuf, ConfigContent> getStreamCodec() {
		return this.streamCodec;
	}

	@Override
	public ConfigContent getDefaultContent() {
		return this.defaultContent;
	}

	@Override
	public ConfigContent getContent() {
		if (this.networkManagement.equals(ConfigNetworkManagement.UPSTREAM_SERVER) && this.upstreamCachedContent != null) {
			return this.upstreamCachedContent;
		}
		return this.level.equals(ConfigLevel.ALWAYS_UPDATED) ? ConfigLoader.load(this) : this.localCachedContent;
	}

	public void updateLocalContent(ConfigContent content) {
		this.localCachedContent = content;
	}

	// Should only get called on client side; if not, server will cache upstream content, which will make #getContent misbehave on server side.
	public void updateUpstreamContent(@Nullable ConfigContent content) {
		this.upstreamCachedContent = content;
	}
}
