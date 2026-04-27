package com.mmodding.library.config.impl.content;

import com.mmodding.library.config.api.content.ConfigContent;
import com.mmodding.library.config.api.content.ConfigSpec;
import com.mmodding.library.java.api.container.Pair;
import com.mmodding.library.java.api.container.Typed;
import com.mmodding.library.java.api.either.Either;
import com.mmodding.library.java.api.map.BiMap;
import com.mmodding.library.java.api.map.MixedMap;
import com.mojang.serialization.Codec;
import io.netty.buffer.ByteBuf;
import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap;
import net.minecraft.network.codec.StreamCodec;

import java.util.Map;
import java.util.function.Consumer;

// Config spec will be a builder for codec, schema and defaults (each field is for each object)
public class ConfigSpecImpl implements ConfigSpec {

	private final Map<String, Either<ConfigSpec, Pair<Codec<Object>, StreamCodec<ByteBuf, Object>>>> raw = new Object2ObjectOpenHashMap<>();
	private final MixedMap<String> defaults = MixedMap.linked();

	public static Map<String, Either<ConfigSpec, Pair<Codec<Object>, StreamCodec<ByteBuf, Object>>>> getRaw(ConfigSpec spec) {
		return ((ConfigSpecImpl) spec).raw;
	}

	public static Codec<ConfigContent> buildCodec(ConfigSpec spec) {
		return new ConfigCodec(getRaw(spec));
	}

	public static ConfigContent retrieveDefaultContent(ConfigSpec spec) {
		return new ConfigContentImpl(((ConfigSpecImpl) spec).defaults);
	}

	@Override
	@SuppressWarnings("unchecked")
	public <T> ConfigSpec element(String property, Codec<T> codec, StreamCodec<ByteBuf, T> streamCodec, T initial) {
		this.raw.put(property, Either.ofSecond(Pair.create((Codec<Object>) codec, (StreamCodec<ByteBuf, Object>) streamCodec)));
		this.defaults.put(property, Typed.of(initial));
		return this;
	}

	@Override
	public ConfigSpec category(String property, Consumer<ConfigSpec> category) {
		ConfigSpecImpl spec = (ConfigSpecImpl) ConfigSpec.create();
		category.accept(spec);
		this.raw.put(property, Either.ofFirst(spec));
		this.defaults.put(property, ConfigContent.class, new ConfigContentImpl(spec.defaults));
		return this;
	}
}
