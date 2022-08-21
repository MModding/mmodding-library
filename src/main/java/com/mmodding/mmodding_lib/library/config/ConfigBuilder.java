package com.mmodding.mmodding_lib.library.config;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import net.minecraft.util.Util;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class ConfigBuilder {

	private final JsonObject jsonObject;

	public ConfigBuilder() {
		jsonObject = new JsonObject();
	}

	public ConfigBuilder addStringParameter(String parameter, String value) {
		this.jsonObject.addProperty(parameter, value);
		return this;
	}

	public ConfigBuilder addIntegerParameter(String parameter, int value) {
		this.jsonObject.addProperty(parameter, value);
		return this;
	}

	public ConfigBuilder addBooleanParameter(String parameter, boolean value) {
		this.jsonObject.addProperty(parameter, value);
		return this;
	}

	public ConfigBuilder addArray(String arrayName, Consumer<List<Object>> listConsumer) {
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

	public ConfigBuilder addCategory(String categoryName, ConfigBuilder category) {
		this.jsonObject.add(categoryName, category.getJsonObject());
		return this;
	}

	public JsonObject getJsonObject() {
		return this.jsonObject;
	}

	public static String getSeparator() {
		return Util.getOperatingSystem() == Util.OperatingSystem.WINDOWS ? "\\" : "/";
	}
}
