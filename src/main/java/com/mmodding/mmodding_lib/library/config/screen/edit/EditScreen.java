package com.mmodding.mmodding_lib.library.config.screen.edit;

import com.mmodding.mmodding_lib.library.config.ConfigObject;
import com.mmodding.mmodding_lib.library.config.screen.ConfigElementsListEntry;
import com.mmodding.mmodding_lib.library.config.screen.ConfigElementsListWidget;
import com.mmodding.mmodding_lib.library.config.screen.ConfigScreen;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.Text;

public abstract class EditScreen<T> extends Screen {

	protected final ConfigScreen lastScreen;
	protected final ConfigElementsListWidget list;
	protected final ConfigElementsListEntry entry;
	protected ConfigObject mutableConfig;

	protected final String fieldName;
	protected final ConfigObject.Value<T> baseValue;

	protected ButtonWidget keepButton;
	protected ButtonWidget cancelButton;

	public EditScreen(ConfigScreen lastScreen, ConfigElementsListWidget list, ConfigElementsListEntry entry, ConfigObject mutableConfig, String fieldName, ConfigObject.Value<T> baseValue) {
		super(Text.of("Edit Value Screen"));

		this.lastScreen = lastScreen;
		this.list = list;
		this.entry = entry;
		this.mutableConfig = mutableConfig;

		this.fieldName = fieldName;
		this.baseValue = baseValue;
	}

	@Override
	protected void init() {
		assert this.client != null;
		this.client.keyboard.setRepeatEvents(true);
		this.keepButton = this.addDrawableChild(new ButtonWidget(this.width / 2 - 50, this.height - 30, 100, 20, Text.of("Keep"), button -> this.keepAndClose()));
		this.cancelButton = this.addDrawableChild(new ButtonWidget(this.width / 2 - 55, this.height - 30, 100, 20, Text.of("Cancel"), button -> this.close()));
	}

	@Override
	public void render(MatrixStack matrices, int mouseX, int mouseY, float delta) {
		this.keepButton.render(matrices, mouseX, mouseY, delta);
		this.cancelButton.render(matrices, mouseX, mouseY, delta);
	}

	@Override
	public void renderBackground(MatrixStack matrices) {
		RenderSystem.setShader(GameRenderer::getPositionTexColorShader);
		RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
	}

	public abstract void keepAndClose();

	protected void keep(ConfigObject.Value<T> newValue) {
		int index = this.children().indexOf(this.entry);
		this.list.removeParameter(this.entry);
		this.list.children().add(index, new ConfigElementsListEntry(this.lastScreen, this.fieldName, newValue));
		ConfigObject.Builder builder = ConfigObject.Builder.fromConfigObject(this.mutableConfig);
		builder.addParameter(this.fieldName, newValue);
		this.mutableConfig = builder.build();
	}

	public void close() {
		assert this.client != null;
		this.client.setScreen(lastScreen);
	}
}
