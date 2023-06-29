package com.mmodding.mmodding_lib.library.utils;

import com.mmodding.mmodding_lib.library.DamageSources;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;
import net.minecraft.world.explosion.Explosion;

public class WorldUtils {

	public static void doTaskAfter(ServerWorld serverWorld, long ticksToWait, Runnable task) {
		((TickTaskServer) serverWorld).doTaskAfter(ticksToWait, task);
	}

	public static void repeatTaskUntil(ServerWorld serverWorld, long ticksUntil, Runnable task) {
		((TickTaskServer) serverWorld).repeatTaskUntil(ticksUntil, task);
	}

	public static void doTaskAfter(ClientWorld clientWorld, long ticksToWait, Runnable task) {
		((TickTaskClient) clientWorld).doTaskAfter(ticksToWait, task);
	}

	public static void repeatTaskUntil(ClientWorld clientWorld, long ticksUntil, Runnable task) {
		((TickTaskClient) clientWorld).repeatTaskUntil(ticksUntil, task);
	}

	public static void pushExplosion(WorldAccess world, BlockPos pos, float power) {
		Explosion explosion = new Explosion((World) world, null, DamageSources.PUSH, null, pos.getX(), pos.getY(), pos.getZ(), power, false, Explosion.DestructionType.NONE);
		explosion.collectBlocksAndDamageEntities();
		explosion.affectWorld(false);
	}

	public interface TickTaskServer {

		void doTaskAfter(long ticksToWait, Runnable run);

		void repeatTaskUntil(long ticksUntil, Runnable run);
	}

	public interface TickTaskClient {

		void doTaskAfter(long ticksToWait, Runnable run);

		void repeatTaskUntil(long ticksUntil, Runnable run);
	}
}
