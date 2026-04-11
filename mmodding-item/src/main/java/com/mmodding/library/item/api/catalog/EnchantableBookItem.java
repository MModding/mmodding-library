package com.mmodding.library.item.api.catalog;

import java.util.function.Supplier;
import net.minecraft.world.item.BookItem;
import net.minecraft.world.item.EnchantedBookItem;

/**
 * A {@link BookItem} class supporting enchantments.
 */
public class EnchantableBookItem extends BookItem {

	private final Supplier<EnchantedBookItem> enchantedBookItem;

	public EnchantableBookItem(Supplier<EnchantedBookItem> enchantedBookItem, Properties settings) {
		super(settings);
		this.enchantedBookItem = enchantedBookItem;
	}

	public EnchantedBookItem getEnchantedBookItem() {
		return this.enchantedBookItem.get();
	}
}
