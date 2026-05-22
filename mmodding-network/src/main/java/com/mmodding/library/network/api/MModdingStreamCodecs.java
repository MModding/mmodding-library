package com.mmodding.library.network.api;

import com.mmodding.library.java.api.list.MixedList;
import com.mmodding.library.java.api.map.MixedMap;
import com.mmodding.library.network.impl.MixedListStreamCodec;
import com.mmodding.library.network.impl.MixedMapStreamCodec;
import io.netty.buffer.ByteBuf;
import io.netty.util.internal.ObjectUtil;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.util.Mth;

import java.util.Map;
import java.util.function.Supplier;

/**
 * Providing common stream codecs.
 */
public class MModdingStreamCodecs {

	/**
	 * Creates a stream codec for an enumeration.
	 * @param values the enumeration values
	 * @return the stream codec
	 * @param <B> the byte buff class type
	 * @param <E> the enum class type
	 */
	public static <B extends ByteBuf, E extends Enum<E>> StreamCodec<B, E> fromEnum(Supplier<E[]> values) {
		return StreamCodec.of(
			(output, value) -> output.writeInt(value.ordinal()),
			input -> values.get()[input.readInt()]
		);
	}

	/**
	 * Creates a stream codec for a specified int range.
	 * @param min the minimum
	 * @param max the maximum
	 * @return the stream codec
	 * @param <B> the byte buff class
	 */
	public static <B extends ByteBuf> StreamCodec<B, Integer> intRange(int min, int max) {
		return ByteBufCodecs.INT.map(
			decoded -> Mth.clamp(decoded, min, max),
			encoding -> ObjectUtil.checkInRange(min, max, encoding, "encoding")
		).cast();
	}

	/**
	 * Creates a stream codec for a specified double range.
	 * @param min the minimum
	 * @param max the maximum
	 * @return the stream codec
	 * @param <B> the byte buff class type
	 */
	public static <B extends ByteBuf> StreamCodec<B, Double> doubleRange(double min, double max) {
		return ByteBufCodecs.DOUBLE.map(
			decoded -> Mth.clamp(decoded, min, max),
			encoding -> ObjectUtil.checkInRange(min, max, encoding, "encoding")
		).cast();
	}

	/**
	 * Creates a stream codec for a mixed list that provides stream codecs for each supported type it can contain.
	 * @param supportedTypes the supported types
	 * @return the stream codec
	 * @param <B> the byte buff class type
	 */
	public static <B extends ByteBuf> StreamCodec<B, MixedList> mixedList(Map<Class<?>, StreamCodec<B, Object>> supportedTypes) {
		return new MixedListStreamCodec<>(supportedTypes);
	}

	/**
	 * Creates a stream codec for a mixed map that provides stream codecs for each supported value type it can contain.
	 * @param supportedTypes the supported types
	 * @return the stream codec
	 * @param <B> the byte buff class type
	 */
	public static <B extends ByteBuf, K> StreamCodec<B, MixedMap<K>> mixedMap(StreamCodec<B, K> keyCodec, Map<Class<?>, StreamCodec<B, Object>> supportedTypes) {
		return new MixedMapStreamCodec<>(keyCodec, supportedTypes);
	}
}
