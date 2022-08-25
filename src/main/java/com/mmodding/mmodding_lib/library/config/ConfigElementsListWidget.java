package com.mmodding.mmodding_lib.library.config;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.widget.AlwaysSelectedEntryListWidget;
import net.minecraft.text.TranslatableText;

import java.util.Map;

public class ConfigElementsListWidget extends AlwaysSelectedEntryListWidget<ConfigElementListEntry> {

	public ConfigElementsListWidget(MinecraftClient minecraftClient, int i, int j, int k, int l, int m) {
		super(minecraftClient, i, j, k, l, m);
	}

	public void addConfigContent(Map<String, Object> configContentMap) {
		configContentMap.forEach((string, configElement) -> {
			this.addEntry(new ConfigElementListEntry(new TranslatableText("config." + string), configElement));
		});
	}
}
