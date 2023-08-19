package com.mmodding.mmodding_lib.client;

import com.mmodding.mmodding_lib.networking.MModdingPackets;
import com.mmodding.mmodding_lib.networking.client.ClientOperations;
import org.jetbrains.annotations.ApiStatus;
import org.quiltmc.loader.api.minecraft.ClientOnly;
import org.quiltmc.qsl.networking.api.client.ClientPlayNetworking;

@ClientOnly
@ApiStatus.Internal
public class ClientPacketReceivers {

	public static void register() {
		ClientPlayNetworking.registerGlobalReceiver(MModdingPackets.CONFIGS, ((client, handler, buf, responseSender) -> ClientOperations.receiveConfigOnClient(buf)));
		ClientPlayNetworking.registerGlobalReceiver(MModdingPackets.GLINT_PACKS, ((client, handler, buf, responseSender) -> ClientOperations.receiveGlintPackOnClient(buf)));
		ClientPlayNetworking.registerGlobalReceiver(MModdingPackets.STELLAR_STATUS, (((client, handler, buf, responseSender) -> ClientOperations.receiveStellarStatusOnClient(handler, buf))));
	}
}
