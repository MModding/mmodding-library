package com.mmodding.library.config.impl.content;

import com.mmodding.library.config.api.content.ConfigContent;
import com.mmodding.library.config.api.content.ConfigSchema;
import com.mmodding.library.config.api.content.ConfigSchemaNode;
import com.mmodding.library.config.api.content.ConfigSpec;
import com.mmodding.library.config.api.exception.ConfigNetworkDecodingException;
import com.mmodding.library.config.api.exception.ConfigNetworkEncodingException;
import com.mmodding.library.java.api.container.Pair;
import com.mmodding.library.java.api.either.Either;
import com.mmodding.library.java.api.list.BiList;
import com.mmodding.library.java.api.map.MixedMap;
import com.mojang.serialization.Codec;
import io.netty.buffer.ByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;

import java.util.Map;
import java.util.function.Supplier;
import java.util.stream.Collectors;

public class ConfigStreamCodec<B extends ByteBuf> implements StreamCodec<B, ConfigContent> {

	private final ConfigSchema schema;
	private final String path;
	private final CategoryInfo info;

	public ConfigStreamCodec(ConfigSchema schema, ConfigSpec spec) {
		this(schema, "", CategoryInfo.compileSpec(spec));
	}

	private ConfigStreamCodec(ConfigSchema schema, String path, CategoryInfo info) {
		this.schema = schema;
		this.path = path;
		this.info = info;
	}

	@Override
	public ConfigContent decode(B input) {
		MixedMap<String> result = MixedMap.linked();
		int size = input.readInt();
		for (int i = 0; i < size; i++) {
			String property = ByteBufCodecs.STRING_UTF8.decode(input);
			String propertyPath = ConfigContent.resolve(this.path, property);
			boolean isCategory = input.readBoolean();
			if (isCategory) {
				CategoryInfo inner = this.info.getCategory(property, () -> new ConfigNetworkDecodingException("Expected a field to decode at " + propertyPath));
				result.put(property, ConfigContent.class, new ConfigStreamCodec<>(this.schema, propertyPath, inner).decode(input));
			}
			else {
				ConfigSchemaNode node = this.schema.findNodeOrThrow(propertyPath); // Check for presence in schema.
				StreamCodec<B, Object> objectStreamCodec = this.info.getElementCodec(property, () -> new ConfigNetworkDecodingException("Expected a category to decode at " + propertyPath));
				Object decodedObject = objectStreamCodec.decode(input);
				result.put(property, this.schema.validate(propertyPath, decodedObject.getClass(), node), decodedObject);
			}
		}
		return new ConfigContentImpl(this.schema, this.path, result);
	}

	@Override
	public void encode(B output, ConfigContent value) {
		BiList<String, Class<?>> properties = value.getAllProperties();
		output.writeInt(properties.size());
		for (Pair<String, Class<?>> property : properties) {
			String propertyPath = ConfigContent.resolve(this.path, property.first());
			boolean isCategory = property.second().equals(ConfigContent.class);
			if (isCategory) {
				CategoryInfo inner = this.info.getCategory(property.first(), () -> new ConfigNetworkEncodingException("Expected a field to encode at " + propertyPath));
				new ConfigStreamCodec<>(this.schema, propertyPath, inner).encode(output, value.category(property.first()));
			}
			else {
				StreamCodec<B, Object> objectStreamCodec = this.info.getElementCodec(property.first(), () -> new ConfigNetworkEncodingException("Expected a category to encode at " + propertyPath));
				objectStreamCodec.encode(output, value.element(property.first(), property.second()));
			}
		}
	}

	private static class CategoryInfo {

		private final Map<String, Either<CategoryInfo, StreamCodec<? extends ByteBuf, Object>>> raw;

		public CategoryInfo(Map<String, Either<CategoryInfo, StreamCodec<? extends ByteBuf, Object>>> raw) {
			this.raw = raw;
		}

		@SuppressWarnings("unchecked") // that cast is horrifying, I hate when generics do this
		private static CategoryInfo compileSpec(ConfigSpec spec) {
			Map<String, Either<ConfigSpec, Pair<Codec<Object>, StreamCodec<? extends ByteBuf, Object>>>> rawSpec = ConfigSpecImpl.getRaw(spec);
			Map<String, Either<CategoryInfo, StreamCodec<? extends ByteBuf, Object>>> raw = (Map<String, Either<CategoryInfo, StreamCodec<? extends ByteBuf, Object>>>) (Map<?, ?>) rawSpec.entrySet().stream()
				.map(entry -> Map.entry(entry.getKey(), entry.getValue().mapBoth(CategoryInfo::compileSpec, Pair::second)))
				.collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
			return new CategoryInfo(raw);
		}

		public <T extends Throwable> CategoryInfo getCategory(String property, Supplier<T> exceptionSupplier) throws T {
			return this.raw.get(property).getFirst().orElseThrow(exceptionSupplier);
		}

		@SuppressWarnings("unchecked")
		public <B extends ByteBuf, T extends Throwable> StreamCodec<B, Object> getElementCodec(String property, Supplier<T> exceptionSupplier) throws T {
			return (StreamCodec<B, Object>) this.raw.get(property).getSecond().orElseThrow(exceptionSupplier);
		}
	}
}
