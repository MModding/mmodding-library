package com.mmodding.mmodding_lib.library.enchantments.types;

import com.mmodding.mmodding_lib.library.enchantments.CustomEnchantedBookItem;
import net.minecraft.item.EnchantedBookItem;
import net.minecraft.item.Item;

public class SimpleEnchantmentType implements EnchantmentType {

	private final String qualifier;
	private final Prefix prefix;
	private final EnchantedBookItem enchantedBook;

	SimpleEnchantmentType(String qualifier, Prefix prefix, Item.Settings enchantedBookSettings) {
		this.qualifier = qualifier;
		this.prefix = prefix;
		this.enchantedBook = new CustomEnchantedBookItem(this, enchantedBookSettings);
	}

	@Override
	public String getQualifier() {
		return this.qualifier;
	}

	@Override
	public Prefix getPrefix() {
		return this.prefix;
	}

	@Override
	public EnchantedBookItem getEnchantedBook() {
		return this.enchantedBook;
	}
}
