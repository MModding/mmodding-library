package com.mmodding.mmodding_lib.mixin.injectors;

import net.minecraft.util.random.RandomGenerator;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(World.class)
public class WorldMixin {
	@Shadow
	@Final
	public RandomGenerator random;
}
