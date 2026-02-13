package com.mmodding.library.config.impl.element.builtin;

import com.mmodding.library.config.api.content.ConfigContent;
import com.mmodding.library.config.api.content.MutableConfigContent;
import com.mmodding.library.config.api.element.builtin.FloatingRange;
import com.mmodding.library.config.api.element.ConfigElementTypeWrapper;
import org.jetbrains.annotations.ApiStatus;

@ApiStatus.Internal
public class FloatingRangeWrapper implements ConfigElementTypeWrapper<FloatingRange, Double, FloatingRangeWrapper.Properties> {

	@Override
	public FloatingRange resolve(ConfigContent category, String qualifier, Properties properties) {
		return FloatingRange.of(properties.min, properties.max, category.floating(qualifier));
	}

	@Override
	public void modify(MutableConfigContent mutable, String qualifier, Properties properties, Double value) {
		mutable.floating(qualifier, value);
	}

	public record Properties(double min, double max) implements ConfigElementTypeWrapper.Properties {}
}
