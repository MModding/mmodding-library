package com.mmodding.mmodding_lib.mixin.accessors;

import net.minecraft.entity.AnimationState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(AnimationState.class)
public interface AnimationStateAccessor {

	@Accessor("field_39112")
	void setField_39112(long value);
}
