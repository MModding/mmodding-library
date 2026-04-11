package com.mmodding.library.item.mixin;

import net.minecraft.world.entity.Entity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(Entity.class)
public abstract class EntityMixin {

	@Shadow
	public abstract double distanceToSqr(Entity entity);
}
