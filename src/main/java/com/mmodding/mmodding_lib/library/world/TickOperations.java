package com.mmodding.mmodding_lib.library.world;

public interface TickOperations {

	int getTickValue();

	void setTickValue(int tickValue);

	default void checkTickForOperation(int tick, Runnable run) {
		if (this.getTickValue() >= tick) {
			run.run();
			this.setTickValue(0);
		} else {
			this.setTickValue(this.getTickValue() + 1);
		}
	}
}
