package com.mmodding.library.config.impl;

import com.mmodding.library.config.api.Config;
import com.mmodding.library.config.api.ConfigLevel;
import com.mmodding.library.config.api.ConfigNetworkManagement;
import com.mmodding.library.config.api.content.ConfigContent;
import com.mmodding.library.config.api.content.ConfigSpec;
import com.mmodding.library.core.api.MModdingLibrary;
import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.entity.event.v1.ServerPlayerEvents;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLevelEvents;
import net.fabricmc.fabric.api.networking.v1.PayloadTypeRegistry;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.resources.Identifier;

import java.util.Map;

public class ConfigInitializer implements ModInitializer {

	public static final ConfigSpec COMMON_SPEC = ConfigSpec.create()
		.bool("strict_schema_mode", true);

	public static final Config COMMON_CONFIG = Config.builder("mmodding", "mmodding/common", COMMON_SPEC)
		.withLevel(ConfigLevel.IN_GAME_MODIFICATION)
		.withNetworkManagement(ConfigNetworkManagement.LOCALLY_MANAGED)
		.build(MModdingLibrary.createId("common_config"));

	@Override
	public void onInitialize() {
		PayloadTypeRegistry.clientboundPlay().register(ConfigsPayload.TYPE, ConfigsPayload.CODEC);
		ServerLevelEvents.LOAD.register((server, _) -> {
			ConfigsImpl.getAll().forEach((identifier, config) -> {
				if (config.getLevel().equals(ConfigLevel.LEVEL_LOAD)) {
					ConfigLoader.loadAndSend(server, identifier, config);
				}
			});
		});
		ServerPlayerEvents.JOIN.register(player -> {
			Map<Identifier, ConfigContent> contents = new Object2ObjectOpenHashMap<>();
			ConfigsImpl.getAll().forEach((identifier, config) -> {
				if (config.getNetworkManagement().equals(ConfigNetworkManagement.UPSTREAM_SERVER)) {
					contents.put(identifier, config.getContent());
				}
			});
			ServerPlayNetworking.send(player, new ConfigsPayload(contents));
		});
	}
}
