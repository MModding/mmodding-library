package com.mmodding.mmodding_lib.client;

import com.google.common.collect.ImmutableList;
import com.mmodding.mmodding_lib.MModdingLib;
import com.mmodding.mmodding_lib.library.client.TemporaryConfig;
import com.mmodding.mmodding_lib.library.config.Config;
import com.mmodding.mmodding_lib.library.config.screen.ConfigScreen;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.Element;
import net.minecraft.client.gui.Selectable;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.narration.NarrationMessageBuilder;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.gui.widget.ElementListWidget;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.ScreenTexts;
import net.minecraft.text.Text;

import java.util.List;

public class MModdingModConfigsScreen extends Screen {

    private final Screen lastScreen;
    private final boolean clientConfigs;

    private MModdingModConfigsListWidget mmoddingModConfigsList;
    private ButtonWidget returnButton;

    public MModdingModConfigsScreen(Screen lastScreen, boolean clientConfigs) {
        super(Text.translatable("mmodding_lib.settings.mod_configs"));
        this.lastScreen = lastScreen;
        this.clientConfigs = clientConfigs;
    }

    @Override
    protected void init() {
        assert this.client != null;
        this.mmoddingModConfigsList = this.addDrawableChild(new MModdingModConfigsListWidget(
            this, this.client, this.clientConfigs, this.width, this.height, 10, this.height - 60, 30
        ));
        this.returnButton = this.addDrawableChild(new ButtonWidget(
            this.width / 2 - 102,
            this.height - 45,
            204,
            20,
            ScreenTexts.DONE,
            button -> this.client.setScreen(this.lastScreen)
        ));
    }

    @Override
    public void render(MatrixStack matrices, int mouseX, int mouseY, float delta) {
        this.mmoddingModConfigsList.render(matrices, mouseX, mouseY, delta);
        this.returnButton.render(matrices, mouseX, mouseY, delta);
    }

	public static class MModdingModConfigsListWidget extends ElementListWidget<MModdingModConfigsListEntry> {

        public MModdingModConfigsListWidget(MModdingModConfigsScreen screen, MinecraftClient client, boolean clientConfigs, int width, int height, int top, int bottom, int itemHeight) {
            super(client, width, height, top, bottom, itemHeight);
            if (clientConfigs) {
                MModdingLibClient.CLIENT_CONFIGS.forEach((qualifier, config) -> {
                    if (!(config instanceof TemporaryConfig)) {
                        this.addEntry(new MModdingModConfigsListEntry(screen, qualifier, config));
                    }
                });
            }
            else {
                MModdingLib.CONFIGS.forEach((qualifier, config) -> this.addEntry(new MModdingModConfigsListEntry(screen, qualifier, config)));
            }
        }

        @Override
        protected int getScrollbarPositionX() {
            return this.width - 3;
        }

        @Override
        public int getRowWidth() {
            return this.width - 20;
        }

        @Override
        public void appendNarrations(NarrationMessageBuilder builder) {
        }
    }

	public static class MModdingModConfigsListEntry extends ElementListWidget.Entry<MModdingModConfigsListEntry> {

        private final MinecraftClient client;
        private final Text configName;
        private final ButtonWidget configButton;

        public MModdingModConfigsListEntry(MModdingModConfigsScreen screen, String qualifier, Config config) {
            this.client = MinecraftClient.getInstance();
            this.configName = config.getConfigOptions().name();
            this.configButton = new ButtonWidget(0, 0, 98, 20, Text.translatable("mmodding_lib.settings.open_config"), button -> this.client.setScreen(
                new ConfigScreen(qualifier, config, screen))
            );
        }

		@Override
		public void render(MatrixStack matrices, int index, int y, int x, int entryWidth, int entryHeight, int mouseX, int mouseY, boolean hovered, float tickDelta) {
            this.client.textRenderer.draw(matrices, this.configName, 5 + x, (float) entryHeight / 3 + y, 16777215);
            this.configButton.x = x + entryWidth - 103;
            this.configButton.y = y;
            this.configButton.render(matrices, mouseX, mouseY, tickDelta);
		}

        @Override
        public List<? extends Element> children() {
            return ImmutableList.of(this.configButton);
        }

        @Override
        public List<? extends Selectable> selectableChildren() {
            return ImmutableList.of(this.configButton);
        }

        @Override
        public boolean mouseClicked(double mouseX, double mouseY, int button) {
            return this.configButton.mouseClicked(mouseX, mouseY, button);
        }

        @Override
        public boolean mouseReleased(double mouseX, double mouseY, int button) {
            return this.configButton.mouseReleased(mouseX, mouseY, button);
        }
    }
}
