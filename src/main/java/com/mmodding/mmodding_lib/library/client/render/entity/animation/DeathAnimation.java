package com.mmodding.mmodding_lib.library.client.render.entity.animation;

public interface DeathAnimation {

	boolean applyRedOverlayOnDeath();

	int getDeathTime();

	Runnable executeDeathAnimation();
}
