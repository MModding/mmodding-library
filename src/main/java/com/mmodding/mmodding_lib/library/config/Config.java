package com.mmodding.mmodding_lib.library.config;

import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.mmodding.mmodding_lib.MModdingLibConfig;
import com.mmodding.mmodding_lib.library.config.client.screen.ConfigScreenOptions;
import com.mmodding.mmodding_lib.networking.server.ServerOperations;
import com.mmodding.mmodding_lib.library.utils.ConfigUtils;
import net.minecraft.server.network.ServerPlayerEntity;
import org.quiltmc.loader.api.QuiltLoader;
import org.quiltmc.loader.api.minecraft.DedicatedServerOnly;

import java.io.*;

public interface Config {

	/**
	 * The "identifier" of the config. Used to define the translations keys of the config.
	 * You can take example on {@link MModdingLibConfig#getQualifier()}.
	 * @return the qualifier of the config
	 */
	String getQualifier();

	/**
	 * The file path.
	 * You can take example on {@link MModdingLibConfig#getPath()}.
	 * @return the path of the file (from the config directory and without file extension)
	 */
	String getFilePath();

	/**
	 * The default config object.
	 * You can take example on {@link MModdingLibConfig#defaultConfig()}.
	 * @return the config object in its default state
	 * @see ConfigObject.Builder
	 */
	ConfigObject defaultConfig();

	/**
	 * Some options used in the config screen.
	 * You can take example on {@link MModdingLibConfig#getConfigOptions()}
	 * @return the config screen options
	 * @see ConfigScreenOptions
	 */
	ConfigScreenOptions getConfigOptions();

	/**
	 * The config content directly read from the config json file
	 * @return the config content
	 * @see ConfigObject
	 */
	default ConfigObject getContent() {
		return new ConfigObject(this.getReader().getAsJsonObject());
	}

	/**
	 * This method allows to send a config from a server to a client
	 * @param player the targeted player
	 * @see ServerOperations
	 */
	@DedicatedServerOnly
	default void sendServerConfigToClient(ServerPlayerEntity player) {
		ServerOperations.sendConfigToClient(this, player);
	}

	private JsonElement getReader() {
		try {
			return JsonParser.parseReader(new FileReader(this.getPath()));
		} catch (FileNotFoundException e) {
			throw new RuntimeException(e);
		}
	}

	private String getPath() {
		return QuiltLoader.getConfigDir().toString() + ConfigUtils.getSeparator() +
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
					new File(QuiltLoader.getConfigDir().toString() + ConfigUtils.getSeparator() + temp + string).mkdirs();
					temp.append(string).append(ConfigUtils.getSeparator());
				}
			}
		}
		File configFile = new File(this.getPath());
		System.out.println(configFile.getPath());
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
}
