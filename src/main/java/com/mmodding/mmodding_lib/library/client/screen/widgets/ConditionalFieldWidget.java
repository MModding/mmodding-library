package com.mmodding.mmodding_lib.library.client.screen.widgets;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.widget.TextFieldWidget;
import net.minecraft.text.Text;

import java.util.function.Predicate;

@Environment(EnvType.CLIENT)
public class ConditionalFieldWidget extends TextFieldWidget {

	private final Predicate<String> conditionPredicate;

	public ConditionalFieldWidget(TextRenderer textRenderer, int x, int y, int width, int height, Text text, Predicate<String> condition) {
		super(textRenderer, x, y, width, height, text);
		this.conditionPredicate = condition;
	}

	@Override
	public boolean charTyped(char chr, int modifiers) {
		if (this.conditionPredicate.test(String.valueOf(chr)))
			return super.charTyped(chr, modifiers);
		return false;
	}
}
