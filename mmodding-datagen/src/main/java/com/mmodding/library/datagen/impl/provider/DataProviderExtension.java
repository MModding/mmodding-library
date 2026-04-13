package com.mmodding.library.datagen.impl.provider;

import com.google.common.hash.Hashing;
import com.google.common.hash.HashingOutputStream;
import com.google.gson.JsonElement;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import com.mmodding.library.datagen.mixin.DataProviderAccessor;
import net.minecraft.util.Util;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.util.concurrent.CompletableFuture;
import net.minecraft.data.CachedOutput;
import net.minecraft.util.GsonHelper;

public class DataProviderExtension {

	public static final Logger LOGGER = LoggerFactory.getLogger("mmodding_datagen");

	@SuppressWarnings("UnstableApiUsage")
	public static CompletableFuture<?> readAndWriteToPath(CachedOutput writer, JsonElement json, Path path) {
		return CompletableFuture.runAsync(() -> {
			try {
				if (path.toFile().isFile()) {
					try (JsonReader jsonReader = new JsonReader(new FileReader(path.toFile()))) {
						jsonReader.beginObject();
						while (jsonReader.hasNext()) {
							json.getAsJsonObject().addProperty(jsonReader.nextName(), jsonReader.nextString());
						}
						jsonReader.endObject();
					}
					catch (IOException exception) {
						DataProviderExtension.LOGGER.error("Failed to read previous file from {}", path, exception);
					}
				}

				ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
				HashingOutputStream hashingOutputStream = new HashingOutputStream(Hashing.sha1(), byteArrayOutputStream);

				try (JsonWriter jsonWriter = new JsonWriter(new OutputStreamWriter(hashingOutputStream, StandardCharsets.UTF_8))) {
					jsonWriter.setSerializeNulls(false);
					jsonWriter.setIndent("  ");
					GsonHelper.writeValue(jsonWriter, json, DataProviderAccessor.mmodding$getSortingComparator());
				}

				writer.writeIfNeeded(path, byteArrayOutputStream.toByteArray(), hashingOutputStream.hash());
			} catch (IOException iOException) {
				LOGGER.error("Failed to save file to {}", path, iOException);
			}

		}, Util.backgroundExecutor());
	}
}
