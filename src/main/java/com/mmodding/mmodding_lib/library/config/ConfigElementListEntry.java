package com.mmodding.mmodding_lib.library.config;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.widget.AlwaysSelectedEntryListWidget;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;

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
		float yEntry = (float) entryHeight / 3 + y;
		this.client.textRenderer.draw(matrices, this.fieldText, (float) 5 + x, yEntry, 16777215);
		Text fieldType;
		int color;
		String stringValue;
		if (this.fieldValue instanceof String) {
			fieldType = new TranslatableText("mmodding_lib.configs.string");
			color = 4781378;
			stringValue = (String) this.fieldValue;
		}
		else if (this.fieldValue instanceof Integer) {
			fieldType = new TranslatableText("mmodding_lib.configs.integer");
			color = 1641430;
			stringValue = ((Integer) this.fieldValue).toString();
		}
		else if (this.fieldValue instanceof Boolean) {
			fieldType = new TranslatableText("mmodding_lib.configs.boolean");
			color = 14027531;
			stringValue = ((Boolean) this.fieldValue).toString();
		}
		else {
			fieldType = new TranslatableText("mmodding_lib.configs.null");
			color = 0;
			stringValue = "null";
		}
		this.client.textRenderer.draw(matrices, fieldType, (float) entryWidth / 2 - 10, yEntry, color);
		this.client.textRenderer.draw(matrices, stringValue, (float) entryWidth / 4 * 3, yEntry, 16777215);
	}

	@Override
	public boolean mouseClicked(double mouseX, double mouseY, int button) {
		this.screen.select(this);
		return true;
	}
}
