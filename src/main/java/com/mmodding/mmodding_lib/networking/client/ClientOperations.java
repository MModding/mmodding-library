package com.mmodding.mmodding_lib.networking.client;

import com.google.gson.JsonParser;
import com.mmodding.mmodding_lib.client.ClientCaches;
import com.mmodding.mmodding_lib.client.MModdingLibClient;
import com.mmodding.mmodding_lib.library.glint.client.GlintPack;
import com.mmodding.mmodding_lib.library.client.utils.MModdingClientGlobalMaps;
import com.mmodding.mmodding_lib.library.config.ConfigObject;
import com.mmodding.mmodding_lib.library.events.client.ClientConfigNetworkingEvents;
import com.mmodding.mmodding_lib.library.events.client.ClientGlintPackNetworkingEvents;
import net.minecraft.item.Item;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.util.registry.Registry;
import org.quiltmc.loader.api.minecraft.ClientOnly;

@ClientOnly
public class ClientOperations {

	public static void receiveConfigOnClient(PacketByteBuf packet) {
		String configName = packet.readString();
		String configContent = packet.readString();

		ClientConfigNetworkingEvents.BEFORE.invoker().beforeConfigReceived(MModdingLibClient.CLIENT_CONFIGS.get(configName));

		MModdingLibClient.CLIENT_CONFIGS.get(configName).saveConfig(new ConfigObject(JsonParser.parseString(configContent).getAsJsonObject()));

		ClientConfigNetworkingEvents.AFTER.invoker().afterConfigReceived(MModdingLibClient.CLIENT_CONFIGS.get(configName));
	}

	public static void receiveGlintPackOnClient(PacketByteBuf packet) {
		Item item = Registry.ITEM.get(packet.readIdentifier());
		GlintPack glintPack = MModdingClientGlobalMaps.getGlintPack(packet.readIdentifier());

		ClientGlintPackNetworkingEvents.BEFORE.invoker().beforeGlintPackReceived(ClientCaches.GLINT_PACKS);

		ClientCaches.GLINT_PACKS.put(item, glintPack);

		ClientGlintPackNetworkingEvents.AFTER.invoker().afterGlintPackReceived(ClientCaches.GLINT_PACKS);
	}
}
