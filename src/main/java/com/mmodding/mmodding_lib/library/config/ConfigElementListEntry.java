package com.mmodding.mmodding_lib.library.config;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.widget.AlwaysSelectedEntryListWidget;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.Text;

public class ConfigElementListEntry extends AlwaysSelectedEntryListWidget.Entry<ConfigElementListEntry> {

	private final MinecraftClient client;
	private final Text fieldText;
	private final Object fieldValue;

	public ConfigElementListEntry(Text fieldText, Object fieldValue) {
		this.client = MinecraftClient.getInstance();
		this.fieldText = fieldText;
		this.fieldValue = fieldValue;
	}

	@Override
	public Text getNarration() {
		return fieldText;
	}

	@Override
	public void render(MatrixStack matrices, int index, int y, int x, int entryWidth, int entryHeight, int mouseX, int mouseY, boolean hovered, float tickDelta) {
		this.client.textRenderer.draw(matrices, this.fieldText, (float) entryWidth / 8, (float) entryHeight / 2, 16777215);
	}
}
