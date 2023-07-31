package com.mmodding.mmodding_lib.library.client.screen.widgets;

import net.minecraft.client.font.TextRenderer;
import net.minecraft.text.Text;
import org.apache.commons.lang3.math.NumberUtils;

public class NumberFieldWidget extends ConditionalFieldWidget {

	public NumberFieldWidget(TextRenderer textRenderer, int x, int y, int width, int height, Text text) {
		super(textRenderer, x, y, width, height, text, NumberUtils::isCreatable);
	}

	public int getNumber() {
		String stringNumber = this.getText();
		if (this.getText().length() >= 10) stringNumber = this.getText().substring(0, 9);
		return !this.getText().isEmpty() ? Integer.parseInt(stringNumber) : 0;
	}
}
