package com.mmodding.library.config.impl.element.builtin;

import com.mmodding.library.config.api.content.ConfigContent;
import com.mmodding.library.config.api.content.MutableConfigContent;
import com.mmodding.library.config.api.element.ConfigElementTypeWrapper;
import com.mmodding.library.java.api.color.Color;

public class ColorWrapper implements ConfigElementTypeWrapper<Color, Color, ConfigElementTypeWrapper.Properties> {

	@Override
	public Color resolve(ConfigContent category, String qualifier, Properties properties) {
		return Color.argb(category.integer(qualifier));
	}

	@Override
	public void modify(MutableConfigContent mutable, String qualifier, Properties properties, Color element) {
		mutable.integer(qualifier, element.toDecimal());
	}
}
