package com.mmodding.mmodding_lib.library.world;

import com.mmodding.mmodding_lib.library.utils.WorldUtils;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class SmoothTeleport {

	public static void teleport(World world, Entity entity, Vec3d vec3d, int ticks) {
		SmoothTeleport.teleport(world, entity, vec3d.x, vec3d.y, vec3d.z, ticks);
	}

	public static void teleport(World world, Entity entity, double x, double y, double z, int ticks) {
		double baseX = entity.getX();
		double baseY = entity.getY();
		double baseZ = entity.getZ();

		double distX = Math.abs(baseX - x);
		double distY = Math.abs(baseY - y);
		double distZ = Math.abs(baseZ - z);

		double partX = distX / ticks;
		double partY = distY / ticks;
		double partZ = distZ / ticks;

		WorldUtils.repeatSyncedTaskUntil(world, ticks, () -> {
			entity.setVelocity(Vec3d.ZERO);
			entity.teleport(
				entity.getX() + partX,
				entity.getY() + partY,
				entity.getZ() + partZ
			);
		});
	}
}
