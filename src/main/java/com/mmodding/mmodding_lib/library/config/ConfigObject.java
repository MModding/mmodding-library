package com.mmodding.mmodding_lib.library.config;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import org.jetbrains.annotations.ApiStatus;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
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
		Map<String, Value<?>> configElements = new HashMap<>();
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
				case "string" -> this.jsonObject.addProperty(parameter, value.getValue());
				case "number" -> this.jsonObject.addProperty(parameter, Integer.valueOf(value.getValue()));
				case "boolean" -> {
					this.jsonObject.addProperty(parameter, Boolean.valueOf(value.getValue()));
					System.out.println("String Value : " + value.getValue() + ". Boolean Value : " + Boolean.valueOf(value.getValue()) + ".");
				}
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

		public Builder setParameter(String parameter, Value<?> value) {

			int index = this.jsonObject.keySet().stream().toList().indexOf(parameter);

			AtomicInteger temp = new AtomicInteger();
			Map<String, Value<?>> configElementsMap = this.build().getConfigElementsMap();
			Map<String, Value<?>> map = new HashMap<>();

			this.jsonObject.remove(parameter);

			configElementsMap.forEach(((elementParameter, elementValue) -> {
				temp.set(temp.get() + 1);
				if (temp.get() > index) {
					map.put(elementParameter, elementValue);
					this.jsonObject.remove(elementParameter);
				}
			}));

			map.forEach(this::addParameter);

			return this;
		}

		public Builder setStringParameter(String parameter, String value) {
			return this.setParameter(parameter, new Value<>(value));
		}

		public Builder setIntegerParameter(String parameter, int value) {
			return this.setParameter(parameter, new Value<>(value));
		}

		public Builder setBooleanParameter(String parameter, boolean value) {
			return this.setParameter(parameter, new Value<>(value));
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
		private final String type;

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
				this.type = "string";
			} else if (value instanceof Number) {
				this.type = "number";
			} else if (value instanceof Boolean) {
				this.type = "boolean";
			} else {
				throw new IllegalArgumentException("Invalid Parameter Type");
			}
		}

		public String getValue() {
			return String.valueOf(value);
		}

		public String getType() {
			return this.type;
		}
	}
}
