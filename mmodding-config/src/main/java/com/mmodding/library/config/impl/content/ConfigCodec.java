package com.mmodding.library.config.impl.content;

import com.mmodding.library.config.api.exception.ConfigDecodingException;
import com.mmodding.library.config.api.exception.ConfigEncodingException;
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
import java.util.function.Supplier;
import java.util.stream.Collectors;

public class ConfigCodec implements Codec<ConfigContent> {

	private final ConfigSchema schema;
	private final String path;
	private final CategoryInfo info;

	public ConfigCodec(ConfigSchema schema, ConfigSpec spec) {
		this(schema, "", CategoryInfo.compileSpec(spec));
	}

	private ConfigCodec(ConfigSchema schema, String path, CategoryInfo info) {
		this.schema = schema;
		this.path = path;
		this.info = info;
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
					CategoryInfo inner = this.info.getCategory(property, () -> new ConfigDecodingException("Expected an element to decode for field " + propertyPath));
					ConfigCodec innerCodec = new ConfigCodec(this.schema, propertyPath, inner);
					ConfigContent innerParsed = innerCodec.decode(ops, entry.getSecond()).getOrThrow().getFirst();
					result.put(ops.getStringValue(entry.getFirst()).getOrThrow(), ConfigContent.class, innerParsed);
				}
				else {
					ConfigSchemaNode node = this.schema.findNodeOrThrow(propertyPath); // Check for presence in schema.
					Codec<Object> valueCodec = this.info.getElementCodec(property, () -> new ConfigDecodingException("Expected a category to decode for field " + propertyPath));
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
				var inner = this.info.getCategory(property.first(), () -> new ConfigEncodingException("Expected an element to encode for field " + propertyPath));
				ConfigCodec innerCodec = new ConfigCodec(this.schema, propertyPath, inner);
				T serialized = innerCodec.encodeStart(ops, input.category(property.first())).getOrThrow();
				map = ops.mergeToMap(map, ops.createString(property.first()), serialized).getOrThrow();
			}
			else {
				Codec<Object> valueCodec = this.info.getElementCodec(property.first(), () -> new ConfigEncodingException("Expected a category to encode for field " + propertyPath));
				T serialized = valueCodec.encodeStart(ops, input.element(property.first(), property.second())).getOrThrow(s -> new ConfigEncodingException("Encoding of field " + propertyPath + " failed: " + s));
				map = ops.mergeToMap(map, ops.createString(property.first()), serialized).getOrThrow();
			}
		}
		return DataResult.success(map);
	}

	private static class CategoryInfo {

		private final Map<String, Either<CategoryInfo, Codec<Object>>> raw;

		public CategoryInfo(Map<String, Either<CategoryInfo, Codec<Object>>> raw) {
			this.raw = raw;
		}

		private static CategoryInfo compileSpec(ConfigSpec spec) {
			Map<String, Either<ConfigSpec, Pair<Codec<Object>, StreamCodec<? extends ByteBuf, Object>>>> rawSpec = ConfigSpecImpl.getRaw(spec);
			Map<String, Either<CategoryInfo, Codec<Object>>> raw = rawSpec.entrySet().stream()
				.map(entry -> Map.entry(entry.getKey(), entry.getValue().mapBoth(CategoryInfo::compileSpec, Pair::first)))
				.collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
			return new CategoryInfo(raw);
		}

		public <T extends Throwable> CategoryInfo getCategory(String property, Supplier<T> exceptionSupplier) throws T {
			return this.raw.get(property).getFirst().orElseThrow(exceptionSupplier);
		}

		public <T extends Throwable> Codec<Object> getElementCodec(String property, Supplier<T> exceptionSupplier) throws T {
			return this.raw.get(property).getSecond().orElseThrow(exceptionSupplier);
		}
	}
}
