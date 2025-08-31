package com.mmodding.library.item.api.category;

import com.mmodding.library.core.api.management.info.InjectedContent;
import net.minecraft.item.Item;

@InjectedContent(Item.class)
public interface ItemCategoryContainer {

	default <T extends Item> T setCategory(ItemCategory category) {
		throw new IllegalStateException();
	}
}
