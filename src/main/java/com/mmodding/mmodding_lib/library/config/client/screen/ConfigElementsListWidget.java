package com.mmodding.mmodding_lib.library.config.client.screen;

import com.mmodding.mmodding_lib.library.config.Config;
import com.mmodding.mmodding_lib.library.config.ConfigObject;
import com.mmodding.mmodding_lib.library.config.client.screen.editing.BooleanEditScreen;
import com.mmodding.mmodding_lib.library.config.client.screen.editing.NumberEditScreen;
import com.mmodding.mmodding_lib.library.config.client.screen.editing.StringEditScreen;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.widget.AlwaysSelectedEntryListWidget;

import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

@Environment(EnvType.CLIENT)
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
		AtomicInteger atomicIndex = new AtomicInteger();
		configContentMap.forEach((string, configElement) -> {
			this.addEntry(new ConfigElementsListEntry(this.screen, atomicIndex.get(), string, configElement));
			atomicIndex.addAndGet(1);
		});
	}

	public void refreshConfigContent(Map<String, ConfigObject.Value<?>> configContentMap) {
		this.clearEntries();
		this.addConfigContent(configContentMap);
	}

	public void removeParameter(ConfigElementsListEntry entry) {
		this.removeEntry(entry);
	}

	public void modifyParameter(ConfigElementsListEntry entry) {
		String stringValue = entry.getFieldValue().getValue();
		switch (entry.getFieldValue().getType()) {
			case STRING -> this.client.setScreen(new StringEditScreen(this.screen, this, entry, this.mutableConfig, entry.getFieldName(), new ConfigObject.Value<>(stringValue)));
			case NUMBER -> this.client.setScreen(new NumberEditScreen(this.screen, this, entry, this.mutableConfig, entry.getFieldName(), new ConfigObject.Value<>(Integer.valueOf(stringValue))));
			case BOOLEAN -> this.client.setScreen(new BooleanEditScreen(this.screen, this, entry, this.mutableConfig, entry.getFieldName(), new ConfigObject.Value<>(Boolean.valueOf(stringValue))));
		}
	}

	public void resetParameter(ConfigElementsListEntry entry) {
		ConfigObject defaultConfig = this.config.defaultConfig();
		String defaultFieldName = entry.getFieldName();
		ConfigObject.Value<?> defaultFieldValue = defaultConfig.getConfigElementsMap().get(defaultFieldName);
		this.removeParameter(entry);
		this.children().add(entry.getIndex(), new ConfigElementsListEntry(this.screen, entry.getIndex(), defaultFieldName, defaultFieldValue));
		ConfigObject.Builder builder = ConfigObject.Builder.fromConfigObject(this.mutableConfig);
		builder.addParameter(defaultFieldName, defaultFieldValue);
		this.mutableConfig = builder.build();
	}

	public Config getConfig() {
		return this.config;
	}

	public ConfigObject getMutableConfig() {
		return this.mutableConfig;
	}
}
