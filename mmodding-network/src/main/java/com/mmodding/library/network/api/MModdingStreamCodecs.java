package com.mmodding.library.network.api;

import io.netty.buffer.ByteBuf;
import io.netty.util.internal.ObjectUtil;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.util.Mth;

import java.util.function.Supplier;

public class MModdingStreamCodecs {

	public static <E extends Enum<E>> StreamCodec<ByteBuf, E> fromEnum(Supplier<E[]> values) {
		return StreamCodec.of(
			(output, value) -> output.writeInt(value.ordinal()),
			input -> values.get()[input.readInt()]
		);
	}

	public static StreamCodec<ByteBuf, Integer> intRange(int min, int max) {
		return ByteBufCodecs.INT.map(
			decoded -> Mth.clamp(decoded, min, max),
			encoding -> ObjectUtil.checkInRange(min, max, encoding, "encoding")
		);
	}

	public static StreamCodec<ByteBuf, Double> doubleRange(double min, double max) {
		return ByteBufCodecs.DOUBLE.map(
			decoded -> Mth.clamp(decoded, min, max),
			encoding -> ObjectUtil.checkInRange(min, max, encoding, "encoding")
		);
	}
}
