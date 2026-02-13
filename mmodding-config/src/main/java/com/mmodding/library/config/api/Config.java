package com.mmodding.library.config.api;

import com.mmodding.library.config.api.content.ConfigContent;
import com.mmodding.library.config.api.content.MutableConfigContent;
import com.mmodding.library.config.api.schema.ConfigSchema;
import com.mmodding.library.config.impl.ConfigBuilderImpl;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.ApiStatus;

import java.util.function.Consumer;

@ApiStatus.NonExtendable
public interface Config {

	static Config.Builder builder(String translationKey, String filePath) {
		return new ConfigBuilderImpl(translationKey, filePath);
	}

	/**
	 * The translation key used by the configuration.
	 * @return the string representation of the configuration's translation key
	 */
	String getTranslationKey();

	/**
	 * The path of the configuration's file from the config directory (excluded).
	 * @return the string representation of the configuration's file path
	 */
	String getFilePath();

	/**
	 * The configuration level, which defines when the configuration is refreshed
	 * @return the configuration level
	 */
	ConfigLevel getLevel();

	/**
	 * The configuration network management, which defines the relation between
	 * the server and the client about the configuration
	 * @return the configuration network management
	 */
	ConfigNetworkManagement getNetworkManagement();

	/**
	 * The configuration schema. Defines multiple information
	 * such as supported qualifiers and their associated types.
	 * @return the configuration schema
	 */
	ConfigSchema getSchema();

	/**
	 * The default content of this configuration.
	 * @return the {@link ConfigContent} object of this configuration's default content
	 */
	ConfigContent getDefaultContent();

	/**
	 * The content of this configuration.
	 * @return the {@link ConfigContent} object of this configuration's content
	 */
	ConfigContent getContent();

	@ApiStatus.NonExtendable
	interface Builder {

		/**
		 * Default value is {@code ConfigLevel.SCREEN_MODIFICATION}.
		 * @param level the new configuration level
		 */
		Config.Builder withLevel(ConfigLevel level);

		/**
		 * Default value is {@code ConfigNetworkManagement.UPSTREAM_SERVER}
		 * @param networkManagement the new configuration network management
		 */
		Config.Builder withNetworkManagement(ConfigNetworkManagement networkManagement);

		/**
		 * Default value is an instance of {@link ConfigSchema#empty()}.
		 * @param schema the new configuration schema
		 * @apiNote It is highly recommended to not keep an empty schema!
		 */
		Config.Builder withSchema(ConfigSchema schema);

		/**
		 * Default value is an empty {@link Consumer<MutableConfigContent>}.
		 * @param content the default configuration content consumer
		 * @apiNote It is highly recommended to set default values to your configuration!
		 */
		Config.Builder withDefaultContent(Consumer<MutableConfigContent> content);

		/**
		 * Builds the configuration under an identifier reference
		 * @param identifier the configuration identifier
		 * @return the configuration
		 */
		Config build(Identifier identifier);
	}
}
