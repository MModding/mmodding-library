package com.mmodding.library.config.impl.element.builtin;

import com.mmodding.library.config.api.content.ConfigContent;
import com.mmodding.library.config.api.content.MutableConfigContent;
import com.mmodding.library.config.api.element.ConfigElementTypeWrapper;

public class EnumWrapper<T extends Enum<T>> implements ConfigElementTypeWrapper<Enum<T>, Enum<T>, EnumWrapper.Properties<T>> {

	@Override
	public Enum<T> resolve(ConfigContent category, String qualifier, Properties<T> properties) {
		return Enum.valueOf(properties.enumClass(), category.string(qualifier).toUpperCase());
	}

	@Override
	public void modify(MutableConfigContent mutable, String qualifier, Properties<T> properties, Enum<T> element) {
		mutable.string(qualifier, element.toString().toLowerCase());
	}

	public record Properties<T extends Enum<T>>(Class<T> enumClass) implements ConfigElementTypeWrapper.Properties {}
}
