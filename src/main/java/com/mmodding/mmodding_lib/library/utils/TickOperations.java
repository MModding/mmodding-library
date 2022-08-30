package com.mmodding.mmodding_lib.library.utils;

public interface TickOperations {

	int getTickValue();

	void setTickValue(int tickValue);

	default void checkTickForOperation(int tick, Runnable runnable) {
		if (this.getTickValue() >= tick) {
			runnable.run();
			this.setTickValue(0);
		} else {
			this.setTickValue(tick + 1);
		}
	}
}
