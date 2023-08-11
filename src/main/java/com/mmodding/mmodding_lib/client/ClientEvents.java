package com.mmodding.mmodding_lib.client;

import com.mmodding.mmodding_lib.glint.GlintPackView;
import com.mmodding.mmodding_lib.library.client.utils.MModdingClientGlobalMaps;
import com.mmodding.mmodding_lib.library.items.settings.AdvancedItemSettings;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.item.TooltipContext;
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
		if (MModdingLibClient.MMODDING_LIBRARY_CLIENT_CONFIG.getContent().getBoolean("showMModdingLibraryClientCaches")) {
			ClientCaches.debugCaches();
		}
	}

	private static void serverDisconnect(ClientPlayNetworkHandler handler, MinecraftClient client) {
		ClientCaches.avoidCaches();
	}

	private static void itemTooltipCallback(ItemStack stack, PlayerEntity player, TooltipContext context, List<Text> lines) {
		Text[] texts = AdvancedItemSettings.DESCRIPTION_LINES.get(stack.getItem());
		if (texts != null) lines.addAll(List.of(texts));
	}

	public static void register() {
		ClientPlayConnectionEvents.INIT.register(ClientEvents::serverInit);
		ClientPlayConnectionEvents.JOIN.register(ClientEvents::serverJoin);
		ClientPlayConnectionEvents.DISCONNECT.register(ClientEvents::serverDisconnect);
		ItemTooltipCallback.EVENT.register(ClientEvents::itemTooltipCallback);
	}
}
