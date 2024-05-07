package com.mmodding.library.item.api.category;

public interface ItemCategoryContainer {

	default <T> T setCategory(ItemCategory category) {
		throw new IllegalStateException();
	}
}
