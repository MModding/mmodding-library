package com.mmodding.mmodding_lib.library.enchantments.types;

import net.minecraft.item.Item;

public class TableExclusionEnchantmentType extends SimpleEnchantmentType {

	private final boolean inEnchantingTable;

	TableExclusionEnchantmentType(String qualifier, Prefix prefix, Item.Settings enchantedBookSettings, boolean inEnchantingTable) {
		super(qualifier, prefix, enchantedBookSettings);
		this.inEnchantingTable = inEnchantingTable;
	}

	@Override
	public boolean isInEnchantingTable() {
		return this.inEnchantingTable;
	}
}
