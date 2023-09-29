package com.mmodding.mmodding_lib.networking.server;

import com.mmodding.mmodding_lib.MModdingLib;
import com.mmodding.mmodding_lib.ducks.ServerStellarStatusDuckInterface;
import com.mmodding.mmodding_lib.library.events.networking.server.ServerStellarStatusNetworkingEvents;
import com.mmodding.mmodding_lib.library.glint.GlintPackView;
import com.mmodding.mmodding_lib.library.config.Config;
import com.mmodding.mmodding_lib.library.config.ConfigObject;
import com.mmodding.mmodding_lib.library.events.networking.server.ServerConfigNetworkingEvents;
import com.mmodding.mmodding_lib.library.events.networking.server.ServerGlintPackNetworkingEvents;
import com.mmodding.mmodding_lib.library.stellar.StellarStatus;
import com.mmodding.mmodding_lib.networking.MModdingPackets;
import net.minecraft.item.Item;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Identifier;
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

	public static void sendConfigsToClient(ServerPlayerEntity player) {

		ServerConfigNetworkingEvents.BEFORE_ALL.invoker().beforeAllConfigsSent(MModdingLib.CONFIGS);

		MModdingLib.CONFIGS.forEach((qualifier, config) -> {
			boolean isLocalCache = config.getNetworkingSate() == Config.NetworkingState.LOCAL_CACHES;
			boolean isClientCache = config.getNetworkingSate() == Config.NetworkingState.CLIENT_CACHES;
			if (isLocalCache || isClientCache) {
				ServerOperations.sendConfigToClient(config, player);
			}
		});

		ServerConfigNetworkingEvents.AFTER_ALL.invoker().afterAllConfigsSent(MModdingLib.CONFIGS);
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

	public static void sendStellarStatusToClient(Identifier identifier, StellarStatus status, ServerPlayerEntity player) {
		PacketByteBuf packet = PacketByteBufs.create();

		packet.writeIdentifier(identifier);
		packet.writeLong(status.getCurrentTime());
		packet.writeLong(status.getFullTime());

		ServerStellarStatusNetworkingEvents.BEFORE.invoker().beforeStellarStatusSent(identifier, status);

		ServerPlayNetworking.send(player, MModdingPackets.STELLAR_STATUS, packet);

		ServerStellarStatusNetworkingEvents.AFTER.invoker().afterStellarStatusSent(identifier, status);
	}

	public static void sendAllStellarStatusToClient(ServerPlayerEntity player) {

        Map<Identifier, StellarStatus> stellarStatus = new HashMap<>(((ServerStellarStatusDuckInterface) player.getWorld()).mmodding_lib$getAllStellarStatus());

		ServerStellarStatusNetworkingEvents.BEFORE_ALL.invoker().beforeAllStellarStatusSent(stellarStatus);

		stellarStatus.forEach((identifier, status) -> ServerOperations.sendStellarStatusToClient(identifier, status, player));

		ServerStellarStatusNetworkingEvents.AFTER_ALL.invoker().afterAllStellarStatusSent(stellarStatus);
	}
}
