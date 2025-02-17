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
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.util.Identifier;
import org.quiltmc.loader.api.minecraft.ClientOnly;

import java.util.List;

@ClientOnly
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

	public static void receiveAppendedSoundtrack(MinecraftClient client, PacketByteBuf packet) {
		if (client.player != null) {
			Soundtrack soundtrack = Soundtrack.create(
				packet.readList(current -> {
					Identifier path = current.readIdentifier();
					boolean isLooping = current.readBoolean();
					int iterations = current.readVarInt();
					if (isLooping) {
						return Soundtrack.Part.looping(path);
					}
					else {
						return Soundtrack.Part.iterations(path, iterations);
					}
				})
			);
			int[] parts = packet.readIntArray();
			client.player.getSoundtrackPlayer().append(soundtrack, parts);
		}
	}

	public static void receiveSentSoundtrack(MinecraftClient client, PacketByteBuf packet) {
		if (client.player != null) {
			List<Soundtrack.Part> parts = packet.readList(current -> {
				Identifier path = current.readIdentifier();
				boolean isLooping = current.readBoolean();
				int iterations = current.readVarInt();
				if (isLooping) {
					return Soundtrack.Part.looping(path);
				}
				else {
					return Soundtrack.Part.iterations(path, iterations);
				}
			});
			Soundtrack soundtrack = Soundtrack.create(parts);
			int part = packet.readVarInt();
			if (packet.readBoolean()) {
				client.player.getSoundtrackPlayer().play(soundtrack, part);
			}
			else {
				client.player.getSoundtrackPlayer().playOnce(soundtrack, part);
			}
		}
	}

	public static void receiveSoundtrackSkip(MinecraftClient client) {
		if (client.player != null) {
			client.player.getSoundtrackPlayer().skip();
		}
	}

	public static void receiveSoundtrackSkipToPart(MinecraftClient client, PacketByteBuf packet) {
		if (client.player != null) {
			client.player.getSoundtrackPlayer().skip(packet.readVarInt());
		}
	}

	public static void receiveSoundtrackDeletion(MinecraftClient client) {
		if (client.player != null) {
			client.player.getSoundtrackPlayer().stop();
		}
	}
}
