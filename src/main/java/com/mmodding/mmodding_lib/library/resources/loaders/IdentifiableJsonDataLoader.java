package com.mmodding.mmodding_lib.library.resources.loaders;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.mmodding.mmodding_lib.MModdingLib;
import net.fabricmc.fabric.api.resource.SimpleResourceReloadListener;
import net.minecraft.resource.Resource;
import net.minecraft.resource.ResourceManager;
import net.minecraft.util.Identifier;
import net.minecraft.util.JsonHelper;
import net.minecraft.util.profiler.Profiler;

import java.io.IOException;
import java.io.Reader;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;

public interface IdentifiableJsonDataLoader extends SimpleResourceReloadListener<Map<Identifier, JsonElement>> {

	String SUFFIX = ".json";

	Gson getGson();

	String getDataType();

	@Override
	default CompletableFuture<Map<Identifier, JsonElement>> load(ResourceManager resourceManager, Profiler profiler, Executor executor) {
		Map<Identifier, JsonElement> map = new HashMap<>();

		for(Map.Entry<Identifier, Resource> entry : resourceManager.findResources(this.getDataType(), id -> id.getPath().endsWith(SUFFIX)).entrySet()) {
			String string = entry.getKey().getPath();
			Identifier resource = new Identifier(entry.getKey().getNamespace(), string.substring(this.getDataType().length() + 1, string.length() - SUFFIX.length()));
			try {
				Reader reader = entry.getValue().openBufferedReader();
				try {
					JsonElement deserialized = JsonHelper.deserialize(this.getGson(), reader, JsonElement.class);
					if (deserialized != null) {
						JsonElement element = map.put(resource, deserialized);
						if (element != null) {
							throw new IllegalStateException("Duplicate data file ignored with ID " + resource);
						}
					} else {
						MModdingLib.LIBRARY_CONTAINER.getLogger().error("Couldn't load data file {} from {} as it's null or empty", resource, entry.getKey());
					}
				} catch (IllegalStateException illegalStateException) {
					if (reader != null) {
						try {
							reader.close();
						} catch (IOException ioException) {
							illegalStateException.addSuppressed(ioException);
						}
					}
					throw illegalStateException;
				}
                reader.close();
            } catch (IllegalArgumentException | IOException | JsonParseException jsonParseException) {
				MModdingLib.LIBRARY_CONTAINER.getLogger().error("Couldn't parse data file {} from {}", resource, entry.getKey(), jsonParseException);
			}
		}

		return CompletableFuture.supplyAsync(() -> map, executor);
	}
}
