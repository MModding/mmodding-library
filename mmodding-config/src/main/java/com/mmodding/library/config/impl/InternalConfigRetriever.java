package com.mmodding.library.config.impl;

import com.mmodding.library.config.api.Config;
import com.mmodding.library.config.api.content.ConfigContent;
import com.mmodding.library.config.impl.schema.ConfigOperator;
import com.mmodding.library.config.impl.serialization.ConfigDeserializer;
import com.mmodding.library.config.impl.serialization.ConfigSerializer;
import net.fabricmc.loader.api.FabricLoader;
import org.jetbrains.annotations.ApiStatus;

@ApiStatus.Internal
public class InternalConfigRetriever {

	public static void initialLoad(Config config) {
		boolean exists = FabricLoader.getInstance().getConfigDir().resolve(config.getFilePath() + ".json").toFile().exists();
		ConfigContent content = !exists ? InternalConfigRetriever.createAndLoad(config) : InternalConfigRetriever.load(config);
		ConfigOperator.applySchema(config.getSchema(), content);
		((ConfigImpl) config).updateContent(content);
	}

	public static ConfigContent createAndLoad(Config config) {
		ConfigSerializer.serialize(config, config.getDefaultContent());
		return config.getDefaultContent();
	}

	public static ConfigContent load(Config config) {
		return ConfigDeserializer.deserialize(config);
	}
}
