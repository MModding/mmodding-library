package com.mmodding.library.config.impl.serialization;

import com.mmodding.library.config.api.Config;
import com.mmodding.library.config.api.content.ConfigContent;
import com.mmodding.library.config.api.content.MutableConfigContent;
import com.mmodding.library.config.impl.content.MutableConfigContentImpl;
import com.mmodding.library.java.api.list.MixedList;
import net.fabricmc.loader.api.FabricLoader;
import org.quiltmc.parsers.json.JsonReader;
import org.quiltmc.parsers.json.JsonToken;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.BigInteger;

public class ConfigDeserializer {

	private static JsonReader reader(Config config) throws IOException {
		return JsonReader.json(FabricLoader.getInstance().getConfigDir().resolve(config.getFilePath() + ".json"));
	}

	private static void readBoolean(JsonReader reader, String qualifier, MutableConfigContent mutable) throws IOException {
		mutable.bool(qualifier, reader.nextBoolean());
	}

	private static void readNumber(JsonReader reader, String qualifier, MutableConfigContent mutable) throws IOException {
		Number num = reader.nextNumber();
		if (num instanceof BigInteger) {
			mutable.integer(qualifier, ((BigInteger) num).intValueExact());
		}
		else if (num instanceof BigDecimal) {
			mutable.integer(qualifier, ((BigDecimal) num).intValueExact());
		}
		else {
			mutable.floating(qualifier, (float) num.doubleValue());
		}
	}

	private static void readString(JsonReader reader, String qualifier, MutableConfigContent mutable) throws IOException {
		mutable.string(qualifier, reader.nextString());
	}

	/**
	 * @apiNote Array Deserialization only supports Primitive Configuration Types.
	 */
	private static void readArray(JsonReader reader, String qualifier, MutableConfigContent mutable) throws IOException {
		reader.beginArray();
		MixedList list = MixedList.create();
		while (reader.peek() != JsonToken.END_ARRAY) {
			switch (reader.peek()) {
				case BOOLEAN -> list.add(Boolean.class, reader.nextBoolean());
				case NUMBER -> {
					Number num = reader.nextNumber();
					if (num instanceof BigInteger) {
						list.add(Integer.class, ((BigInteger) num).intValueExact());
					}
					else if (num instanceof BigDecimal) {
						list.add(Integer.class, ((BigDecimal) num).intValueExact());
					}
					else {
						list.add(Float.class, (float) num.doubleValue());
					}
				}
				case STRING -> list.add(String.class, reader.nextString());
			}
		}
		mutable.list(qualifier, list);
		reader.endArray();
	}

	private static void objectProcess(JsonReader reader, MutableConfigContent mutable) {
		try {
			while (reader.peek() != JsonToken.END_OBJECT) {
				String qualifier = reader.nextName();
				switch (reader.peek()) {
					case BOOLEAN -> ConfigDeserializer.readBoolean(reader, qualifier, mutable);
					case NUMBER -> ConfigDeserializer.readNumber(reader, qualifier, mutable);
					case STRING -> ConfigDeserializer.readString(reader, qualifier, mutable);
					case BEGIN_ARRAY -> ConfigDeserializer.readArray(reader, qualifier, mutable);
					case BEGIN_OBJECT -> ConfigDeserializer.readObject(reader, qualifier, mutable);
				}
			}
		}
		catch (IOException error) {
			throw new RuntimeException("Failed to read configuration!", error);
		}
	}

	private static void readObject(JsonReader reader, String qualifier, MutableConfigContent mutable) throws IOException {
		reader.beginObject();
		mutable.category(qualifier, next -> ConfigDeserializer.objectProcess(reader, next));
		reader.endObject();
	}

	public static ConfigContent deserialize(Config config) {
		try {
			MutableConfigContentImpl mutable = new MutableConfigContentImpl();
			JsonReader reader = ConfigDeserializer.reader(config);
			reader.beginObject();
			ConfigDeserializer.objectProcess(reader, mutable);
			reader.endObject();
			reader.close();
			return mutable.immutable();
		}
		catch (IOException error) {
			throw new RuntimeException("Failed to read configuration!", error);
		}
	}
}
