package com.mmodding.mmodding_lib.library.config.screen;

import com.mmodding.mmodding_lib.library.config.ConfigObject;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.widget.AlwaysSelectedEntryListWidget;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.Text;

public class ConfigElementsListEntry extends AlwaysSelectedEntryListWidget.Entry<ConfigElementsListEntry> {

	private final MinecraftClient client;
	private final ConfigScreen screen;
	private final int index;
	private final String fieldString;
	private final ConfigObject.Value<?> fieldValue;

	public ConfigElementsListEntry(ConfigScreen screen, int index, String fieldString, ConfigObject.Value<?> fieldValue) {
		this.client = MinecraftClient.getInstance();
		this.screen = screen;
		this.index = index;
		this.fieldString = fieldString;
		this.fieldValue = fieldValue;
	}

	@Override
	public Text getNarration() {
		return Text.of(this.fieldString);
	}

	@Override
	public void render(MatrixStack matrices, int index, int y, int x, int entryWidth, int entryHeight, int mouseX, int mouseY, boolean hovered, float tickDelta) {
		float yEntry = (float) entryHeight / 3 + y;
		this.client.textRenderer.draw(matrices, Text.translatable("config." + this.screen.getQualifier() + "." + this.fieldString), (float) 5 + x, yEntry, 16777215);
		Text fieldType;
		int color;
		String stringValue;
		switch (this.fieldValue.getType()) {
			case "string" -> {
				fieldType = Text.translatable("mmodding_lib.configs.string");
				color = 4781378;
				stringValue = this.fieldValue.getValue();
			}
			case "number" -> {
				fieldType = Text.translatable("mmodding_lib.configs.integer");
				color = 1641430;
				stringValue = this.fieldValue.getValue();
			}
			case "boolean" -> {
				fieldType = Text.translatable("mmodding_lib.configs.boolean");
				color = 14027531;
				stringValue = this.fieldValue.getValue();
			}
			default -> {
				fieldType = Text.translatable("mmodding_lib.configs.null");
				color = 0;
				stringValue = "null";
			}
		}
		this.client.textRenderer.draw(matrices, fieldType, (float) entryWidth / 2 - 10, yEntry, color);
		this.client.textRenderer.draw(matrices, stringValue, (float) entryWidth / 4 * 3, yEntry, 16777215);
	}

	@Override
	public boolean mouseClicked(double mouseX, double mouseY, int button) {
		this.screen.select(this);
		return true;
	}

	public int getIndex() {
		return this.index;
	}

	public String getFieldName() {
		return this.fieldString;
	}

	public ConfigObject.Value<?> getFieldValue() {
		return this.fieldValue;
	}
}
