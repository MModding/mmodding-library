package com.mmodding.mmodding_lib.mixin.accessors;

import net.minecraft.entity.FallingBlockEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(FallingBlockEntity.class)
public interface FallingBlockEntityAccessor {

    @Accessor
    boolean getDestroyedOnLanding();

    @Accessor
    boolean getHurtEntities();

    @Accessor
    int getFallHurtMax();

    @Accessor
    float getFallHurtAmount();
}
