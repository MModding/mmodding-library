package com.mmodding.mmodding_lib.client;

import com.mmodding.mmodding_lib.library.network.request.c2s.ClientPendingRequestManager;
import com.mmodding.mmodding_lib.library.network.request.s2c.ClientRequestHandler;
import com.mmodding.mmodding_lib.library.network.support.NetworkSupport;
import com.mmodding.mmodding_lib.library.network.support.type.NetworkList;
import com.mmodding.mmodding_lib.library.network.support.type.NetworkMap;
import com.mmodding.mmodding_lib.library.utils.MModdingIdentifier;
import com.mmodding.mmodding_lib.networking.MModdingPackets;
import com.mmodding.mmodding_lib.networking.client.ClientOperations;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.Entity;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.ApiStatus;

import java.util.Map;
import java.util.UUID;

@ApiStatus.Internal
@Environment(EnvType.CLIENT)
public class ClientPacketReceivers {

	@SuppressWarnings("unchecked")
	public static <T extends NetworkSupport> void register() {

		// S2C Requests Networking
		ClientPlayNetworking.registerGlobalReceiver(MModdingPackets.S2C_REQUESTS, (server, handler, buf, sender) -> {
			Identifier requestHandler = buf.readIdentifier();
			Map<UUID, NetworkList> requestMap = buf.readMap(PacketByteBuf::readUuid, NetworkSupport::readComplete);
			PacketByteBuf packet = PacketByteBufs.create();
			NetworkMap responseMap = new NetworkMap();
			requestMap.forEach((uuid, args) -> {
				T value = (T) ClientRequestHandler.HANDLERS.get(requestHandler).respond(MinecraftClient.getInstance().player, args);
				responseMap.put(new MModdingIdentifier(uuid.toString()), (Class<T>) value.getClass(), value);
			});
			packet.writeIdentifier(requestHandler);
			responseMap.writeComplete(packet);
			ClientPlayNetworking.send(MModdingPackets.S2C_RESPONSES, packet);
		});

		// C2S Responses Networking
		ClientPlayNetworking.registerGlobalReceiver(MModdingPackets.C2S_RESPONSES, (client, handler, buf, response) -> {
			Identifier manager = buf.readIdentifier();
			NetworkMap map = NetworkSupport.readComplete(buf);
			map.forEach(
				(identifier, type, value) -> ClientPendingRequestManager.getManager(manager)
					.consume(UUID.fromString(identifier.getPath()))
					.ifPresent(action -> action.cast(value))
			);
		});

		// Syncable Data Elements Networking
		ClientPlayNetworking.registerGlobalReceiver(MModdingPackets.SYNCABLE_DATA, (client, handler, buf, sender) -> {
			int entityId = buf.readVarInt();
			assert client.world != null;
			Entity entity = client.world.getEntityById(entityId);
			if (entity != null) {
				entity.getSyncableDataRegistry().accept(buf);
			}
		});

		// Client Operations Networking
		ClientPlayNetworking.registerGlobalReceiver(MModdingPackets.CONFIGS, (client, handler, buf, sender) -> ClientOperations.receiveConfigOnClient(buf));
		ClientPlayNetworking.registerGlobalReceiver(MModdingPackets.STELLAR_STATUS, (client, handler, buf, sender) -> ClientOperations.receiveStellarStatusOnClient(handler, buf));
		ClientPlayNetworking.registerGlobalReceiver(MModdingPackets.PLAY_SOUNDTRACKS, (client, handler, buf, sender) -> ClientOperations.receiveSoundtrackToPlay(client, buf));
		ClientPlayNetworking.registerGlobalReceiver(MModdingPackets.RELEASE_SOUNDTRACKS, (client, handler, buf, sender) -> ClientOperations.receiveSoundtrackToRelease(client));
		ClientPlayNetworking.registerGlobalReceiver(MModdingPackets.CLEAR_SOUNDTRACKS, (client, handler, buf, sender) -> ClientOperations.receiveSoundtrackToClear(client));
		ClientPlayNetworking.registerGlobalReceiver(MModdingPackets.STOP_SOUNDTRACKS, (client, handler, buf, sender) -> ClientOperations.receiveSoundtrackToStop(client));
	}
}
