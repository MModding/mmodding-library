package com.mmodding.mmodding_lib.networking.server;

import com.mmodding.mmodding_lib.library.glint.GlintPackView;
import com.mmodding.mmodding_lib.library.config.Config;
import com.mmodding.mmodding_lib.library.config.ConfigObject;
import com.mmodding.mmodding_lib.library.events.server.ServerConfigNetworkingEvents;
import com.mmodding.mmodding_lib.library.events.server.ServerGlintPackNetworkingEvents;
import com.mmodding.mmodding_lib.networking.MModdingPackets;
import net.minecraft.item.Item;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.registry.Registry;
import org.quiltmc.loader.api.minecraft.DedicatedServerOnly;
import org.quiltmc.qsl.networking.api.PacketByteBufs;
import org.quiltmc.qsl.networking.api.ServerPlayNetworking;

import java.util.HashMap;
import java.util.Map;

@DedicatedServerOnly
public class ServerOperations {

	public static void sendConfigToClient(Config config, ServerPlayerEntity player) {
		PacketByteBuf packet = PacketByteBufs.create();

		packet.writeString(config.getQualifier());
		packet.writeString(ConfigObject.Builder.fromConfigObject(config.getContent()).getJsonObject().toString());

		ServerConfigNetworkingEvents.BEFORE.invoker().beforeConfigSent(config);

		ServerPlayNetworking.send(player, MModdingPackets.CONFIGS, packet);

		ServerConfigNetworkingEvents.AFTER.invoker().afterConfigSent(config);
	}

	public static void sendGlintPackToClient(Item item, GlintPackView view, ServerPlayerEntity player) {
		PacketByteBuf packet = PacketByteBufs.create();

		packet.writeIdentifier(Registry.ITEM.getId(item));
		packet.writeIdentifier(view.getIdentifier());

		ServerGlintPackNetworkingEvents.BEFORE.invoker().beforeGlintPackSent(item, view);

		ServerPlayNetworking.send(player, MModdingPackets.GLINT_PACKS, packet);

		ServerGlintPackNetworkingEvents.AFTER.invoker().afterGlintPackSent(item, view);
	}

	public static void sendGlintPacksToClient(ServerPlayerEntity player) {
		Map<Item, GlintPackView> glintPacks = new HashMap<>();

		Registry.ITEM.stream().filter(item -> item.getGlintPackView() != null).forEach(item -> glintPacks.put(item, item.getGlintPackView()));

		ServerGlintPackNetworkingEvents.BEFORE_ALL.invoker().beforeAllGlintPacksSent(glintPacks);

		glintPacks.forEach((item, view) -> ServerOperations.sendGlintPackToClient(item, view, player));

		ServerGlintPackNetworkingEvents.AFTER_ALL.invoker().afterAllGlintPacksSent(glintPacks);
	}
}
