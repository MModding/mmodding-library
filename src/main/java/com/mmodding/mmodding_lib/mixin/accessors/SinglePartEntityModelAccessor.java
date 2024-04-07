package com.mmodding.mmodding_lib.mixin.accessors;

import net.minecraft.client.render.entity.model.SinglePartEntityModel;
import net.minecraft.util.math.Vec3f;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(SinglePartEntityModel.class)
public interface SinglePartEntityModelAccessor {

	@Accessor("field_39195")
	static Vec3f getField39195() {
		throw new IllegalStateException();
	}
}
