package com.mmodding.mmodding_lib.library.utils;

import net.minecraft.client.font.TextRenderer;
import net.minecraft.text.MutableText;
import net.minecraft.text.StringVisitable;
import net.minecraft.text.Text;
import net.minecraft.util.math.MathHelper;

public class TextUtils {

	public static Text spaceBetween(MutableText text, Text... siblings) {
		for (Text sibling : siblings) {
			text.append(" ").append(sibling);
		}
		return text;
	}

	public static void drawCenteredTrimmed(TextRenderer textRenderer, StringVisitable text, int x, int maxWidth, int startY, int startZ, int color) {
		int lines = textRenderer.wrapLines(text, maxWidth).size();
		int height = lines * 9;
		int anchor = MathHelper.abs(startY - startZ);
		textRenderer.drawTrimmed(text, x, startY + anchor - height / 2, maxWidth, color);
	}
}
