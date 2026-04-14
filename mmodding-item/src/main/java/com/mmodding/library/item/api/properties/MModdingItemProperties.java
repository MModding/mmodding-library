package com.mmodding.library.item.api.properties;

import com.mmodding.library.core.api.management.info.InjectedContent;
import net.minecraft.world.item.Item;

@InjectedContent(Item.Properties.class)
public interface MModdingItemProperties {

	/**
	 * Allows setting custom item properties.
	 * @param property the custom item property
	 * @param value the value
	 * @return the current item properties
	 * @param <T> the class type of the property
	 */
	<T> Item.Properties custom(CustomItemProperty<T> property, T value);
}
