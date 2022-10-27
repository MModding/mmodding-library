package com.mmodding.mmodding_lib.library.config;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.widget.AlwaysSelectedEntryListWidget;

import java.util.Map;

public class ConfigElementsListWidget extends AlwaysSelectedEntryListWidget<ConfigElementsListEntry> {

	private final Config config;
	private final ConfigScreen screen;

	private ConfigObject mutableConfig;

	public ConfigElementsListWidget(Config config, ConfigScreen screen, MinecraftClient minecraftClient, int i, int j, int k, int l, int m) {
		super(minecraftClient, i, j, k, l, m);
		this.config = config;
		this.screen = screen;
		this.mutableConfig = config.getContent();
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

	public void addConfigContent(Map<String, ConfigObject.Value<?>> configContentMap) {
		configContentMap.forEach((string, configElement) ->
				this.addEntry(new ConfigElementsListEntry(this.screen, string, configElement)));
	}

	public void refreshConfigContent(Map<String, ConfigObject.Value<?>> configContentMap) {
		this.clearEntries();
		this.addConfigContent(configContentMap);
	}

	public void removeParameter(ConfigElementsListEntry entry) {
		this.removeEntry(entry);
	}

	public void modifyParameter(ConfigElementsListEntry entry) {}

	public void resetParameter(ConfigElementsListEntry entry) {
		ConfigObject defaultConfig = this.config.defaultConfig();
		String defaultFieldName = entry.getFieldName();
		ConfigObject.Value<?> defaultFieldValue = defaultConfig.getConfigElementsMap().get(defaultFieldName);
		int index = this.children().indexOf(entry);
		this.removeParameter(entry);
		this.children().add(index, new ConfigElementsListEntry(this.screen, defaultFieldName, defaultFieldValue));
		ConfigObject.Builder builder = ConfigObject.Builder.fromConfigObject(this.mutableConfig);
		System.out.println(ConfigObject.Builder.fromConfigObject(this.mutableConfig).getJsonObject().toString());
		builder.addParameter(defaultFieldName, defaultFieldValue);
		this.mutableConfig = builder.build();
		System.out.println(ConfigObject.Builder.fromConfigObject(this.mutableConfig).getJsonObject().toString());
	}

	public Config getConfig() {
		return this.config;
	}

	public ConfigObject getMutableConfig() {
		return this.mutableConfig;
	}
}
