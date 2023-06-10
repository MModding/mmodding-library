package com.mmodding.mmodding_lib.library.utils;

import com.mmodding.mmodding_lib.library.DamageSources;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.ServerWorldAccess;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;
import net.minecraft.world.explosion.Explosion;
import org.quiltmc.loader.api.minecraft.DedicatedServerOnly;

public class WorldUtils {

	public static void doTaskAfter(ServerWorld serverWorld, long ticksToWait, Runnable task) {
		((WorldUtils.TickTask) serverWorld).doTaskAfter(ticksToWait, task);
	}

	public static void pushExplosion(WorldAccess world, BlockPos pos, float power) {
		Explosion explosion = new Explosion((World) world, null, DamageSources.PUSH, null, pos.getX(), pos.getY(), pos.getZ(), power, false, Explosion.DestructionType.NONE);
		explosion.collectBlocksAndDamageEntities();
		explosion.affectWorld(false);
	}

	public interface TickTask {
		void doTaskAfter(long ticksUntil, Runnable run);
	}
}
