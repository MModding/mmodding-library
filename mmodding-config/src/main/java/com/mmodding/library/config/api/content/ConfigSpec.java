package com.mmodding.library.config.api.content;

import com.mmodding.library.config.impl.content.ConfigSpecImpl;
import com.mmodding.library.core.api.serialization.MModdingCodecs;
import com.mmodding.library.java.api.color.Color;
import com.mmodding.library.network.api.MModdingStreamCodecs;
import com.mojang.serialization.Codec;
import io.netty.buffer.ByteBuf;
import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.util.StringRepresentable;
import org.jetbrains.annotations.ApiStatus;

import java.util.List;
import java.util.Map;
import java.util.function.Consumer;
import java.util.function.Supplier;

/**
 * The Configuration Specification. It explicits the specification of the configuration to follow, limiting its content to a set of rules.
 */
@ApiStatus.NonExtendable
public interface ConfigSpec {

	/**
	 * Creates a new {@link ConfigSpec}, ready to be specified.
	 * @return the newly created config schema
	 */
	static ConfigSpec create() {
		return new ConfigSpecImpl();
	}

	/**
	 * Specifies a boolean value at this location.
	 * @param property the property
	 * @return the current schema
	 */
	default ConfigSpec bool(String property, boolean initial) {
		return this.element(property, Codec.BOOL, ByteBufCodecs.BOOL, initial);
	}

	/**
	 * Specifies an integer value at this location.
	 * @param property the property
	 * @return the current schema
	 */
	default ConfigSpec intValue(String property, int initial) {
		return this.element(property, Codec.INT, ByteBufCodecs.INT, initial);
	}

	/**
	 * Specifies a double floating point value at this location.
	 * @param property the property
	 * @return the current schema
	 */
	default ConfigSpec doubleValue(String property, float initial) {
		return this.element(property, Codec.FLOAT, ByteBufCodecs.FLOAT, initial);
	}

	/**
	 * Specifies a string value at this location.
	 * @param property the property
	 * @return the current schema
	 */
	default ConfigSpec string(String property, String initial) {
		return this.element(property, Codec.STRING, ByteBufCodecs.STRING_UTF8, initial);
	}

	/**
	 * Specifies a color value at this location.
	 * @param property the property
	 * @return the current schema
	 */
	default ConfigSpec color(String property, Color initial) {
		return this.element(property, Color.CODEC, Color.STREAM_CODEC, initial);
	}

	/**
	 * Specifies an integer range at this location.
	 * @param property the property
	 * @param start the first boundary of the range
	 * @param end the second boundary of the range
	 * @return the current schema
	 */
	default ConfigSpec intRange(String property, int start, int end, int initial) {
		return this.element(property, MModdingCodecs.intRange(start, end), MModdingStreamCodecs.intRange(start, end), initial);
	}

	/**
	 * Specifies a double floating point range at this location.
	 * @param property the property
	 * @param start the first boundary of the range
	 * @param end the second boundary of the range
	 * @return the current schema
	 */
	default ConfigSpec doubleRange(String property, double start, double end, double initial) {
		return this.element(property, MModdingCodecs.doubleRange(start, end), MModdingStreamCodecs.doubleRange(start, end), initial);
	}

	/**
	 * Specifies an enumeration value at this location. The enumeration names should be using <code>UPPER_SNAKE_CASE</code> in Java code and <code>snake_case</code> in JSON objects.
	 * @param property the property
	 * @param values the enumeration values
	 * @return the current schema
	 * @param <T> the enumeration class type
	 */
	default <T extends Enum<T> & StringRepresentable> ConfigSpec enumValue(String property, Supplier<T[]> values, T initial) {
		return this.element(property, StringRepresentable.fromEnum(values), MModdingStreamCodecs.fromEnum(values), initial);
	}

	/**
	 * Specifies a list at this location.
	 * @param property the property
	 * @return the current schema
	 */
	default <T> ConfigSpec list(String property, Codec<T> entryCodec, StreamCodec<ByteBuf, T> entryStreamCodec, T... initial) {
		return this.element(property, entryCodec.listOf(), entryStreamCodec.apply(ByteBufCodecs.list()), List.of(initial));
	}

	default <K, V> ConfigSpec map(String property, Codec<K> keyCodec, StreamCodec<ByteBuf, K> keyStreamCodec, Codec<V> valueCodec, StreamCodec<ByteBuf, V> valueStreamCodec, Map.Entry<K, V>... fallback) {
		return this.element(property, Codec.unboundedMap(keyCodec, valueCodec), ByteBufCodecs.map(Object2ObjectOpenHashMap::new, keyStreamCodec, valueStreamCodec), Map.ofEntries(fallback));
	}

	/**
	 * Specifies a new element at this location.
	 * @param property the property
	 * @param codec the element codec
	 * @return the current schema
	 * @param <T> the wrapped element class type
	 */
	<T> ConfigSpec element(String property, Codec<T> codec, StreamCodec<ByteBuf, T> streamCodec, T initial);

	/**
	 * Specifies a new category at this location, and specifies its inner content.
	 * @param property the property
	 * @param category the category's content specification
	 * @return the current schema
	 */
	ConfigSpec category(String property, Consumer<ConfigSpec> category);
}
