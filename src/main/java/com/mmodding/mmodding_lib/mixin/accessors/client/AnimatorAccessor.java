package com.mmodding.mmodding_lib.mixin.accessors.client;

import net.minecraft.client.render.animation.Animation;
import net.minecraft.client.render.animation.Animator;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(Animator.class)
public interface AnimatorAccessor {

	@Invoker("getAnimationTimestamp")
	static float invokeGetAnimationTimestamp(Animation animation, long elapsedMillis) {
		throw new IllegalStateException();
	}
}
