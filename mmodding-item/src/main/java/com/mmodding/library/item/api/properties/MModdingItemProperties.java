package com.mmodding.library.item.api.properties;

import com.mmodding.library.core.api.MModdingLibrary;
import com.mmodding.library.core.api.management.info.InjectedContent;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.entity.DecoratedPotPattern;

@InjectedContent(Item.Properties.class)
public interface MModdingItemProperties {

	CustomItemProperty<ResourceKey<DecoratedPotPattern>> DECORATED_POT_PATTERN = CustomItemProperty.create(MModdingLibrary.createId("decorated_pot_pattern"), ResourceKey.class, null);

	/**
	 * Allows setting a decorated pot pattern asset for this item.
	 * @param pattern the decorated pot pattern
	 * @return the current item properties
	 */
	default Item.Properties decoratedPotPattern(ResourceKey<DecoratedPotPattern> pattern) {
		return this.custom(DECORATED_POT_PATTERN, pattern);
	}

	/**
	 * Allows setting custom item properties.
	 * @param property the custom item property
	 * @param value the value
	 * @return the current item properties
	 * @param <T> the class type of the property
	 */
	<T> Item.Properties custom(CustomItemProperty<T> property, T value);
}
