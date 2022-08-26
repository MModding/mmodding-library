package com.mmodding.mmodding_lib.library.config;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.widget.AlwaysSelectedEntryListWidget;
import net.minecraft.text.TranslatableText;

import java.util.Map;

public class ConfigElementsListWidget extends AlwaysSelectedEntryListWidget<ConfigElementListEntry> {
	private final Config config;
	private final ConfigScreen screen;

	public ConfigElementsListWidget(Config config, ConfigScreen screen, MinecraftClient minecraftClient, int i, int j, int k, int l, int m) {
		super(minecraftClient, i, j, k, l, m);
		this.config = config;
		this.screen = screen;
	}

	@Override
	protected boolean isFocused() {
		return this.screen.getFocused() == this;
	}

	@Override
	protected int getScrollbarPositionX() {
		return this.width - 3;
	}

	@Override
	public int getRowWidth() {
		return this.width - 20;
	}

	public void addConfigContent(Map<String, Object> configContentMap) {
		configContentMap.forEach((string, configElement) -> {
			this.addEntry(new ConfigElementListEntry(this.screen, new TranslatableText("config." + this.screen.getModId() + "." + string), configElement));
		});
	}

	public Config getConfig() {
		return config;
	}
}
