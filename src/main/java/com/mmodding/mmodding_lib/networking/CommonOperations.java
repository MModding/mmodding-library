package com.mmodding.mmodding_lib.networking;

import com.mmodding.mmodding_lib.ducks.ServerWorldDuckInterface;
import com.mmodding.mmodding_lib.library.events.networking.common.StellarStatusNetworkingEvents;
import com.mmodding.mmodding_lib.library.stellar.StellarStatus;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Identifier;
import org.quiltmc.qsl.networking.api.PacketByteBufs;
import org.quiltmc.qsl.networking.api.ServerPlayNetworking;

import java.util.HashMap;
import java.util.Map;

public class CommonOperations {

	public static void sendStellarStatusToClient(ServerPlayerEntity player, Identifier identifier, StellarStatus status) {
		PacketByteBuf packet = PacketByteBufs.create();

		packet.writeIdentifier(identifier);
		packet.writeLong(status.getCurrentTime());
		packet.writeLong(status.getFullTime());

		StellarStatusNetworkingEvents.BEFORE.invoker().beforeStellarStatusSent(identifier, status);

		ServerPlayNetworking.send(player, MModdingPackets.STELLAR_STATUS, packet);

		StellarStatusNetworkingEvents.AFTER.invoker().afterStellarStatusSent(identifier, status);
	}

	public static void sendStellarStatusesToClient(ServerPlayerEntity player) {
		Map<Identifier, StellarStatus> stellarStatus = new HashMap<>(((ServerWorldDuckInterface) player.getWorld()).mmodding_lib$getStellarStatuses().getMap());

		StellarStatusNetworkingEvents.BEFORE_ALL.invoker().beforeAllStellarStatusSent(stellarStatus);

		stellarStatus.forEach((identifier, status) -> CommonOperations.sendStellarStatusToClient(player, identifier, status));

		StellarStatusNetworkingEvents.AFTER_ALL.invoker().afterAllStellarStatusSent(stellarStatus);
	}
}
