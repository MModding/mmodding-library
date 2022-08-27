package com.mmodding.mmodding_lib.library.config;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

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

	public static class Builder {

		private final JsonObject jsonObject;

		public Builder() {
			this.jsonObject = new JsonObject();
		}

		private Builder(ConfigObject from) {
			this.jsonObject = from.jsonObject;
		}

		public static Builder fromConfigObject(ConfigObject configObject) {
			return new Builder(configObject);
		}

		public Builder addStringParameter(String parameter, String value) {
			this.jsonObject.addProperty(parameter, value);
			return this;
		}

		public Builder addIntegerParameter(String parameter, int value) {
			this.jsonObject.addProperty(parameter, value);
			return this;
		}

		public Builder addBooleanParameter(String parameter, boolean value) {
			this.jsonObject.addProperty(parameter, value);
			return this;
		}

		public Builder addArray(String arrayName, Consumer<List<Object>> listConsumer) {
			List<Object> list = new ArrayList<>();
			listConsumer.accept(list);
			this.jsonObject.add(arrayName, new JsonArray());
			list.forEach((element) -> {
				if (element instanceof String string) {
					this.jsonObject.getAsJsonArray(arrayName).add(string);
				}
				else if (element instanceof Number number) {
					this.jsonObject.getAsJsonArray(arrayName).add(number);
				}
				else if (element instanceof Boolean bool) {
					this.jsonObject.getAsJsonArray(arrayName).add(bool);
				}
			});
			return this;
		}

		public Builder addCategory(String categoryName, Builder category) {
			this.jsonObject.add(categoryName, category.getJsonObject());
			return this;
		}

		public Builder setStringParameter(String parameter, String value) {
			this.jsonObject.remove(parameter);
			return this.addStringParameter(parameter, value);
		}

		public Builder setIntegerParameter(String parameter, int value) {
			this.jsonObject.remove(parameter);
			return this.addIntegerParameter(parameter, value);
		}

		public Builder setBooleanParameter(String parameter, boolean value) {
			this.jsonObject.remove(parameter);
			return this.addBooleanParameter(parameter, value);
		}

		public JsonObject getJsonObject() {
			return this.jsonObject;
		}

		public ConfigObject build() {
			return new ConfigObject(this.jsonObject);
		}
	}
}
