package com.mmodding.mmodding_lib.networking;

import com.mmodding.mmodding_lib.ducks.ServerWorldDuckInterface;
import com.mmodding.mmodding_lib.library.events.networking.common.StellarStatusNetworkingEvents;
import com.mmodding.mmodding_lib.library.soundtracks.Soundtrack;
import com.mmodding.mmodding_lib.library.stellar.StellarStatus;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Identifier;
import org.quiltmc.qsl.networking.api.PacketByteBufs;
import org.quiltmc.qsl.networking.api.ServerPlayNetworking;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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

	public static void playSoundtrackForPlayer(ServerPlayerEntity player, Soundtrack soundtrack, int fromPart, int toPart) {
		PacketByteBuf packet = PacketByteBufs.create();

		List<Soundtrack.Part> parts = new ArrayList<>();

		for (int i = 0; i < soundtrack.getPartsCount(); i++) {
			parts.add(soundtrack.getPart(i));
		}

		packet.writeIdentifier(soundtrack.getIdentifier());
		packet.writeCollection(parts, (buf, current) -> {
			buf.writeIdentifier(current.getSound().getId());
			buf.writeBoolean(current.isLooping());
			buf.writeVarInt(current.getIterations());
		});

		packet.writeVarInt(fromPart);
		packet.writeVarInt(toPart);

		ServerPlayNetworking.send(player, MModdingPackets.PLAY_SOUNDTRACKS, packet);
	}

	public static void releaseSoundtrackForPlayer(ServerPlayerEntity player) {
		ServerPlayNetworking.send(player, MModdingPackets.RELEASE_SOUNDTRACKS, PacketByteBufs.create());
	}

	public static void clearSoundtrackForPlayer(ServerPlayerEntity player) {
		ServerPlayNetworking.send(player, MModdingPackets.CLEAR_SOUNDTRACKS, PacketByteBufs.create());
	}

	public static void stopSoundtrackForPlayer(ServerPlayerEntity player) {
		ServerPlayNetworking.send(player, MModdingPackets.STOP_SOUNDTRACKS, PacketByteBufs.create());
	}
}
