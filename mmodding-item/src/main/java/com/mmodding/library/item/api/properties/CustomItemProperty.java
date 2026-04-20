package com.mmodding.library.item.api.properties;

import com.mmodding.library.core.api.MModdingLibrary;
import com.mmodding.library.item.impl.setting.CustomItemPropertyImpl;
import net.minecraft.resources.Identifier;
import net.minecraft.world.level.ItemLike;

public interface CustomItemProperty<T> {

	/**
	 * Creates the {@link CustomItemProperty} instance for these parameters.
	 */
	static <T> CustomItemProperty<T> create(Identifier identifier, Class<T> type, T defaultValue) {
		return new CustomItemPropertyImpl<>(identifier, type, defaultValue);
	}

	/**
	 * Gets the value of a {@link CustomItemProperty}.
	 * @param property the custom item property
	 * @return the associated value
	 */
	static <T> T get(ItemLike item, CustomItemProperty<T> property) {
		return CustomItemPropertyImpl.get(item, property);
	}

	/**
	 * The identifier of the {@link CustomItemProperty}.
	 * If you believe that the property you make is conventional, consider using the <code>mmodding</code> namespace (see {@link MModdingLibrary#createId(String)}).
	 * @return the identifier
	 */
	Identifier getIdentifier();

	/**
	 * The class object of the value type.
	 * @return the class object
	 */
	Class<T> getType();

	/**
	 * Returns the default value of this setting for an item not specifying it.
	 * @return the value
	 */
	T getDefault();
}
