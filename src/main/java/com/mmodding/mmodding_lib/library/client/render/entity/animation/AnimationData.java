package com.mmodding.mmodding_lib.library.client.render.entity.animation;

import com.mmodding.mmodding_lib.library.utils.ObjectUtils;
import net.minecraft.entity.AnimationState;
import net.minecraft.entity.Entity;

// will move that to a better package since it's not a client thing, but that will be in a next version
public class AnimationData {

	public final AnimationState moving = new AnimationState();
	public final AnimationState idle = new AnimationState();
	public final AnimationState falling = new AnimationState();
	public final AnimationState dodge = new AnimationState();
	public final AnimationState dying = new AnimationState();

	public int transitionAge;
	public int transitionCount;

	public int fallingAge;
	public int fallingCount;

	public void handle(Entity entity) {
		if (entity.getWorld().isClient() && ObjectUtils.ifNullMakeDefault(!this.dying.isAnimating(), () -> true)) {
			this.idle.start(entity.age);
			this.moving.start(entity.age);
			this.falling.start(entity.age);
		} else {
			this.idle.stop();
			this.moving.stop();
			this.falling.stop();
		}
	}
}
