package com.mmodding.library.config.impl;

import com.mmodding.library.config.api.Config;
import com.mmodding.library.config.api.content.ConfigContent;
import com.mmodding.library.config.impl.serialization.ConfigDeserializer;
import com.mmodding.library.config.impl.serialization.ConfigSerializer;
import net.fabricmc.loader.api.FabricLoader;
import org.jetbrains.annotations.ApiStatus;

@ApiStatus.Internal
public class ConfigLoader {

	public static void initialLoad(Config config) {
		boolean exists = FabricLoader.getInstance().getConfigDir().resolve(config.getFilePath() + ".json").toFile().exists();
		ConfigContent content = !exists ? ConfigLoader.createAndLoad(config) : ConfigLoader.load(config);
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
