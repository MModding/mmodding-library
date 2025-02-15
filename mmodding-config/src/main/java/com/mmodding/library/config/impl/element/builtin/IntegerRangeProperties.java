package com.mmodding.library.config.impl.element.builtin;

import com.mmodding.library.config.api.element.ConfigElementProperties;
import com.mmodding.library.config.api.element.builtin.IntegerRange;
import org.jetbrains.annotations.ApiStatus;

import java.util.Map;

@ApiStatus.Internal
public class IntegerRangeProperties extends ConfigElementProperties<IntegerRange> {

	public IntegerRangeProperties(int min, int max) {
		super(IntegerRange.class, Map.of("min", min, "max", max));
	}
}
