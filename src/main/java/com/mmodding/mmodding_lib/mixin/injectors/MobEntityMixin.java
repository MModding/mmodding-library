package com.mmodding.mmodding_lib.mixin.injectors;

import net.minecraft.entity.mob.MobEntity;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(MobEntity.class)
public abstract class MobEntityMixin extends LivingEntityMixin {
}
