package com.mmodding.mmodding_lib.client;

import com.google.gson.JsonParser;
import com.mmodding.mmodding_lib.library.config.Config;
import com.mmodding.mmodding_lib.library.config.ConfigObject;
import com.mmodding.mmodding_lib.library.events.config.ConfigNetworkingEvents;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.util.Identifier;
import org.quiltmc.qsl.networking.api.client.ClientPlayNetworking;

@Environment(EnvType.CLIENT)
public class ClientPacketReceivers {

	public static void register() {

		ClientPlayNetworking.registerGlobalReceiver(new Identifier("configs-channel"), ((client, handler, buf, responseSender) -> {
			String configName = buf.readString();
			String configContent = buf.readString();

			MModdingLibClient.clientConfigs.get(configName).saveConfig(new ConfigObject(JsonParser.parseString(configContent).getAsJsonObject()));
			Config config = MModdingLibClient.clientConfigs.get(configName);

			ConfigNetworkingEvents.AFTER.invoker().afterConfigSent(config);
		}));
	}
}
