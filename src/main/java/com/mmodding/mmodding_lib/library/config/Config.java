package com.mmodding.mmodding_lib.library.config;

import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.mmodding.mmodding_lib.MModdingLibConfig;
import com.mmodding.mmodding_lib.networking.server.ServerOperations;
import com.mmodding.mmodding_lib.library.utils.ConfigUtils;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.server.network.ServerPlayerEntity;

import java.io.*;

public interface Config {

	/**
	 * The "identifier" of the config. Used to define the translations keys of the config.
	 * <br>You can take example on {@link MModdingLibConfig#getQualifier()}.
	 * @return the qualifier of the config
	 */
	String getQualifier();

	/**
	 * The file path.
	 * <br>You can take example on {@link MModdingLibConfig#getPath()}.
	 * @return the path of the file (from the config directory and without file extension)
	 */
	String getFilePath();

	/**
	 * The default config object.
	 * <br>You can take example on {@link MModdingLibConfig#defaultConfig()}.
	 * @return the config object in its default state
	 * @see ConfigObject.Builder
	 */
	ConfigObject defaultConfig();

	/**
	 * Some options used in the config screen.
	 * <br>You can take example on {@link MModdingLibConfig#getConfigOptions()}.
	 * @return the config screen options
	 * @see ConfigOptions
	 */
	ConfigOptions getConfigOptions();

	default NetworkingState getNetworkingSate() {
		return NetworkingState.CLIENT_CACHES;
	}

	/**
	 * The config content directly read from the config json file.
	 * @return the config content
	 * @see ConfigObject
	 */
	default ConfigObject getContent() {
		return new ConfigObject(this.getReader().getAsJsonObject());
	}

	/**
	 * This method allows to send a config from a server to a client.
	 * @param player the targeted player
	 * @see ServerOperations
	 */
	@Environment(EnvType.SERVER)
	default void sendServerConfigToClient(ServerPlayerEntity player) {
		ServerOperations.sendConfigToClient(player, this);
	}

	private JsonElement getReader() {
		try {
			return JsonParser.parseReader(new FileReader(this.getPath()));
		} catch (FileNotFoundException e) {
			throw new RuntimeException(e);
		}
	}

	private String getPath() {
		return FabricLoader.getInstance().getConfigDir().toString() + ConfigUtils.getSeparator() +
			this.getFilePath()
				.replace("\\", ConfigUtils.getSeparator())
				.replace("/", ConfigUtils.getSeparator())
			+ ".json";
	}

	default void saveConfig(ConfigObject configObject) {
		try {
			FileWriter configWriter = new FileWriter(this.getPath());
			String json = new GsonBuilder().setPrettyPrinting().create()
				.toJson(ConfigObject.Builder.fromConfigObject(configObject).getJsonObject());
			configWriter.write(json);
			configWriter.close();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	default void initializeConfig() {
		if (this.getFilePath().contains("\\") || this.getFilePath().contains("/")) {
			String path = this.getFilePath().replace("\\", "/");
			String[] strings = path.split("/");
			int counter = 0;
			StringBuilder temp = new StringBuilder();
			for (String string: strings) {
				counter ++;
				if (strings.length > counter) {
					new File(FabricLoader.getInstance().getConfigDir().toString() + ConfigUtils.getSeparator() + temp + string).mkdirs();
					temp.append(string).append(ConfigUtils.getSeparator());
				}
			}
		}
		File configFile = new File(this.getPath());
		try {
			if (configFile.createNewFile()) {
				FileWriter configWriter = new FileWriter(configFile);
				String json = new GsonBuilder().setPrettyPrinting().create()
						.toJson(ConfigObject.Builder.fromConfigObject(this.defaultConfig()).getJsonObject());
				configWriter.write(json);
				configWriter.close();
			}
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}


	/**
	 * Refers to the current config networking state
	 * @see Config#getNetworkingSate()
	 */
	enum NetworkingState {

		/**
		 * When a world is joined, the current configuration is statically stored locally.
		 * <br>When a server is joined, the current configuration is sent to the client.
		 */
		LOCAL_CACHES,

		/**
		 * When a server is joined, the current configuration is sent to the client.
		 * @apiNote Default value of {@link Config#getNetworkingSate()}
		 */
		CLIENT_CACHES,

		/**
		 * The current configuration is never cached.
		 */
		WITHOUT_CACHES
	}
}
