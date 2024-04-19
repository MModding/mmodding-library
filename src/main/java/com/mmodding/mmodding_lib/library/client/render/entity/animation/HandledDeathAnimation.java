package com.mmodding.mmodding_lib.library.client.render.entity.animation;

public interface HandledDeathAnimation extends DeathAnimation {

	AnimationData getAnimationData();

	int getAge();

	@Override
	default Runnable getDeathAnimation() {
		return () -> {
			this.getAnimationData().moving.stop();
			this.getAnimationData().idle.stop();
			this.getAnimationData().falling.stop();
			this.getAnimationData().dying.start(this.getAge());
		};
	}
}
