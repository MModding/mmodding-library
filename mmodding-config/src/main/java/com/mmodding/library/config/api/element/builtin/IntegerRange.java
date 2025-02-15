package com.mmodding.library.config.api.element.builtin;

import com.mmodding.library.config.api.element.ConfigElement;
import com.mmodding.library.java.api.object.Copyable;
import net.minecraft.util.math.MathHelper;

@ConfigElement
public final class IntegerRange implements Copyable<IntegerRange> {

	private final int min;
	private final int max;

	private int value;

	public IntegerRange(int min, int max, int value) {
		this.min = min;
		this.max = max;
		this.value = value;
	}

	public int getValue() {
		return this.value;
	}

	public void setValue(int value) {
		this.value = MathHelper.clamp(value, this.min, this.max);
	}

	@Override
	public IntegerRange copy() {
		return new IntegerRange(this.min, this.max, this.value);
	}
}
