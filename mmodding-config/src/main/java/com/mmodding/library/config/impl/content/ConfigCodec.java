package com.mmodding.library.config.impl.content;

import com.mmodding.library.config.api.content.ConfigContent;
import com.mmodding.library.config.api.content.ConfigSpec;
import com.mmodding.library.java.api.container.Pair;
import com.mmodding.library.java.api.container.Typed;
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

	private final Map<String, Either<ConfigSpec, Codec<Object>>> raw;

	public ConfigCodec(Map<String, Either<ConfigSpec, Pair<Codec<Object>, StreamCodec<ByteBuf, Object>>>> raw) {
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
				DataResult<MapLike<T>> maybeInner = ops.getMap(entry.getSecond());
				if (maybeInner.isSuccess()) {
					var innerRaw = ConfigSpecImpl.getRaw(this.raw.get(property).getFirst().orElseThrow(() -> new IllegalStateException("Expected a category to decode for field " + entry.getFirst())));
					ConfigCodec innerCodec = new ConfigCodec(innerRaw);
					ConfigContent innerParsed = innerCodec.decode(ops, entry.getSecond()).getOrThrow().getFirst();
					result.put(ops.getStringValue(entry.getFirst()).getOrThrow(), ConfigContent.class, innerParsed);
				}
				else {
					Codec<Object> valueCodec = this.raw.get(property).getSecond().orElseThrow(() -> new IllegalStateException("Expected an element to decode for field " + entry.getFirst()));
					Object valueParsed = valueCodec.decode(ops, entry.getSecond()).getOrThrow().getFirst();
					result.put(ops.getStringValue(entry.getFirst()).getOrThrow(), Typed.of(valueParsed));
				}
			});
			return DataResult.success(new com.mojang.datafixers.util.Pair<>(new ConfigContentImpl(result), input));
		}
		else {
			throw new IllegalStateException("Failed to parse as a map");
		}
	}

	@Override
	public <T> DataResult<T> encode(ConfigContent input, DynamicOps<T> ops, T prefix) {
		T map = ops.createMap(new Object2ObjectLinkedOpenHashMap<>());
		for (Pair<String, Class<?>> property : input.getAllProperties()) {
			if (property.second().equals(ConfigContent.class)) {
				var innerRaw = ConfigSpecImpl.getRaw(this.raw.get(property.first()).getFirst().orElseThrow(() -> new IllegalStateException("Expected a category to encode for field " + property.first())));
				ConfigCodec innerCodec = new ConfigCodec(innerRaw);
				T serialized = innerCodec.encodeStart(ops, input.category(property.first())).getOrThrow();
				map = ops.mergeToMap(map, ops.createString(property.first()), serialized).getOrThrow();
			}
			else {
				Codec<Object> valueCodec = this.raw.get(property.first()).getSecond().orElseThrow(() -> new IllegalStateException("Expected an element to decode for field " + property.first()));
				T serialized = valueCodec.encodeStart(ops, input.element(property.first(), property.second())).getOrThrow();
				map = ops.mergeToMap(map, ops.createString(property.first()), serialized).getOrThrow();
			}
		}
		return DataResult.success(map);
	}
}
