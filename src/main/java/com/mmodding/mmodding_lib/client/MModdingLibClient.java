package com.mmodding.mmodding_lib.client;

import com.mmodding.mmodding_lib.library.config.Config;
import org.quiltmc.loader.api.ModContainer;
import org.quiltmc.loader.api.minecraft.ClientOnly;
import org.quiltmc.qsl.base.api.entrypoint.client.ClientModInitializer;

import java.util.HashMap;
import java.util.Map;

@ClientOnly
public class MModdingLibClient implements ClientModInitializer {

	public static final Map<String, Config> CLIENT_CONFIGS = new HashMap<>();

	@Override
	public void onInitializeClient(ModContainer mod) {
		ClientEvents.register();
		ClientPacketReceivers.register();
		ClientGlintPacks.register();
	}
}
