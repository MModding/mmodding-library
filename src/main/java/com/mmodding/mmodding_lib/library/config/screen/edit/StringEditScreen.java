package com.mmodding.mmodding_lib.library.config.screen.edit;

import com.mmodding.mmodding_lib.library.config.ConfigObject;
import com.mmodding.mmodding_lib.library.config.screen.ConfigElementsListEntry;
import com.mmodding.mmodding_lib.library.config.screen.ConfigElementsListWidget;
import com.mmodding.mmodding_lib.library.config.screen.ConfigScreen;
import net.minecraft.client.gui.widget.TextFieldWidget;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.Text;

public class StringEditScreen extends EditScreen<String> {

	private TextFieldWidget stringBox;

	public StringEditScreen(ConfigScreen lastScreen, ConfigElementsListWidget list, ConfigElementsListEntry entry, ConfigObject mutableConfig, String fieldName, ConfigObject.Value<String> baseValue) {
		super(lastScreen, list, entry, mutableConfig, fieldName, baseValue);
	}

	@Override
	protected void init() {
		super.init();
		this.addSelectableChild(this.stringBox = new TextFieldWidget(this.textRenderer, this.height / 2, this.width / 2 - 75, 150, 20, Text.of(this.baseValue.getValue())));
	}

	@Override
	public void render(MatrixStack matrices, int mouseX, int mouseY, float delta) {
		super.render(matrices, mouseX, mouseY, delta);
		this.stringBox.render(matrices, mouseX, mouseY, delta);
	}

	@Override
	public void keepAndClose() {
		this.keep(new ConfigObject.Value<>(this.stringBox.getText()));
		this.close();
	}
}
