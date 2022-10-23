package com.mmodding.mmodding_lib.client;

import com.mmodding.mmodding_lib.library.items.CustomItemSettings;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.text.Text;
import org.quiltmc.qsl.tooltip.api.client.ItemTooltipCallback;

import java.util.List;

@Environment(EnvType.CLIENT)
public class ClientEvents {

	public static void register() {

		ItemTooltipCallback.EVENT.register((stack, player, context, lines) -> {
			Text[] texts = CustomItemSettings.DESCRIPTION_LINES.get(stack.getItem());
			if (texts != null) lines.addAll(List.of(texts));
		});
	}
}
