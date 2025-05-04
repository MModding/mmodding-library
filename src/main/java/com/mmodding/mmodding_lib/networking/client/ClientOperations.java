package com.mmodding.mmodding_lib.networking.client;

import com.google.gson.JsonParser;
import com.mmodding.mmodding_lib.MModdingLib;
import com.mmodding.mmodding_lib.client.ClientCaches;
import com.mmodding.mmodding_lib.ducks.ClientWorldDuckInterface;
import com.mmodding.mmodding_lib.library.config.ConfigObject;
import com.mmodding.mmodding_lib.library.config.StaticConfig;
import com.mmodding.mmodding_lib.library.events.networking.client.ClientConfigNetworkingEvents;
import com.mmodding.mmodding_lib.library.events.networking.client.ClientStellarStatusNetworkingEvents;
import com.mmodding.mmodding_lib.library.soundtracks.Soundtrack;
import com.mmodding.mmodding_lib.library.stellar.StellarStatus;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.util.Identifier;

@Environment(EnvType.CLIENT)
public class ClientOperations {

	public static void receiveConfigOnClient(PacketByteBuf packet) {
		String qualifier = packet.readString();
		String content = packet.readString();

		ClientConfigNetworkingEvents.BEFORE.invoker().beforeConfigReceived(ClientCaches.CONFIGS);

		StaticConfig staticConfig = StaticConfig.of(MModdingLib.CONFIGS.get(qualifier));
		staticConfig.saveConfig(new ConfigObject(JsonParser.parseString(content).getAsJsonObject()));
		ClientCaches.CONFIGS.put(qualifier, staticConfig);

		ClientConfigNetworkingEvents.AFTER.invoker().afterConfigReceived(ClientCaches.CONFIGS);
	}

	public static void receiveStellarStatusOnClient(ClientPlayNetworkHandler handler, PacketByteBuf packet) {
		if (handler.getWorld() == null) return;

		Identifier identifier = packet.readIdentifier();
		long currentTime = packet.readLong();
		long totalTime = packet.readLong();

		StellarStatus status = StellarStatus.of(currentTime, totalTime);

		ClientStellarStatusNetworkingEvents.BEFORE.invoker().beforeStellarStatusReceived(identifier, status);

		((ClientWorldDuckInterface) handler.getWorld()).mmodding_lib$getStellarStatusesAccess().getMap().put(identifier, status);

		ClientStellarStatusNetworkingEvents.AFTER.invoker().afterStellarStatusReceived(identifier, status);
	}

	public static void receiveSoundtrackToPlay(MinecraftClient client, PacketByteBuf packet) {
		if (client.player != null) {
			Soundtrack soundtrack = Soundtrack.create(packet.readIdentifier(), packet.readList(current -> {
				Identifier path = current.readIdentifier();
				boolean isLooping = current.readBoolean();
				int iterations = current.readVarInt();
				if (isLooping) {
					return Soundtrack.Part.looping(path);
				}
				else {
					return Soundtrack.Part.iterations(path, iterations);
				}
			}));
			int fromPart = packet.readVarInt();
			int toPart = packet.readVarInt();
			client.player.getSoundtrackPlayer().play(soundtrack, fromPart, toPart);
		}
	}

	public static void receiveSoundtrackToRelease(MinecraftClient client) {
		if (client.player != null) {
			client.player.getSoundtrackPlayer().release();
		}
	}

	public static void receiveSoundtrackToClear(MinecraftClient client) {
		if (client.player != null) {
			client.player.getSoundtrackPlayer().clear();
		}
	}

	public static void receiveSoundtrackToStop(MinecraftClient client) {
		if (client.player != null) {
			client.player.getSoundtrackPlayer().stop();
		}
	}
}
