package com.mmodding.library.config.impl.element.builtin;

import com.mmodding.library.config.api.element.ConfigElementProperties;
import com.mmodding.library.config.api.element.builtin.FloatingRange;
import org.jetbrains.annotations.ApiStatus;

import java.util.Map;

@ApiStatus.Internal
public class FloatingRangeProperties extends ConfigElementProperties<FloatingRange> {

	public FloatingRangeProperties(float min, float max) {
		super(FloatingRange.class, Map.of("min", min, "max", max));
	}
}
