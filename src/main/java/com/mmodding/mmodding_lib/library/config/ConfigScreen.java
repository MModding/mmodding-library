package com.mmodding.mmodding_lib.library.config;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.render.*;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;

public class ConfigScreen extends Screen implements AutoCloseable {

	private Config config;
	private Screen lastScreen;
	private boolean initialized;

	private ConfigElementsListWidget configElementsList;

	public ConfigScreen(Config config, Screen lastScreen) {
		super(config.getConfigOptions().name());
		this.config = config;
		this.lastScreen = lastScreen;
	}

	@Override
	protected void init() {
		assert this.client != null;
		this.client.keyboard.setRepeatEvents(true);
		if (this.initialized) {
			this.configElementsList.updateSize(this.width, this.height, 32, this.height - 64);
		}
		else {
			this.initialized = true;
			this.configElementsList = new ConfigElementsListWidget(this.client, this.width, this.height, 32, this.height - 64, 36);
			this.configElementsList.addConfigContent(this.config.getContent().getConfigElementsMap());
		}
	}

	@Override
	public void render(MatrixStack matrices, int mouseX, int mouseY, float delta) {
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

	@Override
	public void close() {
		assert this.client != null;
		this.client.setScreen(lastScreen);
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
