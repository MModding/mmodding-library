package com.mmodding.library.config.api.content.builtin;

import com.mmodding.library.config.api.content.ConfigElement;
import com.mmodding.library.java.api.object.Copyable;
import net.minecraft.util.Mth;

@ConfigElement
public final class IntegerRange implements Copyable<IntegerRange> {

	private final int min;
	private final int max;

	private int value;

	private IntegerRange(int min, int max, int value) {
		this.min = min;
		this.max = max;
		this.setValue(value);
	}

	public static IntegerRange of(int min, int max, int value) {
		return new IntegerRange(min, max, value);
	}

	public int getValue() {
		return this.value;
	}

	public void setValue(int value) {
		this.value = Mth.clamp(value, this.min, this.max);
	}

	@Override
	public IntegerRange copy() {
		return new IntegerRange(this.min, this.max, this.value);
	}
}
