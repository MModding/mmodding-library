package com.mmodding.mmodding_lib.library.entities.projectiles;

import net.minecraft.entity.projectile.ArrowEntity;
import net.minecraft.world.World;

public interface StuckArrowDisplay<T extends ArrowEntity> {


	T getArrowInstance(World world, double x, double y, double z);
}
