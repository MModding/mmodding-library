package com.mmodding.mmodding_lib;

import com.mmodding.mmodding_lib.library.network.request.s2c.ServerPendingRequestManager;
import com.mmodding.mmodding_lib.library.network.support.NetworkSupport;
import com.mmodding.mmodding_lib.library.network.request.c2s.ServerRequestHandler;
import com.mmodding.mmodding_lib.library.network.support.type.NetworkList;
import com.mmodding.mmodding_lib.library.network.support.type.NetworkMap;
import com.mmodding.mmodding_lib.library.utils.MModdingIdentifier;
import com.mmodding.mmodding_lib.networking.MModdingPackets;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.ApiStatus;
import org.quiltmc.qsl.networking.api.PacketByteBufs;
import org.quiltmc.qsl.networking.api.ServerPlayNetworking;

import java.util.Map;
import java.util.UUID;

@ApiStatus.Internal
public class PacketReceivers {

	@SuppressWarnings("unchecked")
	public static <T extends NetworkSupport> void register() {

		// C2S Requests Networking
		ServerPlayNetworking.registerGlobalReceiver(MModdingPackets.C2S_REQUESTS, (server, player, handler, buf, sender) -> {
			Identifier requestHandler = buf.readIdentifier();
			Map<UUID, NetworkList> requestMap = buf.readMap(PacketByteBuf::readUuid, NetworkSupport::readComplete);
			PacketByteBuf packet = PacketByteBufs.create();
			NetworkMap responseMap = new NetworkMap();
			requestMap.forEach((uuid, args) -> {
				T value = (T) ServerRequestHandler.HANDLERS.get(requestHandler).respond(args);
				responseMap.put(new MModdingIdentifier(uuid.toString()), (Class<T>) value.getClass(), value);
			});
			packet.writeIdentifier(requestHandler);
			responseMap.writeComplete(packet);
			ServerPlayNetworking.send(player, MModdingPackets.C2S_RESPONSES, packet);
		});

		// S2C Responses Networking
		ServerPlayNetworking.registerGlobalReceiver(MModdingPackets.S2C_RESPONSES, (server, player, handler, buf, sender) -> {
			Identifier manager = buf.readIdentifier();
			NetworkMap map = NetworkSupport.readComplete(buf);
			map.forEach(
				(identifier, type, value) -> ServerPendingRequestManager.getManager(manager)
					.consume(UUID.fromString(identifier.getPath()))
					.ifPresent(action -> action.cast(value))
			);
		});
	}
}
