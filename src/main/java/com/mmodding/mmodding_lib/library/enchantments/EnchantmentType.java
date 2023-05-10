package com.mmodding.mmodding_lib.library.enchantments;

import com.mmodding.mmodding_lib.library.items.settings.AdvancedItemSettings;
import net.minecraft.item.EnchantedBookItem;
import net.minecraft.item.Items;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.ApiStatus;

@ApiStatus.Experimental
public record EnchantmentType(String name, Text prefix, EnchantedBookItem bookItem, boolean inEnchantingTable) {

	public static EnchantmentType DEFAULT = new EnchantmentType("default", Text.of(""), (EnchantedBookItem) Items.ENCHANTED_BOOK, true);

	public EnchantmentType {
		if (bookItem instanceof CustomEnchantedBookItem customBookItem) {
			customBookItem.setType(this);
		}
	}

	public static EnchantmentType createWithCustomBook(String name, Text prefix, boolean inEnchantingTable, Identifier bookIdentifier, AdvancedItemSettings bookSettings) {
		CustomEnchantedBookItem enchantedBookItem = new CustomEnchantedBookItem(bookSettings);
		enchantedBookItem.register(bookIdentifier);
		return new EnchantmentType(name, prefix, enchantedBookItem, inEnchantingTable);
	}
}
