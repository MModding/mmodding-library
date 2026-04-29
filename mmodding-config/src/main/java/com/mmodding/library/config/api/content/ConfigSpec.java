package com.mmodding.library.config.api.content;

import com.mmodding.library.config.api.content.context.DoubleRangeContext;
import com.mmodding.library.config.api.content.context.IntRangeContext;
import com.mmodding.library.config.impl.content.ConfigSpecImpl;
import com.mmodding.library.core.api.serialization.MModdingCodecs;
import com.mmodding.library.java.api.color.Color;
import com.mmodding.library.network.api.MModdingStreamCodecs;
import com.mojang.serialization.Codec;
import io.netty.buffer.ByteBuf;
import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap;
import net.minecraft.core.BlockPos;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.resources.Identifier;
import net.minecraft.util.StringRepresentable;
import net.minecraft.world.phys.Vec3;
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
	 * @return the newly created config specification
	 */
	static ConfigSpec create() {
		return new ConfigSpecImpl();
	}

	/**
	 * Specifies a boolean value at this location.
	 * @param property the property
	 * @param initial the initial value
	 * @return the current specification
	 */
	default ConfigSpec bool(String property, boolean initial) {
		return this.element(property, initial, Codec.BOOL, ByteBufCodecs.BOOL, ConfigSchemaNode.of(Boolean.class));
	}

	/**
	 * Specifies an integer value at this location.
	 * @param property the property
	 * @param initial the initial value
	 * @return the current specification
	 */
	default ConfigSpec intValue(String property, int initial) {
		return this.element(property, initial, Codec.INT, ByteBufCodecs.INT, ConfigSchemaNode.of(Integer.class));
	}

	/**
	 * Specifies a double floating point value at this location.
	 * @param property the property
	 * @param initial the initial value
	 * @return the current specification
	 */
	default ConfigSpec doubleValue(String property, float initial) {
		return this.element(property, initial, Codec.FLOAT, ByteBufCodecs.FLOAT, ConfigSchemaNode.of(Double.class));
	}

	/**
	 * Specifies a string value at this location.
	 * @param property the property
	 * @param initial the initial value
	 * @return the current specification
	 */
	default ConfigSpec string(String property, String initial) {
		return this.element(property, initial, Codec.STRING, ByteBufCodecs.STRING_UTF8, ConfigSchemaNode.of(String.class));
	}

	/**
	 * Specifies an identifier value at this location.
	 * @param property the property
	 * @param initial the initial value
	 * @return the current specification
	 */
	default ConfigSpec identifier(String property, Identifier initial) {
		return this.element(property, initial, Identifier.CODEC, Identifier.STREAM_CODEC, ConfigSchemaNode.of(Identifier.class));
	}

	/**
	 * Specifies a color value at this location.
	 * @param property the property
	 * @param initial the initial value
	 * @return the current specification
	 */
	default ConfigSpec color(String property, Color initial) {
		return this.element(property, initial, Color.CODEC, Color.STREAM_CODEC, ConfigSchemaNode.of(Color.class));
	}

	/**
	 * Specifies a (block) position value at this location.
	 * @param property the property
	 * @param initial the initial value
	 * @return the current specification
	 */
	default ConfigSpec pos(String property, BlockPos initial) {
		return this.element(property, initial, BlockPos.CODEC, BlockPos.STREAM_CODEC, ConfigSchemaNode.of(BlockPos.class));
	}

	/**
	 * Specifies a vec3 value at this location.
	 * @param property the property
	 * @param initial the initial value
	 * @return the current specification
	 */
	default ConfigSpec vec3(String property, Vec3 initial) {
		return this.element(property, initial, Vec3.CODEC, Vec3.STREAM_CODEC, ConfigSchemaNode.of(Vec3.class));
	}

	/**
	 * Specifies an integer range at this location.
	 * @param property the property
	 * @param start the first boundary of the range
	 * @param end the second boundary of the range
	 * @param initial the initial value
	 * @return the current specification
	 */
	default ConfigSpec intRange(String property, int start, int end, int initial) {
		return this.element(
			property,
			initial,
			MModdingCodecs.intRange(start, end),
			MModdingStreamCodecs.intRange(start, end),
			ConfigSchemaNode.of(Integer.class, new IntRangeContext(start, end))
		);
	}

	/**
	 * Specifies a double floating point range at this location.
	 * @param property the property
	 * @param start the first boundary of the range
	 * @param end the second boundary of the range
	 * @param initial the initial value
	 * @return the current specification
	 */
	default ConfigSpec doubleRange(String property, double start, double end, double initial) {
		return this.element(
			property,
			initial,
			MModdingCodecs.doubleRange(start, end),
			MModdingStreamCodecs.doubleRange(start, end),
			ConfigSchemaNode.of(Double.class, new DoubleRangeContext(start, end))
		);
	}

	/**
	 * Specifies an enumeration value at this location. The enumeration names should be using <code>UPPER_SNAKE_CASE</code> in Java code and <code>snake_case</code> in JSON objects.
	 * @param property the property
	 * @param values the enumeration values
	 * @param initial the initial value
	 * @return the current specification
	 * @param <T> the enumeration class type
	 */
	default <T extends Enum<T> & StringRepresentable> ConfigSpec enumValue(String property, Supplier<T[]> values, T initial) {
		return this.element(
			property,
			initial,
			StringRepresentable.fromEnum(values),
			MModdingStreamCodecs.fromEnum(values),
			ConfigSchemaNode.of(initial.getClass(), ConfigSchemaNode.Validation.STRICT)
		);
	}

	/**
	 * Specifies a list at this location.
	 * @param property the property
	 * @param entryCodec the entry codec
	 * @param entryStreamCodec the entry stream codec
	 * @param initial the initial value
	 * @return the current specification
	 * @param <T> the entry class type of the list
	 */
	default <T> ConfigSpec list(String property, Codec<T> entryCodec, StreamCodec<ByteBuf, T> entryStreamCodec, T... initial) {
		return this.element(
			property,
			List.of(initial),
			entryCodec.listOf(),
			entryStreamCodec.apply(ByteBufCodecs.list()),
			ConfigSchemaNode.of(List.class)
		);
	}

	/**
	 * Specifies a map at this location.
	 * @param property the property
	 * @param keyCodec the key codec
	 * @param keyStreamCodec the key stream codec
	 * @param valueCodec the value codec
	 * @param valueStreamCodec the value stream codec
	 * @param initial the initial value
	 * @return the current specification
	 * @param <K> the key class type of the map
	 * @param <V> the value class type of the map
	 */
	default <K, V> ConfigSpec map(String property, Codec<K> keyCodec, StreamCodec<ByteBuf, K> keyStreamCodec, Codec<V> valueCodec, StreamCodec<ByteBuf, V> valueStreamCodec, Map.Entry<K, V>... initial) {
		return this.element(
			property,
			Map.ofEntries(initial),
			Codec.unboundedMap(keyCodec, valueCodec),
			ByteBufCodecs.map(Object2ObjectOpenHashMap::new, keyStreamCodec, valueStreamCodec),
			ConfigSchemaNode.of(Map.class)
		);
	}

	/**
	 * Specifies a new element at this location and selects its {@link ConfigSchemaNode}.
	 * @param property the property
	 * @param codec the element codec
	 * @param streamCodec the element stream codec
	 * @param initial the initial value
	 * @param node the schema node
	 * @return the current specification
	 * @param <T> the element class type
	 */
	<T> ConfigSpec element(String property, T initial, Codec<T> codec, StreamCodec<? extends ByteBuf, T> streamCodec, ConfigSchemaNode node);

	/**
	 * Specifies a new category at this location, and specifies its inner content.
	 * @param property the property
	 * @param category the category's content specification
	 * @return the current specification
	 */
	ConfigSpec category(String property, Consumer<ConfigSpec> category);
}
