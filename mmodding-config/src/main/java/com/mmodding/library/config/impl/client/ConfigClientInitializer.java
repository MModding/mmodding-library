package com.mmodding.library.config.impl.client;

import com.mmodding.library.config.api.Config;
import com.mmodding.library.config.api.ConfigNetworkManagement;
import com.mmodding.library.config.api.Configs;
import com.mmodding.library.config.impl.ConfigImpl;
import com.mmodding.library.config.impl.ConfigsPayload;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.networking.v1.ClientLoginConnectionEvents;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ConfigClientInitializer implements ClientModInitializer {

	private static final Logger LOGGER = LoggerFactory.getLogger("mmodding_config_client");

	@Override
	public void onInitializeClient() {
		ClientPlayNetworking.registerGlobalReceiver(ConfigsPayload.TYPE, (payload, _) -> {
			payload.contents().forEach((identifier, content) -> {
				Config config = Configs.get(identifier);
				if (config.getNetworkManagement().equals(ConfigNetworkManagement.LOCALLY_MANAGED)) {
					LOGGER.warn("Server attempted to send the content of a configuration with identifier {}. That should not happen, be sure that the mod version matches on both sides.", identifier);
				}
				else {
					((ConfigImpl) config).updateUpstreamContent(content); // client will have to use this now
				}
			});
		});
		ClientLoginConnectionEvents.DISCONNECT.register((_, _) -> {
			for (Config config : Configs.getAll().values()) {
				if (config.getNetworkManagement().equals(ConfigNetworkManagement.UPSTREAM_SERVER)) {
					((ConfigImpl) config).updateUpstreamContent(null); // allows client to use back its own content
				}
			}
		});
	}
}
