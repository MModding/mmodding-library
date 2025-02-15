package com.mmodding.library.config.api.element.builtin;

import com.mmodding.library.config.api.element.ConfigElement;
import com.mmodding.library.java.api.object.Copyable;
import net.minecraft.util.math.MathHelper;

@ConfigElement
public final class FloatingRange implements Copyable<FloatingRange> {

	private final float min;
	private final float max;

	private float value;

	public FloatingRange(float min, float max, float value) {
		this.min = min;
		this.max = max;
		this.value = value;
	}

	public float getValue() {
		return this.value;
	}

	public void setValue(float value) {
		this.value = MathHelper.clamp(value, this.min, this.max);
	}

	@Override
	public FloatingRange copy() {
		return new FloatingRange(this.min, this.max, this.value);
	}
}
