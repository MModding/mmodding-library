package com.mmodding.library.item.api.category;

import net.minecraft.item.Item;
import org.quiltmc.qsl.base.api.util.InjectedInterface;

@InjectedInterface(Item.class)
public interface ItemCategoryContainer {

	default <T extends Item> T setCategory(ItemCategory category) {
		throw new IllegalStateException();
	}
}
