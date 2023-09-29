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
		ClientPlayNetworking.registerGlobalReceiver(MModdingPackets.LIVING_ENTITY_STUCK_ARROW_TYPES, (((client, handler, buf, sender) -> ClientOperations.receiveLivingEntityStuckArrowTypesToClient(client, buf))));
		ClientPlayNetworking.registerGlobalReceiver(MModdingPackets.CONFIGS, ((client, handler, buf, sender) -> ClientOperations.receiveConfigOnClient(buf)));
		ClientPlayNetworking.registerGlobalReceiver(MModdingPackets.GLINT_PACKS, ((client, handler, buf, sender) -> ClientOperations.receiveGlintPackOnClient(buf)));
		ClientPlayNetworking.registerGlobalReceiver(MModdingPackets.STELLAR_STATUS, (((client, handler, buf, sender) -> ClientOperations.receiveStellarStatusOnClient(handler, buf))));
	}
}
