package com.mmodding.mmodding_lib.library.utils;

import net.minecraft.util.math.BlockPos;

import java.util.function.Consumer;

public class RadiusUtils {

	public static void forBlockPosInCubicRadius(BlockPos pos, int radius, Consumer<? super BlockPos> execute) {
		BlockPos.iterate(
				pos.getX() - radius,
				pos.getY() - radius,
				pos.getZ() - radius,
				pos.getX() + radius,
				pos.getY() + radius,
				pos.getZ() + radius
		).forEach(execute);
	}
}
