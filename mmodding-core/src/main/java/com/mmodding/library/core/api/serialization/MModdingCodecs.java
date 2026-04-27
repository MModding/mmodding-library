package com.mmodding.library.core.api.serialization;

import com.mojang.serialization.Codec;
import io.netty.util.internal.ObjectUtil;
import net.minecraft.util.Mth;

public class MModdingCodecs {

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
}
