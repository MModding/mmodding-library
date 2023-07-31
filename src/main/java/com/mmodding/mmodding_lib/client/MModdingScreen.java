package com.mmodding.mmodding_lib.client;

import com.mmodding.mmodding_lib.MModdingLib;
import com.mmodding.mmodding_lib.library.config.client.screen.ConfigScreen;
import com.mmodding.mmodding_lib.library.utils.Colors;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.ScreenTexts;
import net.minecraft.text.Text;
import org.jetbrains.annotations.ApiStatus;
import org.quiltmc.loader.api.minecraft.ClientOnly;

@ClientOnly
@ApiStatus.Internal
public class MModdingScreen extends Screen {

	private final Screen lastScreen;

	private ButtonWidget libraryConfigButton;
	private ButtonWidget modConfigsButton;
	private ButtonWidget modClientConfigsButton;
	private ButtonWidget returnButton;

	public MModdingScreen(Screen lastScreen) {
		super(Text.translatable("mmodding_lib.settings.title"));
		this.lastScreen = lastScreen;
	}

	@Override
	protected void init() {
		assert this.client != null;
		this.client.keyboard.setRepeatEvents(true);
		this.libraryConfigButton = this.addDrawableChild(new ButtonWidget(
			this.width / 2 - 102,
			this.height / 4 + 72 - 16,
			204,
			20,
			Text.translatable("mmodding_lib.settings.library_config"),
			button -> this.client.setScreen(new ConfigScreen("mmodding_lib", MModdingLib.MMODDING_LIBRARY_CONFIG, this))
		));
		this.modConfigsButton = this.addDrawableChild(new ButtonWidget(
			this.width / 2 - 102,
			this.height / 4 + 96 - 16,
			204,
			20,
			Text.translatable("mmodding_lib.settings.mod_configs"),
			button -> this.client.setScreen(new MModdingModConfigsScreen(this, false))
		));
		this.modClientConfigsButton = this.addDrawableChild(new ButtonWidget(
			this.width / 2 - 102,
			this.height / 4 + 120 - 16,
			204,
			20,
			Text.translatable("mmodding_lib.settings.mod_client_configs"),
			button -> this.client.setScreen(new MModdingModConfigsScreen(this, true))
		));
		this.returnButton = this.addDrawableChild(new ButtonWidget(
			this.width / 2 - 102,
			this.height / 4 + 144 - 16,
			204,
			20,
			ScreenTexts.DONE,
			button -> this.client.setScreen(this.lastScreen)
		));
	}

	@Override
	public void render(MatrixStack matrices, int mouseX, int mouseY, float delta) {
		this.renderBackground(matrices);
		RenderSystem.setShader(GameRenderer::getPositionTexShader);
		RenderSystem.setShaderTexture(0, MModdingLib.createId("title.png"));
		Screen.drawTexture(matrices, this.width / 2 - 102, 30, 0, 0, 204, 73, 204, 73);
		this.libraryConfigButton.render(matrices, mouseX, mouseY, delta);
		this.modConfigsButton.render(matrices, mouseX, mouseY, delta);
		this.modClientConfigsButton.render(matrices, mouseX, mouseY, delta);
		this.returnButton.render(matrices, mouseX, mouseY, delta);
	}

	@Override
	public void renderBackground(MatrixStack matrices) {
		Colors.ARGB colorStart = Colors.ARGB.fromDecimal(-1072689136);
		colorStart.setAlpha(150);
		colorStart.alterBlue(20);
		Colors.ARGB colorEnd = Colors.ARGB.fromDecimal(-804253680);
		colorEnd.setAlpha(150);
		colorEnd.alterBlue(20);
		this.fillGradient(matrices, 0, 0, this.width, this.height, colorStart.toDecimal(), colorEnd.toDecimal());
	}
}
