package com.mmodding.mmodding_lib.library.config.screen;

import com.mmodding.mmodding_lib.library.config.Config;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.BufferBuilder;
import com.mojang.blaze3d.vertex.Tessellator;
import com.mojang.blaze3d.vertex.VertexFormat;
import com.mojang.blaze3d.vertex.VertexFormats;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.text.ScreenTexts;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.render.*;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

public class ConfigScreen extends Screen {

	private final String modId;
	private final Config config;
	private final Screen lastScreen;
	private boolean initialized;

	private ConfigElementsListWidget configElementsList;
	private ButtonWidget modifyValueButton;
	private ButtonWidget resetValueButton;
	private ButtonWidget saveButton;
	private ButtonWidget refreshButton;
	private ButtonWidget cancelButton;

	public ConfigScreen(String modId, Config config, Screen lastScreen) {
		super(config.getConfigOptions().name());
		this.modId = modId;
		this.config = config;
		this.lastScreen = lastScreen;
	}

	@Override
	protected void init() {
		assert this.client != null;
		this.client.keyboard.setRepeatEvents(true);
		if (this.initialized) {
			this.configElementsList.updateSize(this.width, this.height, 10, this.height - 60);
		}
		else {
			this.initialized = true;
			this.configElementsList = new ConfigElementsListWidget(this.config, this, this.client, this.width, this.height, 10, this.height - 60, 30);
			this.configElementsList.addConfigContent(this.config.getContent().getConfigElementsMap());
		}
		int w0 = this.width / 2 - 155;
		int w1 = this.width / 2 + 5;
		int w2 = this.width / 2 - 50;
		int w3 = this.width / 2 + 55;
		int h0 = this.height - 55;
		int h1 = this.height - 30;
		this.addSelectableChild(this.configElementsList);
		this.modifyValueButton = this.addDrawableChild(new ButtonWidget(w0, h0, 150, 20, Text.of("Modify Value"), button -> this.modifyEntryValue()));
		this.resetValueButton = this.addDrawableChild(new ButtonWidget(w1, h0, 150, 20, Text.of("Reset Value"), button -> this.resetEntryValue()));
		this.saveButton = this.addDrawableChild(new ButtonWidget(w0, h1, 100, 20, Text.of("Save"), button -> this.saveAndClose()));
		this.refreshButton = this.addDrawableChild(new ButtonWidget(w2, h1, 100, 20, Text.of("Refresh Config"), button -> this.refreshConfig()));
		this.cancelButton = this.addDrawableChild(new ButtonWidget(w3, h1, 100, 20, ScreenTexts.CANCEL, button -> this.close()));
		this.updateButtons();
	}

	public void select(ConfigElementsListEntry entry) {
		this.configElementsList.setSelected(entry);
		this.updateButtons();
	}

	public void updateButtons() {
		this.modifyValueButton.active = false;
		this.resetValueButton.active = false;
		ConfigElementsListEntry entry = this.configElementsList.getSelectedOrNull();
		if (entry != null) {
			this.modifyValueButton.active = true;
			this.resetValueButton.active = true;
		}
	}

	@Override
	public void render(MatrixStack matrices, int mouseX, int mouseY, float delta) {
		this.configElementsList.render(matrices, mouseX, mouseY, delta);
		this.modifyValueButton.render(matrices, mouseX, mouseY, delta);
		this.resetValueButton.render(matrices, mouseX, mouseY, delta);
		this.saveButton.render(matrices, mouseX, mouseY, delta);
		this.refreshButton.render(matrices, mouseX, mouseY, delta);
		this.cancelButton.render(matrices, mouseX, mouseY, delta);
	}

	@Override
	public void renderBackground(MatrixStack matrices) {
		this.renderBlockTextureAsBackgroundTexture(this.config.getConfigOptions().blockTextureLocation());
	}

	public void renderBlockTextureAsBackgroundTexture(Identifier identifier) {
		this.renderBlockTextureAsBackgroundTexture(identifier, 64, 64, 64, 255);
	}

	public void renderBlockTextureAsBackgroundTexture(Identifier identifier, int red, int green, int blue, int alpha) {
		Tessellator tessellator = Tessellator.getInstance();
		BufferBuilder bufferBuilder = tessellator.getBufferBuilder();
		RenderSystem.setShader(GameRenderer::getPositionTexColorShader);
		RenderSystem.setShaderTexture(0, identifier);
		RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
		bufferBuilder.begin(VertexFormat.DrawMode.QUADS, VertexFormats.POSITION_TEXTURE_COLOR);
		bufferBuilder.vertex(0.0, this.height, 0.0)
				.uv(0.0F, this.height / 32.0F)
				.color(red, green, blue, alpha)
				.next();
		bufferBuilder.vertex(this.width, this.height, 0.0)
				.uv(this.width / 32.0F, this.height / 32.0F)
				.color(red, green, blue, alpha)
				.next();
		bufferBuilder.vertex(this.width, 0.0, 0.0)
				.uv(this.width / 32.0F, 0)
				.color(red, green, blue, alpha)
				.next();
		bufferBuilder.vertex(0.0, 0.0, 0.0)
				.uv(0.0F, 0)
				.color(red, green, blue, alpha)
				.next();
		tessellator.draw();
	}

	public void modifyEntryValue() {
		ConfigElementsListEntry entry = this.configElementsList.getSelectedOrNull();
		if (entry != null) {
			this.configElementsList.modifyParameter(entry);
		}
	}

	public void resetEntryValue() {
		ConfigElementsListEntry entry = this.configElementsList.getSelectedOrNull();
		if (entry != null) {
			this.configElementsList.resetParameter(entry);
			this.disableRefreshing();
		}
	}

	public void saveAndClose() {
		this.config.saveConfig(this.configElementsList.getMutableConfig());
		this.close();
	}

	public void refreshConfig() {
		this.configElementsList.refreshConfigContent(this.config.getContent().getConfigElementsMap());
	}

	public void close() {
		assert this.client != null;
		this.client.setScreen(lastScreen);
	}

	public void disableRefreshing() {
		this.refreshButton.active = false;
	}

	public String getModId() {
		return this.modId;
	}

	public static class BlockTextureLocation extends Identifier {

		public BlockTextureLocation(String location) {
			this("minecraft", location);
		}

		public BlockTextureLocation(String namespace, String location) {
			super(namespace, "textures/block/" + location);
		}
	}
}
