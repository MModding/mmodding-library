package com.mmodding.library.item.api.catalog;

import java.util.function.Supplier;
import net.minecraft.world.item.EnchantedBookItem;

/**
 * A simple variant of the {@link EnchantedBookItem} class with better vanilla-integration for modders.
 */
public class SimpleEnchantedBookItem extends EnchantedBookItem {

	private Supplier<EnchantableBookItem> sourceItem;

	public SimpleEnchantedBookItem(Supplier<EnchantableBookItem> sourceItem, Properties settings) {
		super(settings);
	}

	public EnchantableBookItem getSourceItem() {
		return this.sourceItem.get();
	}
}
