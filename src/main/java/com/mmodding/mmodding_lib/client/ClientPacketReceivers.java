package com.mmodding.mmodding_lib.client;

import com.google.gson.JsonParser;
import com.mmodding.mmodding_lib.library.events.client.ClientConfigNetworkingEvents;
import com.mmodding.mmodding_lib.library.config.Config;
import com.mmodding.mmodding_lib.library.config.ConfigObject;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.ApiStatus;
import org.quiltmc.loader.api.minecraft.ClientOnly;
import org.quiltmc.qsl.networking.api.client.ClientPlayNetworking;

@ClientOnly
@ApiStatus.Internal
public class ClientPacketReceivers {

	public static void register() {

		ClientPlayNetworking.registerGlobalReceiver(new Identifier("configs-channel"), ((client, handler, buf, responseSender) -> {
			String configName = buf.readString();
			String configContent = buf.readString();

			ClientConfigNetworkingEvents.BEFORE.invoker().beforeConfigReceived(MModdingLibClient.CLIENT_CONFIGS.get(configName));

			MModdingLibClient.CLIENT_CONFIGS.get(configName).saveConfig(new ConfigObject(JsonParser.parseString(configContent).getAsJsonObject()));
			Config config = MModdingLibClient.CLIENT_CONFIGS.get(configName);

			ClientConfigNetworkingEvents.AFTER.invoker().afterConfigReceived(config);
		}));
	}
}
