package com.mmodding.library.config.impl;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.google.gson.stream.JsonWriter;
import com.mmodding.library.config.api.Config;
import com.mmodding.library.config.api.content.ConfigContent;
import com.mojang.serialization.JsonOps;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.resources.Identifier;
import net.minecraft.server.MinecraftServer;
import org.jetbrains.annotations.ApiStatus;

import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Map;

@ApiStatus.Internal
public class ConfigLoader {

	public static void initialLoad(Config config) {
		boolean exists = FabricLoader.getInstance().getConfigDir().resolve(config.getFilePath() + ".json").toFile().exists();
		ConfigContent content = !exists ? ConfigLoader.createAndLoad(config) : ConfigLoader.load(config);
		((ConfigImpl) config).updateLocalContent(content);
	}

	private static JsonWriter writer(Config config) throws IOException {
		Path path = FabricLoader.getInstance().getConfigDir().resolve(config.getFilePath() + ".json");
		Files.createDirectories(path.getParent());
		return new JsonWriter(Files.newBufferedWriter(path));
	}

	public static ConfigContent createAndLoad(Config config) {
		try {
			JsonWriter writer = ConfigLoader.writer(config);
			writer.setIndent("  ");
			Gson gson = new GsonBuilder().setPrettyPrinting().create();
			gson.toJson(config.getCodec().encodeStart(JsonOps.INSTANCE, config.getDefaultContent()).getOrThrow(), writer);
			writer.close();
			return config.getDefaultContent();
		}
		catch (IOException error) {
			throw new RuntimeException("Failed to write configuration!", error);
		}
	}

	private static JsonElement parse(Config config) throws IOException {
		return JsonParser.parseReader(new FileReader(FabricLoader.getInstance().getConfigDir().resolve(config.getFilePath() + ".json").toFile()));
	}

	// Called on config boostrap, and then only on server side (so that if client side is using upstream server config, it does not erase the cache).
	public static ConfigContent load(Config config) {
		try {
			JsonElement parsed = ConfigLoader.parse(config);
			return config.getCodec().decode(JsonOps.INSTANCE, parsed).getOrThrow().getFirst();
		}
		catch (IOException error) {
			throw new RuntimeException("Failed to read configuration!", error);
		}
	}

	public static ConfigContent loadAndSend(MinecraftServer server, Identifier identifier, Config config) {
		ConfigContent content = ConfigLoader.load(config);
		server.getPlayerList().getPlayers().forEach(player -> ServerPlayNetworking.send(player, new ConfigsPayload(Map.of(identifier, content))));
		return content;
	}
}
