package com.mmodding.mmodding_lib.lib.utils;

import net.minecraft.util.math.BlockPos;

import java.util.stream.IntStream;

public class RadiusUtils {

	public static void forBlockPosInCubicRadius(BlockPos pos, int radius, BlockPosToRunnable blockPosToRunnable) {
		for (int x: IntStream.range(pos.getX() - radius, pos.getX() + radius).toArray()) {
			for (int y: IntStream.range(pos.getY() + radius, pos.getY() - radius).toArray()) {
				for (int z: IntStream.range(pos.getZ() + radius, pos.getZ() - radius).toArray()) {
					blockPosToRunnable.execute(new BlockPos(x, y ,z));
				}
			}
		}
	}

	public interface BlockPosToRunnable {
		void execute(BlockPos pos);
	}
}
