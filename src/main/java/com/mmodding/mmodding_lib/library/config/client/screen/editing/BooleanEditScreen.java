package com.mmodding.mmodding_lib.library.config.client.screen.editing;

import com.mmodding.mmodding_lib.library.config.ConfigObject;
import com.mmodding.mmodding_lib.library.config.client.screen.ConfigElementsListEntry;
import com.mmodding.mmodding_lib.library.config.client.screen.ConfigElementsListWidget;
import com.mmodding.mmodding_lib.library.config.client.screen.ConfigScreen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.Text;
import org.quiltmc.loader.api.minecraft.ClientOnly;

@ClientOnly
public class BooleanEditScreen extends EditScreen<Boolean> {

	private ButtonWidget booleanValueButton;

	public BooleanEditScreen(ConfigScreen lastScreen, ConfigElementsListWidget list, ConfigElementsListEntry entry, ConfigObject mutableConfig, String fieldName, ConfigObject.Value<Boolean> baseValue) {
		super(lastScreen, list, entry, mutableConfig, fieldName, baseValue);
	}

	@Override
	protected void init() {
		super.init();
		this.booleanValueButton = this.addDrawableChild(new ButtonWidget(this.width / 2 - 75, this.height / 2, 150, 20, Text.of(baseValue.getValue()), button -> this.changeBooleanValue()));
	}

	@Override
	public void render(MatrixStack matrices, int mouseX, int mouseY, float delta) {
		super.render(matrices, mouseX, mouseY, delta);
		this.booleanValueButton.render(matrices, mouseX, mouseY, delta);
	}

	public void changeBooleanValue() {
		boolean booleanValue = Boolean.parseBoolean(this.booleanValueButton.getMessage().getString());
		this.booleanValueButton.setMessage(Text.of(String.valueOf(!booleanValue)));
	}

	@Override
	public void keepAndClose() {
		this.keep(new ConfigObject.Value<>(Boolean.parseBoolean(this.booleanValueButton.getMessage().getString())));
		this.close();
	}
}
