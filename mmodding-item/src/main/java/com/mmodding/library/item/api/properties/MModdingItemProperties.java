package com.mmodding.library.item.api.properties;

import com.mmodding.library.core.api.MModdingLibrary;
import com.mmodding.library.core.api.management.info.InjectedContent;
import net.minecraft.world.item.Item;

import java.util.List;

@InjectedContent(Item.Properties.class)
public interface MModdingItemProperties {

	CustomItemProperty<List<String>> TRINKET_SLOTS = CustomItemProperty.create(MModdingLibrary.createId("trinket_slots"), List.class, null);

	/**
	 * Allows setting that the current item can also act as a trinket.
	 * <br>Some of its properties will then automatically be applied as a trinket if the <code>trinkets_updated</code> mod is loaded.
	 */
	default Item.Properties trinketSlots(List<String> slots) {
		return this.custom(TRINKET_SLOTS, slots);
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
