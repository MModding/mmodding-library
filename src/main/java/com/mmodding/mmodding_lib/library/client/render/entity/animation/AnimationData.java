package com.mmodding.mmodding_lib.library.client.render.entity.animation;

import net.minecraft.entity.AnimationState;
import net.minecraft.entity.Entity;

// will move that to a better package since it's not a client thing, but that will be in a next version
public class AnimationData {

	public final AnimationState moving = new AnimationState();
	public final AnimationState idle = new AnimationState();
	public final AnimationState falling = new AnimationState();
	public final AnimationState dodge = new AnimationState();

	public int fallingAge;
	public int fallingCount;

	public AnimationData(Entity entity) {
	}
}
