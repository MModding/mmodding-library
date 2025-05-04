package com.mmodding.mmodding_lib.library.config.client.screen.editing;

import com.mmodding.mmodding_lib.library.config.ConfigObject;
import com.mmodding.mmodding_lib.library.config.client.screen.ConfigElementsListEntry;
import com.mmodding.mmodding_lib.library.config.client.screen.ConfigElementsListWidget;
import com.mmodding.mmodding_lib.library.config.client.screen.ConfigScreen;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.BufferBuilder;
import com.mojang.blaze3d.vertex.Tessellator;
import com.mojang.blaze3d.vertex.VertexFormat;
import com.mojang.blaze3d.vertex.VertexFormats;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.Text;

@Environment(EnvType.CLIENT)
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
		this.keepButton = this.addDrawableChild(new ButtonWidget(this.width / 2 - 155, this.height - 42, 150, 20, Text.of("Keep"), button -> this.keepAndClose()));
		this.cancelButton = this.addDrawableChild(new ButtonWidget(this.width / 2 + 5, this.height - 42, 150, 20, Text.of("Cancel"), button -> this.close()));
	}

	@Override
	public void render(MatrixStack matrices, int mouseX, int mouseY, float delta) {
		this.renderBackground(matrices);
		this.keepButton.render(matrices, mouseX, mouseY, delta);
		this.cancelButton.render(matrices, mouseX, mouseY, delta);
	}

	@Override
	public void renderBackground(MatrixStack matrices) {
		Tessellator tessellator = Tessellator.getInstance();
		BufferBuilder bufferBuilder = tessellator.getBufferBuilder();
		RenderSystem.setShader(GameRenderer::getPositionTexColorShader);
		RenderSystem.setShaderTexture(0, this.list.getConfig().getConfigOptions().textureLocation());
		RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
		bufferBuilder.begin(VertexFormat.DrawMode.QUADS, VertexFormats.POSITION_TEXTURE_COLOR);
		bufferBuilder.vertex(0.0, this.height, 0.0)
				.uv(0.0F, this.height / 32.0F)
				.color(64, 64, 64, 255)
				.next();
		bufferBuilder.vertex(this.width, this.height, 0.0)
				.uv(this.width / 32.0F, this.height / 32.0F)
				.color(64, 64, 64, 255)
				.next();
		bufferBuilder.vertex(this.width, 0.0, 0.0)
				.uv(this.width / 32.0F, 0)
				.color(64, 64, 64, 255)
				.next();
		bufferBuilder.vertex(0.0, 0.0, 0.0)
				.uv(0.0F, 0)
				.color(64, 64, 64, 255)
				.next();
		tessellator.draw();
	}

	public abstract void keepAndClose();

	protected void keep(ConfigObject.Value<T> newValue) {
		this.list.children().set(this.entry.getIndex(), new ConfigElementsListEntry(this.lastScreen, this.entry.getIndex(), this.fieldName, newValue));
		ConfigObject.Builder builder = ConfigObject.Builder.fromConfigObject(this.mutableConfig);
		builder.addParameter(this.fieldName, newValue);
		this.mutableConfig = builder.build();
	}

	public void close() {
		assert this.client != null;
		this.client.setScreen(lastScreen);
	}
}
