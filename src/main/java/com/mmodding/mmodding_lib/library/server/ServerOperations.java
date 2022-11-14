package com.mmodding.mmodding_lib.library.server;

import com.mmodding.mmodding_lib.library.config.Config;
import com.mmodding.mmodding_lib.library.config.ConfigObject;
import com.mmodding.mmodding_lib.library.events.server.ServerConfigNetworkingEvents;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Identifier;
import org.quiltmc.loader.api.minecraft.DedicatedServerOnly;
import org.quiltmc.qsl.networking.api.PacketByteBufs;
import org.quiltmc.qsl.networking.api.ServerPlayNetworking;

@DedicatedServerOnly
public class ServerOperations {

	public static void sendConfigToClient(Config config, ServerPlayerEntity player) {
		PacketByteBuf packet = PacketByteBufs.create();
		packet.writeString(config.getConfigName());
		packet.writeString(ConfigObject.Builder.fromConfigObject(config.getContent()).getJsonObject().toString());

		ServerConfigNetworkingEvents.BEFORE.invoker().beforeConfigSent(config);

		ServerPlayNetworking.send(player, new Identifier("configs-channel"), packet);

		ServerConfigNetworkingEvents.AFTER.invoker().afterConfigSent(config);
	}
}
