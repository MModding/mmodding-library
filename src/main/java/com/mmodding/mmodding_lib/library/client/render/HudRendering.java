package com.mmodding.mmodding_lib.library.client.render;

import com.mmodding.mmodding_lib.library.utils.Colors;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.minecraft.client.MinecraftClient;
import net.minecraft.text.Text;

public class HudRendering {

	public static void renderTextOnHud(Text text, int x, int y, Colors.RGB color) {
		HudRenderCallback.EVENT.register((matrices, ticks) -> MinecraftClient.getInstance().textRenderer.draw(matrices, text, x, y, color.toDecimal()));
	}
}
