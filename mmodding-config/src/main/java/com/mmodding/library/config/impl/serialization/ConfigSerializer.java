package com.mmodding.library.config.impl.serialization;

import com.mmodding.library.config.api.Config;
import com.mmodding.library.config.api.content.ConfigContent;
import com.mmodding.library.config.impl.content.ConfigContentImpl;
import com.mmodding.library.java.api.color.Color;
import com.mmodding.library.java.api.list.MixedList;
import com.mmodding.library.java.api.map.MixedMap;
import net.fabricmc.loader.api.FabricLoader;
import org.quiltmc.parsers.json.JsonWriter;

import java.io.IOException;
import java.nio.file.Path;

public class ConfigSerializer {

	private static JsonWriter writer(Config config) throws IOException {
		Path path = FabricLoader.getInstance().getConfigDir().resolve(config.getFilePath() + ".json");
		// noinspection ResultOfMethodCallIgnored
		path.toFile().getParentFile().mkdirs();
		// noinspection ResultOfMethodCallIgnored
		path.toFile().createNewFile();
		return JsonWriter.json(path);
	}

	/**
	 * @apiNote Array Serialization only supports Primitive Configuration Types.
	 */
	private static void writeArray(JsonWriter writer, MixedList list) throws IOException {
		writer.beginArray();
		list.forEach((type, object) -> {
			try {
				if (type.equals(Boolean.class)) {
					writer.value((boolean) object);
				}
				else if (type.equals(Integer.class)) {
					writer.value((int) object);
				}
				else if (type.equals(Double.class)) {
					writer.value((double) object);
				}
				else if (type.equals(String.class)) {
					writer.value((String) object);
				}
				else if (type.equals(Color.class)) {
					writer.value(((Color) object).toDecimal());
				}
			}
			catch (IOException error) {
				throw new RuntimeException("Failed to write configuration!", error);
			}
		});
		writer.endArray();
	}

	private static void writeObject(JsonWriter writer, ConfigContent content) throws IOException {
		writer.beginObject();
		((ConfigContentImpl) content).getRaw().forEach((qualifier, type, value) -> {
			try {
				System.out.println(qualifier + type);
				writer.name(qualifier);
				if (type.equals(Boolean.class)) {
					writer.value(content.bool(qualifier));
				}
				else if (type.equals(Integer.class)) {
					writer.value(content.integer(qualifier));
				}
				else if (type.equals(Double.class)) {
					writer.value(content.floating(qualifier));
				}
				else if (type.equals(String.class)) {
					writer.value(content.string(qualifier));
				}
				else if (type.equals(MixedList.class)) {
					ConfigSerializer.writeArray(writer, content.list(qualifier));
				}
				else if (type.equals(MixedMap.class)) {
					ConfigSerializer.writeObject(writer, content.category(qualifier));
				}
			} catch (IOException error) {
				throw new RuntimeException("Failed to write configuration!", error);
			}
		});
		writer.endObject();
	}

	public static void serialize(Config config, ConfigContent content) {
		try {
			JsonWriter writer = ConfigSerializer.writer(config);
			writer.setIndent("  ");
			ConfigSerializer.writeObject(writer, content);
			writer.close();
		}
		catch (IOException error) {
			throw new RuntimeException("Failed to write configuration!", error);
		}
	}
}
