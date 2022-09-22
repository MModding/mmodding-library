package com.mmodding.mmodding_lib;

import com.mmodding.mmodding_lib.library.items.CustomItemSettings;
import net.minecraft.text.Text;
import org.quiltmc.qsl.tooltip.api.client.ItemTooltipCallback;

import java.util.List;

public class Events {

	public static void register() {

		ItemTooltipCallback.EVENT.register((stack, player, context, lines) -> {
			Text[] texts = CustomItemSettings.DESCRIPTION_LINES.get(stack.getItem());
			if (texts != null) lines.addAll(List.of(CustomItemSettings.DESCRIPTION_LINES.get(stack.getItem())));
		});
	}
}
