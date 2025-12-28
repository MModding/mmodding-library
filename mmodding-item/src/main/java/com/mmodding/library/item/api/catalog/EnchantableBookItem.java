package com.mmodding.library.item.api.catalog;

import net.minecraft.item.BookItem;
import net.minecraft.item.EnchantedBookItem;

import java.util.function.Supplier;

/**
 * A {@link BookItem} class supporting enchantments.
 */
public class EnchantableBookItem extends BookItem {

	private final Supplier<EnchantedBookItem> enchantedBookItem;

	public EnchantableBookItem(Supplier<EnchantedBookItem> enchantedBookItem, Settings settings) {
		super(settings);
		this.enchantedBookItem = enchantedBookItem;
	}

	public EnchantedBookItem getEnchantedBookItem() {
		return this.enchantedBookItem.get();
	}
}
