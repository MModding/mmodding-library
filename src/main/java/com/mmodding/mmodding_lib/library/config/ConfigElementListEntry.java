package com.mmodding.mmodding_lib.library.config;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.widget.AlwaysSelectedEntryListWidget;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.Text;

public class ConfigElementListEntry extends AlwaysSelectedEntryListWidget.Entry<ConfigElementListEntry> {

	private final MinecraftClient client;
	private final ConfigScreen screen;
	private final Text fieldText;
	private final Object fieldValue;

	public ConfigElementListEntry(ConfigScreen screen, Text fieldText, Object fieldValue) {
		this.client = MinecraftClient.getInstance();
		this.screen = screen;
		this.fieldText = fieldText;
		this.fieldValue = fieldValue;
	}

	@Override
	public Text getNarration() {
		return fieldText;
	}

	@Override
	public void render(MatrixStack matrices, int index, int y, int x, int entryWidth, int entryHeight, int mouseX, int mouseY, boolean hovered, float tickDelta) {
		this.client.textRenderer.draw(matrices, this.fieldText, (float) 5 + x, (float) entryHeight / 3 + y, 16777215);
	}

	@Override
	public boolean mouseClicked(double mouseX, double mouseY, int button) {
		this.screen.select(this);
		return true;
	}
}
