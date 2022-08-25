package com.mmodding.mmodding_lib.library.config;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ConfigObject {

	private final JsonObject jsonObject;

	public ConfigObject(JsonObject object) {
		this.jsonObject = object;
	}

	public String getString(String parameter) {
		return this.jsonObject.get(parameter).getAsString();
	}

	public int getInteger(String parameter) {
		return this.jsonObject.get(parameter).getAsInt();
	}

	public boolean getBoolean(String parameter) {
		return this.jsonObject.get(parameter).getAsBoolean();
	}

	public List<Object> getArray(String parameter) {
		List<Object> list = new ArrayList<>();
		this.jsonObject.getAsJsonArray(parameter).forEach(list::add);
		return list;
	}

	public ConfigObject getCategory(String parameter) {
		return new ConfigObject(this.jsonObject.getAsJsonObject(parameter));
	}

	public Map<String, Object> getConfigElementsMap() {
		Map<String, Object> configElements = new HashMap<>();
		this.jsonObject.entrySet().forEach(entry -> configElements.put(entry.getKey(), getElementAsObject(entry.getValue())));
		return configElements;
	}

	public static Object getElementAsObject(JsonElement element) {
		if (element.isJsonPrimitive()) {
			JsonPrimitive primitiveElement = element.getAsJsonPrimitive();
			if (primitiveElement.isString()) {
				return primitiveElement.getAsString();
			} else if (primitiveElement.isNumber()) {
				return primitiveElement.getAsInt();
			} else {
				return primitiveElement.getAsBoolean();
			}
		}
		else if (element.isJsonArray()) {
			List<Object> list = new ArrayList<>();
			element.getAsJsonArray().forEach(listElement -> list.add(getElementAsObject(listElement)));
			return list;
		}
		else if (element.isJsonObject()) {
			Map<String, Object> map = new HashMap<>();
			element.getAsJsonObject().entrySet().forEach(entry -> map.put(entry.getKey(), getElementAsObject(entry.getValue())));
			return map;
		}
		else {
			return null;
		}
	}

	public enum ElementType {
		STRING,
		INTEGER,
		BOOLEAN,
		ARRAY,
		CATEGORY
	}
}
