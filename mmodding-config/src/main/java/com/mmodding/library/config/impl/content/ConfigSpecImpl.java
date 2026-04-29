package com.mmodding.library.config.impl.content;

import com.mmodding.library.config.api.content.ConfigContent;
import com.mmodding.library.config.api.content.ConfigSchema;
import com.mmodding.library.config.api.content.ConfigSchemaNode;
import com.mmodding.library.config.api.content.ConfigSpec;
import com.mmodding.library.java.api.container.Pair;
import com.mmodding.library.java.api.either.Either;
import com.mmodding.library.java.api.map.MixedMap;
import com.mojang.serialization.Codec;
import io.netty.buffer.ByteBuf;
import it.unimi.dsi.fastutil.objects.Object2ObjectLinkedOpenHashMap;
import net.minecraft.network.codec.StreamCodec;

import java.util.Map;
import java.util.function.Consumer;

// Config spec will be a builder for codec, schema and defaults (each field is for each object)
public class ConfigSpecImpl implements ConfigSpec {

	private final ConfigSchemaImpl schema;
	private final String path;
	private final Map<String, Either<ConfigSpec, Pair<Codec<Object>, StreamCodec<? extends ByteBuf, Object>>>> raw = new Object2ObjectLinkedOpenHashMap<>();
	private final MixedMap<String> defaults = MixedMap.linked();

	public static Map<String, Either<ConfigSpec, Pair<Codec<Object>, StreamCodec<? extends ByteBuf, Object>>>> getRaw(ConfigSpec spec) {
		return ((ConfigSpecImpl) spec).raw;
	}

	public static ConfigSchema retrieveSchema(ConfigSpec spec) {
		return ((ConfigSpecImpl) spec).schema;
	}

	public static Codec<ConfigContent> buildCodec(ConfigSchema schema, String path, ConfigSpec spec) {
		return new ConfigCodec(schema, path, getRaw(spec));
	}

	public static ConfigContent retrieveDefaultContent(ConfigSchemaImpl schema, String path, ConfigSpec spec) {
		return new ConfigContentImpl(schema, path, ((ConfigSpecImpl) spec).defaults);
	}

	public ConfigSpecImpl() {
		this(new ConfigSchemaImpl(), "");
	}

	public ConfigSpecImpl(ConfigSchemaImpl schema, String path) {
		this.path = path;
		this.schema = schema;
	}

	@Override
	@SuppressWarnings("unchecked")
	public <T> ConfigSpec element(String property, T initial, Codec<T> codec, StreamCodec<? extends ByteBuf, T> streamCodec, ConfigSchemaNode node) {
		this.schema.raw.put(ConfigContent.resolve(this.path, property), node);
		this.raw.put(property, Either.ofSecond(Pair.create((Codec<Object>) codec, (StreamCodec<? extends ByteBuf, Object>) streamCodec)));
		this.defaults.put(property, node.type(), initial);
		return this;
	}

	@Override
	public ConfigSpec category(String property, Consumer<ConfigSpec> category) {
		String propertyPath = ConfigContent.resolve(this.path, property);
		ConfigSpecImpl spec = new ConfigSpecImpl(this.schema, propertyPath);
		category.accept(spec);
		this.raw.put(property, Either.ofFirst(spec));
		this.defaults.put(property, ConfigContent.class, new ConfigContentImpl(this.schema, propertyPath, spec.defaults));
		return this;
	}
}
