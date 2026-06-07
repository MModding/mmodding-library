package com.mmodding.library.core.api.serialization;

import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.Decoder;
import com.mojang.serialization.DynamicOps;
import io.netty.util.internal.ObjectUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Vec3i;
import net.minecraft.util.Mth;
import org.joml.Vector2f;
import org.joml.Vector2i;
import org.joml.Vector3f;

import java.util.List;

public class MModdingCodecs {

	public static final Codec<Vector2f> VECTOR2F = Codec.FLOAT.listOf(2, 2).xmap(
		v -> new Vector2f(v.getFirst(), v.getLast()),
		v -> List.of(v.x, v.y)
	);

	public static final Codec<Vector2i> VECTOR2I = Codec.INT.listOf(2, 2).xmap(
		v -> new Vector2i(v.getFirst(), v.getLast()),
		v -> List.of(v.x, v.y)
	);

	public static final Codec<Vector3f> VECTOR3F = Codec.FLOAT.listOf(3, 3).xmap(
		v -> new Vector3f(v.getFirst(), v.get(1), v.getLast()),
		v -> List.of(v.x, v.y, v.z)
	);

	public static final Codec<BlockPos> STRING_BLOCKPOS = Codec.STRING.xmap(representation -> {
		String[] coords = representation.split(", ");
		return new BlockPos(Integer.parseInt(coords[0]), Integer.parseInt(coords[1]), Integer.parseInt(coords[2]));
	}, Vec3i::toShortString);

	public static Codec<Integer> intRange(int min, int max) {
		return Codec.INT.xmap(
			decoded -> Mth.clamp(decoded, min, max),
			encoding -> ObjectUtil.checkInRange(encoding, min, max, "encoding")
		);
	}

	public static Codec<Double> doubleRange(double min, double max) {
		return Codec.DOUBLE.xmap(
			decoded -> Mth.clamp(decoded, min, max),
			encoding -> ObjectUtil.checkInRange(encoding, min, max, "encoding")
		);
	}

	public static <A> Codec<A> decodeOnly(Decoder<A> decoder) {

		return new Codec<>() {
			@Override
			public <T> DataResult<Pair<A, T>> decode(DynamicOps<T> ops, T input) {
				return decoder.decode(ops, input);
			}

			@Override
			public <T> DataResult<T> encode(A input, DynamicOps<T> ops, T prefix) {
				throw new UnsupportedOperationException();
			}
		};
	}
}
