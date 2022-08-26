package com.mmodding.mmodding_lib.library.config;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.ScreenTexts;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.render.*;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;

public class ConfigScreen extends Screen {

	private final String modId;
	private final Config config;
	private final Screen lastScreen;
	private boolean initialized;

	private ConfigElementsListWidget configElementsList;
	private ButtonWidget doneButton;
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
			this.configElementsList.updateSize(this.width, this.height, 10, this.height - 40);
		}
		else {
			this.initialized = true;
			this.configElementsList = new ConfigElementsListWidget(this.config, this, this.client, this.width, this.height, 10, this.height - 40, 30);
			this.configElementsList.addConfigContent(this.config.getContent().getConfigElementsMap());
		}
		this.addSelectableChild(this.configElementsList);
		this.doneButton = this.addDrawableChild(new ButtonWidget(
				this.width / 2 - 155,
				this.height - 30,
				150,
				20,
				ScreenTexts.DONE,
				button -> this.saveAndClose()
		));
		this.cancelButton = this.addDrawableChild(new ButtonWidget(
				this.width / 2 + 5,
				this.height - 30,
				150,
				20,
				ScreenTexts.CANCEL,
				button -> this.close()
		));
	}

	public void select(ConfigElementListEntry entry) {
		this.configElementsList.setSelected(entry);
	}

	@Override
	public void render(MatrixStack matrices, int mouseX, int mouseY, float delta) {
		this.configElementsList.render(matrices, mouseX, mouseY, delta);
		this.doneButton.render(matrices, mouseX, mouseY, delta);
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
		BufferBuilder bufferBuilder = tessellator.getBuffer();
		RenderSystem.setShader(GameRenderer::getPositionTexColorShader);
		RenderSystem.setShaderTexture(0, identifier);
		RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
		bufferBuilder.begin(VertexFormat.DrawMode.QUADS, VertexFormats.POSITION_TEXTURE_COLOR);
		bufferBuilder.vertex(0.0, this.height, 0.0)
				.texture(0.0F, this.height / 32.0F)
				.color(red, green, blue, alpha)
				.next();
		bufferBuilder.vertex(this.width, this.height, 0.0)
				.texture(this.width / 32.0F, this.height / 32.0F)
				.color(red, green, blue, alpha)
				.next();
		bufferBuilder.vertex(this.width, 0.0, 0.0)
				.texture(this.width / 32.0F, 0)
				.color(red, green, blue, alpha)
				.next();
		bufferBuilder.vertex(0.0, 0.0, 0.0)
				.texture(0.0F, 0)
				.color(red, green, blue, alpha)
				.next();
		tessellator.draw();
	}

	public void saveAndClose() {
		this.close();
	}

	public void close() {
		assert this.client != null;
		this.client.setScreen(lastScreen);
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
