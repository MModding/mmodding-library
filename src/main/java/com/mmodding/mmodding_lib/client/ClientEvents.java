package com.mmodding.mmodding_lib.client;

import com.mmodding.mmodding_lib.library.items.settings.AdvancedItemSettings;
import net.minecraft.text.Text;
import org.jetbrains.annotations.ApiStatus;
import org.quiltmc.loader.api.minecraft.ClientOnly;
import org.quiltmc.qsl.tooltip.api.client.ItemTooltipCallback;

import java.util.List;

@ClientOnly
@ApiStatus.Internal
public class ClientEvents {

	public static void register() {

		ItemTooltipCallback.EVENT.register((stack, player, context, lines) -> {
			Text[] texts = AdvancedItemSettings.DESCRIPTION_LINES.get(stack.getItem());
			if (texts != null) lines.addAll(List.of(texts));
		});
	}
}

