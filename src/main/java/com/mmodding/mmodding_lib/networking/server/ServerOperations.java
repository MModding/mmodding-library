package com.mmodding.mmodding_lib.networking.server;

import com.mmodding.mmodding_lib.MModdingLib;
import com.mmodding.mmodding_lib.library.config.Config;
import com.mmodding.mmodding_lib.library.config.ConfigObject;
import com.mmodding.mmodding_lib.library.events.networking.server.ServerConfigNetworkingEvents;
import com.mmodding.mmodding_lib.networking.MModdingPackets;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.network.ServerPlayerEntity;
import org.quiltmc.loader.api.minecraft.DedicatedServerOnly;
import org.quiltmc.qsl.networking.api.PacketByteBufs;
import org.quiltmc.qsl.networking.api.ServerPlayNetworking;

@DedicatedServerOnly
public class ServerOperations {

	public static void sendConfigToClient(ServerPlayerEntity player, Config config) {
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
				ServerOperations.sendConfigToClient(player, config);
			}
		});

		ServerConfigNetworkingEvents.AFTER_ALL.invoker().afterAllConfigsSent(MModdingLib.CONFIGS);
	}
}
