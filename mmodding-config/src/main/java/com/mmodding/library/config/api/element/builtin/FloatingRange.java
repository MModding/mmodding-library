package com.mmodding.library.config.api.element.builtin;

import com.mmodding.library.config.api.element.ConfigElement;
import com.mmodding.library.java.api.object.Copyable;
import net.minecraft.util.math.MathHelper;

@ConfigElement
public final class FloatingRange implements Copyable<FloatingRange> {

	private final double min;
	private final double max;

	private double value;

	private FloatingRange(double min, double max, double value) {
		this.min = min;
		this.max = max;
		this.setValue(value);
	}

	public static FloatingRange of(double min, double max, double value) {
		return new FloatingRange(min, max, value);
	}

	public double getValue() {
		return this.value;
	}

	public void setValue(double value) {
		this.value = MathHelper.clamp(value, this.min, this.max);
	}

	@Override
	public FloatingRange copy() {
		return new FloatingRange(this.min, this.max, this.value);
	}
}
