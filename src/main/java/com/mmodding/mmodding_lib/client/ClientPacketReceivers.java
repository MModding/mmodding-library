package com.mmodding.mmodding_lib.client;

import com.mmodding.mmodding_lib.networking.MModdingPackets;
import com.mmodding.mmodding_lib.networking.client.ClientOperations;
import net.minecraft.entity.Entity;
import org.jetbrains.annotations.ApiStatus;
import org.quiltmc.loader.api.minecraft.ClientOnly;
import org.quiltmc.qsl.networking.api.client.ClientPlayNetworking;

@ClientOnly
@ApiStatus.Internal
public class ClientPacketReceivers {

	public static void register() {

		// Syncable Data Elements Networking
		ClientPlayNetworking.registerGlobalReceiver(MModdingPackets.SYNCABLE_DATA, (((client, handler, buf, sender) -> {
			int entityId = buf.readVarInt();
			assert client.world != null;
			Entity entity = client.world.getEntityById(entityId);
			assert entity != null;
			entity.getSyncableDataRegistry().accept(buf);
		})));

		// Client Operations Networking
		ClientPlayNetworking.registerGlobalReceiver(MModdingPackets.CONFIGS, ((client, handler, buf, sender) -> ClientOperations.receiveConfigOnClient(buf)));
		ClientPlayNetworking.registerGlobalReceiver(MModdingPackets.GLINT_PACKS, ((client, handler, buf, sender) -> ClientOperations.receiveGlintPackOnClient(buf)));
		ClientPlayNetworking.registerGlobalReceiver(MModdingPackets.STELLAR_STATUS, (((client, handler, buf, sender) -> ClientOperations.receiveStellarStatusOnClient(handler, buf))));
	}
}
