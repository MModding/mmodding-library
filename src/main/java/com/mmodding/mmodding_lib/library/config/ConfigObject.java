package com.mmodding.mmodding_lib.library.config;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import org.jetbrains.annotations.ApiStatus;

import java.util.ArrayList;
import java.util.LinkedHashMap;
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

	public Map<String, Value<?>> getConfigElementsMap() {
		Map<String, Value<?>> configElements = new LinkedHashMap<>();
		this.jsonObject.entrySet().forEach(entry -> configElements.put(entry.getKey(), Value.fromJsonElement(entry.getValue())));
		return configElements;
	}

	public ConfigObject copy() {
		return new ConfigObject(this.jsonObject);
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

		public Builder addParameter(String parameter, Value<?> value) {
			switch (value.getType()) {
				case STRING -> this.jsonObject.addProperty(parameter, value.getValue());
				case NUMBER -> this.jsonObject.addProperty(parameter, Integer.valueOf(value.getValue()));
				case BOOLEAN -> this.jsonObject.addProperty(parameter, Boolean.valueOf(value.getValue()));
			}
			return this;
		}

		public Builder addStringParameter(String parameter, String value) {
			return this.addParameter(parameter, new Value<>(value));
		}

		public Builder addIntegerParameter(String parameter, int value) {
			return this.addParameter(parameter, new Value<>(value));
		}

		public Builder addBooleanParameter(String parameter, boolean value) {
			return this.addParameter(parameter, new Value<>(value));
		}

		@ApiStatus.Experimental
		public Builder addArray(String arrayName, Consumer<List<Object>> listConsumer) {
			/*
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
			*/
			return this;
		}

		@ApiStatus.Experimental
		public Builder addCategory(String categoryName, Builder category) {
			/*
			int index = this.jsonObject.keySet().stream().toList().indexOf(categoryName);
			this.jsonObject.add(categoryName, category.getJsonObject());
			*/
			return this;
		}

		public JsonObject getJsonObject() {
			return this.jsonObject;
		}

		public ConfigObject build() {
			return new ConfigObject(this.jsonObject);
		}
	}

	public static class Value <T> {

		private final T value;
		private final Type type;

		public static Value<?> fromJsonElement(JsonElement element) {
			if (element instanceof JsonPrimitive primitive) {
				if (primitive.isString()) return new Value<>(primitive.getAsString());
				else if (primitive.isNumber()) return new Value<>(primitive.getAsNumber());
				else return new Value<>(primitive.getAsBoolean());
			} else {
				throw new IllegalArgumentException("Invalid Parameter Type");
			}
		}

		public Value(T value) {
			this.value = value;
			if (value instanceof String) {
				this.type = Type.STRING;
			} else if (value instanceof Number) {
				this.type = Type.NUMBER;
			} else if (value instanceof Boolean) {
				this.type = Type.BOOLEAN;
			} else {
				throw new IllegalArgumentException("Invalid Parameter Type");
			}
		}

		public String getValue() {
			return String.valueOf(value);
		}

		public Type getType() {
			return this.type;
		}

		public enum Type {
			STRING,
			NUMBER,
			BOOLEAN
		}
	}
}
