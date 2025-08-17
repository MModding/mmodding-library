package com.mmodding.mmodding_lib.client;

import com.mmodding.mmodding_lib.library.caches.CacheAccess;
import com.mmodding.mmodding_lib.library.caches.Caches;
import com.mmodding.mmodding_lib.library.client.tooltip.InventoryTooltipComponent;
import com.mmodding.mmodding_lib.library.debug.WatcherManager;
import com.mmodding.mmodding_lib.library.glint.GlintPackView;
import com.mmodding.mmodding_lib.library.client.utils.MModdingClientGlobalMaps;
import com.mmodding.mmodding_lib.library.items.settings.AdvancedItemSettings;
import com.mmodding.mmodding_lib.library.items.tooltip.data.InventoryTooltipData;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.item.v1.ItemTooltipCallback;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayConnectionEvents;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.fabricmc.fabric.api.client.rendering.v1.TooltipComponentCallback;
import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.tooltip.TooltipComponent;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.client.item.TooltipData;
import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import org.apache.commons.lang3.tuple.Pair;
import org.jetbrains.annotations.ApiStatus;

import java.util.List;
import java.util.UUID;
import java.util.function.Predicate;

@ApiStatus.Internal
@Environment(EnvType.CLIENT)
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

	private static void itemTooltipCallback(ItemStack stack, TooltipContext context, List<Text> lines) {
		Text[] texts = AdvancedItemSettings.DESCRIPTION_LINES.get(stack.getItem());
		if (texts != null) lines.addAll(List.of(texts));
	}

	private static void renderWatchers(MatrixStack matrices, float ticks) {
		ClientWorld world = MinecraftClient.getInstance().world;
		if (world != null) {
			TextRenderer renderer = MinecraftClient.getInstance().textRenderer;
			final int gap = renderer.fontHeight + 2;
			int y = 10;
			for (var entry : WatcherManager.getEntries()) {
				UUID uuid = entry.getKey();
				Text entityLabel = Text.literal("[Entity UUID: " + uuid + "]").formatted(Formatting.AQUA);
				renderer.draw(matrices, entityLabel, 10, y, 0);
				y += gap;
				for (var field : entry.getValue().entrySet()) {
					Text valueLabel = Text.literal(String.valueOf(field.getValue().get())).formatted(Formatting.GREEN);
					Text fieldLabel = Text.literal(field.getKey() + ": ").formatted(Formatting.RED).append(valueLabel);
					renderer.draw(matrices, fieldLabel, 20, y, 0);
					y += gap;
				}
			}
		}
	}

	public static void register() {
		ClientPlayConnectionEvents.INIT.register(ClientEvents::serverInit);
		ClientPlayConnectionEvents.JOIN.register(ClientEvents::serverJoin);
		ClientPlayConnectionEvents.DISCONNECT.register(ClientEvents::serverDisconnect);
		TooltipComponentCallback.EVENT.register(ClientEvents::tooltipComponentCallback);
		ItemTooltipCallback.EVENT.register(ClientEvents::itemTooltipCallback);
		HudRenderCallback.EVENT.register(ClientEvents::renderWatchers);
	}
}
