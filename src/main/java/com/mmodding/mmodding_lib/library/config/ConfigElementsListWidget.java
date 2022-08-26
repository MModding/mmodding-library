package com.mmodding.mmodding_lib.library.config;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.widget.AlwaysSelectedEntryListWidget;
import net.minecraft.text.TranslatableText;

import java.util.Map;

public class ConfigElementsListWidget extends AlwaysSelectedEntryListWidget<ConfigElementListEntry> {

	private final Config config;

	public ConfigElementsListWidget(Config config, MinecraftClient minecraftClient, int i, int j, int k, int l, int m) {
		super(minecraftClient, i, j, k, l, m);
		this.config = config;
	}

	@Override
	public int getRowWidth() {
		return this.width - 20;
	}

	public void addConfigContent(Map<String, Object> configContentMap) {
		configContentMap.forEach((string, configElement) -> {
			this.addEntry(new ConfigElementListEntry(new TranslatableText("config." + string), configElement));
		});
	}

	public Config getConfig() {
		return config;
	}
}
