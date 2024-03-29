package com.mmodding.mmodding_lib.library.utils;

public class CycledInteger {

	private final int max;

	private int value;

	public CycledInteger(int max) {
		this(0, max);
	}

	public CycledInteger(int value, int max) {
		this.value = value;
		this.max = max;
	}

	public int add(int value) {
		return this.value = this.value + value > this.max ? this.value + value - this.max : this.value + value;
	}

	public int get() {
		return this.value;
	}
}
