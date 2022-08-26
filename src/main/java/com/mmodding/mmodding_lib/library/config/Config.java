package com.mmodding.mmodding_lib.library.config;

import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.mmodding.mmodding_lib.library.utils.ConfigUtils;
import org.quiltmc.loader.api.QuiltLoader;

import java.io.*;

public interface Config {

	String getFileName();

	ConfigObject.Builder defaultConfig();

	ConfigScreenOptions getConfigOptions();

	default ConfigObject getContent() {
		return new ConfigObject(this.getReader().getAsJsonObject());
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
				this.getFileName()
						.replace("\\", ConfigUtils.getSeparator())
						.replace("/", ConfigUtils.getSeparator())
				+ ".json";
	}

	default void initializeConfig() {
		if (this.getFileName().contains("\\") || this.getFileName().contains("/")) {
			String path = this.getFileName().replace("\\", "/");
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
				String json = new GsonBuilder().setPrettyPrinting().create().toJson(this.defaultConfig().getJsonObject());
				configWriter.write(json);
				configWriter.close();
			}
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
}
