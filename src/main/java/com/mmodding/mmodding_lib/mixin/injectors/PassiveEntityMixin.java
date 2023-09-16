package com.mmodding.mmodding_lib.mixin.injectors;

import net.minecraft.entity.passive.PassiveEntity;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(PassiveEntity.class)
public abstract class PassiveEntityMixin extends PathAwareEntityMixin {
}
