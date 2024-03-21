package com.mmodding.mmodding_lib.library.utils;

import com.mmodding.mmodding_lib.mixin.accessors.StyleAccessor;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.text.*;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.Nullable;

public class TextUtils {

	public static Text spaceBetween(MutableText text, Text... siblings) {
		for (Text sibling : siblings) {
			text.append("").append(sibling);
		}
		return text;
	}

	public static void drawCenteredTrimmed(TextRenderer textRenderer, StringVisitable text, int x, int maxWidth, int startY, int endY, int color) {
		int lines = textRenderer.wrapLines(text, maxWidth).size();
		int height = lines * 9;
		int anchor = (endY - startY) / 2;
		textRenderer.drawTrimmed(text, x, startY + anchor - height / 2, maxWidth, color);
	}

	public static Style createStyle(
		@Nullable TextColor color,
		@Nullable Boolean bold,
		@Nullable Boolean italic,
		@Nullable Boolean underlined,
		@Nullable Boolean strikethrough,
		@Nullable Boolean obfuscated,
		@Nullable ClickEvent clickEvent,
		@Nullable HoverEvent hoverEvent,
		@Nullable String insertion,
		@Nullable Identifier font
	) {
		return StyleAccessor.mmodding_lib$init(color, bold, italic, underlined, strikethrough, obfuscated, clickEvent, hoverEvent, insertion, font);
	}
}
