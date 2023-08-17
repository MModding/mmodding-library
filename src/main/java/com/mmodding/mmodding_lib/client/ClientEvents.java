package com.mmodding.mmodding_lib.client;

import com.mmodding.mmodding_lib.library.caches.CacheAccess;
import com.mmodding.mmodding_lib.library.caches.Caches;
import com.mmodding.mmodding_lib.library.client.tooltip.InventoryTooltipComponent;
import com.mmodding.mmodding_lib.library.glint.GlintPackView;
import com.mmodding.mmodding_lib.library.client.utils.MModdingClientGlobalMaps;
import com.mmodding.mmodding_lib.library.items.settings.AdvancedItemSettings;
import com.mmodding.mmodding_lib.library.items.tooltip.data.InventoryTooltipData;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.tooltip.TooltipComponent;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.client.item.TooltipData;
import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.util.Pair;
import org.jetbrains.annotations.ApiStatus;
import org.quiltmc.loader.api.minecraft.ClientOnly;
import org.quiltmc.qsl.networking.api.PacketSender;
import org.quiltmc.qsl.networking.api.client.ClientPlayConnectionEvents;
import org.quiltmc.qsl.tooltip.api.client.ItemTooltipCallback;
import org.quiltmc.qsl.tooltip.api.client.TooltipComponentCallback;

import java.util.List;
import java.util.function.Predicate;

@ClientOnly
@ApiStatus.Internal
public class ClientEvents {

	private static void serverInit(ClientPlayNetworkHandler handler, MinecraftClient client) {
		MModdingClientGlobalMaps.getGlintPackOverrideKeys().forEach(item -> {
			Pair<GlintPackView, Predicate<Item>> pair = MModdingClientGlobalMaps.getGlintPackOverride(item);
			if (pair.getRight().test(item)) {
				ClientCaches.GLINT_PACK_OVERRIDES.put(item, pair.getLeft());
			}
		});
	}

	private static void serverJoin(ClientPlayNetworkHandler handler, PacketSender sender, MinecraftClient client) {
		if (MModdingLibClient.LIBRARY_CLIENT_CONFIG.getContent().getBoolean("showMModdingLibraryClientCaches")) {
			Caches.CLIENT.forEach(CacheAccess::debugCache);
		}
	}

	private static void serverDisconnect(ClientPlayNetworkHandler handler, MinecraftClient client) {
		Caches.CLIENT.forEach(CacheAccess::clearCache);
	}

	private static TooltipComponent tooltipComponentCallback(TooltipData data) {
		if (data instanceof InventoryTooltipData inventoryTooltipData) {
			return new InventoryTooltipComponent(inventoryTooltipData);
		}
		else {
			return null;
		}
	}

	private static void itemTooltipCallback(ItemStack stack, PlayerEntity player, TooltipContext context, List<Text> lines) {
		Text[] texts = AdvancedItemSettings.DESCRIPTION_LINES.get(stack.getItem());
		if (texts != null) lines.addAll(List.of(texts));
	}

	public static void register() {
		ClientPlayConnectionEvents.INIT.register(ClientEvents::serverInit);
		ClientPlayConnectionEvents.JOIN.register(ClientEvents::serverJoin);
		ClientPlayConnectionEvents.DISCONNECT.register(ClientEvents::serverDisconnect);
		TooltipComponentCallback.EVENT.register(ClientEvents::tooltipComponentCallback);
		ItemTooltipCallback.EVENT.register(ClientEvents::itemTooltipCallback);
	}
}
