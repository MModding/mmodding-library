package com.mmodding.mmodding_lib.library.client.utils;

import net.minecraft.entity.LivingEntity;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import org.quiltmc.loader.api.minecraft.ClientOnly;

@ClientOnly
public class AnimationUtils {

	public static boolean isMoving(LivingEntity livingEntity, float limbDistance) {
		return AnimationUtils.isMoving(livingEntity, limbDistance, 0.015f);
	}

	public static boolean isMoving(LivingEntity livingEntity, float limbDistance, float motionThreshold) {
		Vec3d velocity = livingEntity.getVelocity();
		float averageVelocity = (MathHelper.abs((float) velocity.x) + MathHelper.abs((float) velocity.z)) / 2f;
		return averageVelocity >= motionThreshold && limbDistance != 0;
	}
}
