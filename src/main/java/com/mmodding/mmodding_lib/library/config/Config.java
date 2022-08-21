package com.mmodding.mmodding_lib.library.config;

import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import org.quiltmc.loader.api.QuiltLoader;

import java.io.*;

public interface Config {

	String getFileName();

	ConfigBuilder defaultConfig();

	default ConfigObject getContent() throws FileNotFoundException {
		return new ConfigObject(this.getReader().getAsJsonObject());
	}

	private JsonElement getReader() throws FileNotFoundException {
		return JsonParser.parseReader(new FileReader(this.getPath()));
	}

	private String getPath() {
		return QuiltLoader.getConfigDir().toString() + ConfigBuilder.getSeparator() +
				this.getFileName()
						.replace("\\", ConfigBuilder.getSeparator())
						.replace("/", ConfigBuilder.getSeparator())
				+ ".json";
	}

	default void initializeConfig() throws IOException {
		if (this.getFileName().contains("\\") || this.getFileName().contains("/")) {
			String path = this.getFileName().replace("\\", "/");
			String[] strings = path.split("/");
			int counter = 0;
			StringBuilder temp = new StringBuilder();
			for (String string: strings) {
				counter ++;
				if (strings.length > counter) {
					new File(QuiltLoader.getConfigDir().toString() + ConfigBuilder.getSeparator() + temp + string).mkdirs();
					temp.append(string).append(ConfigBuilder.getSeparator());
				}
			}
		}
		File configFile = new File(this.getPath());
		System.out.println(configFile.getPath());
		if (configFile.createNewFile()) {
			FileWriter configWriter = new FileWriter(configFile);
			String json = new GsonBuilder().setPrettyPrinting().create().toJson(this.defaultConfig().getJsonObject());
			configWriter.write(json);
			configWriter.close();
		}
	}
}
