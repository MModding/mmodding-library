package com.mmodding.library.config.impl.element.builtin;

import com.mmodding.library.config.api.content.ConfigContent;
import com.mmodding.library.config.api.content.MutableConfigContent;
import com.mmodding.library.config.api.element.builtin.IntegerRange;
import com.mmodding.library.config.api.element.ConfigElementTypeWrapper;
import org.jetbrains.annotations.ApiStatus;

@ApiStatus.Internal
public class IntegerRangeWrapper implements ConfigElementTypeWrapper<IntegerRange, Integer, IntegerRangeWrapper.Properties> {

	@Override
	public IntegerRange resolve(ConfigContent category, String qualifier, IntegerRangeWrapper.Properties properties) {
		return IntegerRange.of(properties.min, properties.max, category.integer(qualifier));
	}

	@Override
	public void modify(MutableConfigContent mutable, String qualifier, IntegerRangeWrapper.Properties properties, Integer value) {
		mutable.integer(qualifier, value);
	}

	public record Properties(int min, int max) implements ConfigElementTypeWrapper.Properties {}
}
