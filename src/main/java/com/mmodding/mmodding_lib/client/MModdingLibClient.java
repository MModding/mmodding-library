package com.mmodding.mmodding_lib.client;

import com.mmodding.mmodding_lib.library.config.Config;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import org.quiltmc.loader.api.ModContainer;
import org.quiltmc.qsl.base.api.entrypoint.client.ClientModInitializer;

import java.util.HashMap;
import java.util.Map;

@Environment(EnvType.CLIENT)
public class MModdingLibClient implements ClientModInitializer {

	public static final Map<String, Config> clientConfigs = new HashMap<>();

	@Override
	public void onInitializeClient(ModContainer mod) {
		ClientEvents.register();
		ClientPacketReceivers.register();
	}
}
