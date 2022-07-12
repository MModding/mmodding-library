package com.mmodding.mmodding_lib.library.utils;

import com.mmodding.mmodding_lib.library.DamageSources;
import net.minecraft.util.Util;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;
import net.minecraft.world.explosion.Explosion;

public class WorldUtils {
	public static void pushExplosion(WorldAccess world, BlockPos pos, float power) {
		Explosion explosion = new Explosion((World) world, null, DamageSources.PUSH, null, pos.getX(), pos.getY(), pos.getZ(), power, false, Explosion.DestructionType.NONE);
		explosion.collectBlocksAndDamageEntities();
		explosion.affectWorld(false);
	}

	public static void waitTicks(int ticks) {
		long actualTick = Util.getMeasuringTimeMs();
		boolean passed = false;
		while (!passed) {
			passed = Util.getMeasuringTimeMs() / 50 == actualTick * ticks;
		}
	}
}
