package com.mmodding.mmodding_lib.library.config;

import com.google.gson.JsonObject;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

public class ConfigObject {

	private final JsonObject jsonObject;

	public ConfigObject(JsonObject object) {
		this.jsonObject = object;
	}

	public String getString(String parameter) throws FileNotFoundException {
		return this.jsonObject.get(parameter).getAsString();
	}

	public int getInteger(String parameter) throws FileNotFoundException {
		return this.jsonObject.get(parameter).getAsInt();
	}

	public boolean getBoolean(String parameter) throws FileNotFoundException {
		return this.jsonObject.get(parameter).getAsBoolean();
	}

	public List<Object> getArray(String parameter) throws FileNotFoundException {
		List<Object> list = new ArrayList<>();
		this.jsonObject.getAsJsonArray(parameter).forEach(list::add);
		return list;
	}

	public ConfigObject getCategory(String parameter) throws FileNotFoundException {
		return new ConfigObject(this.jsonObject.getAsJsonObject(parameter));
	}
}
