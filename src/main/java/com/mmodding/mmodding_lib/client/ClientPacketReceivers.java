package com.mmodding.mmodding_lib.client;

import com.mmodding.mmodding_lib.library.utils.MModdingIdentifier;
import org.quiltmc.qsl.networking.api.client.ClientPlayNetworking;

public class ClientPacketReceivers {

	public static String FROM_SERVER_CONFIG;

	public static void register() {

		ClientPlayNetworking.registerGlobalReceiver(new MModdingIdentifier("configs"), ((client, handler, buf, responseSender) -> {
			FROM_SERVER_CONFIG = buf.readString();
		}));
	}
}
