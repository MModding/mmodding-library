package com.mmodding.mmodding_lib.library.containers;

public enum DefaultContainer {
	NULL(0),
	DEFAULT_3X3(9),
	DEFAULT_9X1(9),
	DEFAULT_9X2(18),
	DEFAULT_9X3(27),
	DEFAULT_9X4(36),
	DEFAULT_9X5(45),
	DEFAULT_9X6(54);

	private final int size;

	DefaultContainer(int size) {
		this.size = size;
	}

	public int getSize() {
		return this.size;
	}
}
