package com.mmodding.library.item.api.catalog;

import net.minecraft.item.EnchantedBookItem;

import java.util.function.Supplier;

/**
 * A simple variant of the {@link EnchantedBookItem} class with better vanilla-integration for modders.
 */
public class SimpleEnchantedBookItem extends EnchantedBookItem {

	private Supplier<EnchantableBookItem> sourceItem;

	public SimpleEnchantedBookItem(Supplier<EnchantableBookItem> sourceItem, Settings settings) {
		super(settings);
	}

	public EnchantableBookItem getSourceItem() {
		return this.sourceItem.get();
	}
}
