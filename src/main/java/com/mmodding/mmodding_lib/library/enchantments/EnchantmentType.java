package com.mmodding.mmodding_lib.library.enchantments;

import com.mmodding.mmodding_lib.library.items.CustomItemSettings;
import net.minecraft.item.EnchantedBookItem;
import net.minecraft.item.Items;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

public record EnchantmentType(String name, Text displayName, EnchantedBookItem bookItem, boolean inEnchantingTable) {

	public static EnchantmentType DEFAULT = new EnchantmentType("default", Text.of(""), (EnchantedBookItem) Items.ENCHANTED_BOOK, true);

	public EnchantmentType {
		if (bookItem instanceof CustomEnchantedBookItem customBookItem) {
			customBookItem.setType(this);
		}
	}

	public static EnchantmentType createWithCustomBook(String name, Text displayName, boolean inEnchantingTable, Identifier bookIdentifier, CustomItemSettings bookSettings) {
		CustomEnchantedBookItem enchantedBookItem = new CustomEnchantedBookItem(bookSettings);
		enchantedBookItem.register(bookIdentifier);
		return new EnchantmentType(name, displayName, enchantedBookItem, inEnchantingTable);
	}
}
