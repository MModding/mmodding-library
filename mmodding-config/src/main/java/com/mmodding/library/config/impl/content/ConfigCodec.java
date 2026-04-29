package com.mmodding.library.config.impl.content;

import com.mmodding.library.config.api.ConfigDecodingException;
import com.mmodding.library.config.api.ConfigEncodingException;
import com.mmodding.library.config.api.content.ConfigContent;
import com.mmodding.library.config.api.content.ConfigSchema;
import com.mmodding.library.config.api.content.ConfigSchemaNode;
import com.mmodding.library.config.api.content.ConfigSpec;
import com.mmodding.library.java.api.container.Pair;
import com.mmodding.library.java.api.either.Either;
import com.mmodding.library.java.api.map.MixedMap;
import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.DynamicOps;
import com.mojang.serialization.MapLike;
import io.netty.buffer.ByteBuf;
import it.unimi.dsi.fastutil.objects.Object2ObjectLinkedOpenHashMap;
import net.minecraft.network.codec.StreamCodec;

import java.util.Map;
import java.util.stream.Collectors;

public class ConfigCodec implements Codec<ConfigContent> {

	private final ConfigSchema schema;
	private final String path;
	private final Map<String, Either<ConfigSpec, Codec<Object>>> raw;

	public ConfigCodec(ConfigSchema schema, String path, Map<String, Either<ConfigSpec, Pair<Codec<Object>, StreamCodec<? extends ByteBuf, Object>>>> raw) {
		this.schema = schema;
		this.path = path;
		this.raw = raw.entrySet().stream()
			.map(entry -> Map.entry(entry.getKey(), entry.getValue().mapSecond(Pair::first)))
			.collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
	}

	@Override
	public <T> DataResult<com.mojang.datafixers.util.Pair<ConfigContent, T>> decode(DynamicOps<T> ops, T input) {
		DataResult<MapLike<T>> parsedMapResult = ops.getMap(input);
		if (parsedMapResult.isSuccess()) {
			MixedMap<String> result = MixedMap.linked();
			MapLike<T> mapLike = parsedMapResult.getOrThrow();
			mapLike.entries().forEach(entry -> {
				String property = ops.getStringValue(entry.getFirst()).getOrThrow();
				String propertyPath = ConfigContent.resolve(this.path, property);
				DataResult<MapLike<T>> maybeInner = ops.getMap(entry.getSecond());
				if (maybeInner.isSuccess()) {
					var innerRaw = ConfigSpecImpl.getRaw(this.raw.get(property).getFirst().orElseThrow(() -> new ConfigDecodingException("Expected an element to decode for field " + propertyPath)));
					ConfigCodec innerCodec = new ConfigCodec(this.schema, propertyPath, innerRaw);
					ConfigContent innerParsed = innerCodec.decode(ops, entry.getSecond()).getOrThrow().getFirst();
					result.put(ops.getStringValue(entry.getFirst()).getOrThrow(), ConfigContent.class, innerParsed);
				}
				else {
					ConfigSchemaNode node = this.schema.findNodeOrThrow(propertyPath); // Check for presence in schema.
					Codec<Object> valueCodec = this.raw.get(property).getSecond().orElseThrow(() -> new ConfigDecodingException("Expected a category to decode for field " + propertyPath));
					Object valueParsed = valueCodec.decode(ops, entry.getSecond()).getOrThrow(s -> new ConfigDecodingException("Decoding of field " + propertyPath + " failed: " + s)).getFirst();
					result.put(ops.getStringValue(entry.getFirst()).getOrThrow(), this.schema.validate(propertyPath, valueParsed.getClass(), node), valueParsed);
				}
			});
			return DataResult.success(new com.mojang.datafixers.util.Pair<>(new ConfigContentImpl(this.schema, this.path, result), input));
		}
		else {
			throw new ConfigDecodingException("Failed to parse " + this.path + " as a map");
		}
	}

	@Override
	public <T> DataResult<T> encode(ConfigContent input, DynamicOps<T> ops, T prefix) {
		T map = ops.createMap(new Object2ObjectLinkedOpenHashMap<>());
		for (Pair<String, Class<?>> property : input.getAllProperties()) {
			String propertyPath = ConfigContent.resolve(this.path, property.first());
			if (property.second().equals(ConfigContent.class)) {
				var innerRaw = ConfigSpecImpl.getRaw(this.raw.get(property.first()).getFirst().orElseThrow(() -> new ConfigEncodingException("Expected an element to encode for field " + propertyPath)));
				ConfigCodec innerCodec = new ConfigCodec(this.schema, propertyPath, innerRaw);
				T serialized = innerCodec.encodeStart(ops, input.category(property.first())).getOrThrow();
				map = ops.mergeToMap(map, ops.createString(property.first()), serialized).getOrThrow();
			}
			else {
				Codec<Object> valueCodec = this.raw.get(property.first()).getSecond().orElseThrow(() -> new ConfigEncodingException("Expected a category to encode for field " + propertyPath));
				T serialized = valueCodec.encodeStart(ops, input.element(property.first(), property.second())).getOrThrow(s -> new ConfigEncodingException("Encoding of field " + propertyPath + " failed: " + s));
				map = ops.mergeToMap(map, ops.createString(property.first()), serialized).getOrThrow();
			}
		}
		return DataResult.success(map);
	}
}
