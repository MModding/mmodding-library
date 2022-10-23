package com.mmodding.mmodding_lib;

import com.mmodding.mmodding_lib.library.config.ConfigObject;
import com.mmodding.mmodding_lib.library.utils.MModdingIdentifier;
import net.minecraft.network.PacketByteBuf;
import org.quiltmc.qsl.networking.api.PacketByteBufs;
import org.quiltmc.qsl.networking.api.ServerPlayNetworking;
import org.quiltmc.qsl.networking.api.client.ClientPlayNetworking;

public class PacketReceivers {

	public static void register() {

		ServerPlayNetworking.registerGlobalReceiver(new MModdingIdentifier("config-requests"), ((server, player, handler, buf, responseSender) -> {
			String configName = buf.readString();
			MModdingLib.configs.forEach((string, config) -> {
				if (configName.equals(string)) {
					PacketByteBuf packet = PacketByteBufs.create();
					packet.writeString(ConfigObject.Builder.fromConfigObject(config.getContent()).getJsonObject().toString());
					ClientPlayNetworking.send(new MModdingIdentifier("configs"), buf);
				}
			});
		}));
	}
}
